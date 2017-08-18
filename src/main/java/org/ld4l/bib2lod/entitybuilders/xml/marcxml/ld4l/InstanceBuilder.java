/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.util.Bib2LodStringUtils;

/**
 * Builds an Instance from a Record.
 */
public class InstanceBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private MarcxmlRecord record;
    private InstanceEntity instance;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        // Use this if it generates better error messages 
        // this.record = (MarcxmlRecord.class.cast(params.getRecord()));
        this.record = (MarcxmlRecord) params.getRecord(); 
        
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build an instance.");
        }
        
        this.instance = new InstanceEntity();
 
        // Critical ordering: build admin metadata before identifiers. Remove
        // an identifier with value matching the admin metadata identifier.
        buildAdminMetadata();
        buildIdentifiers();       
        buildTitles();
        buildActivities();
        buildResponsiblityStatement();
        buildProvisionActivityStatement();
        buildWorks();
        buildItem();
       
        return instance;
    }
    
    private void buildAdminMetadata() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAdminMetadataType.superClass());
 
        BuildParams params = new BuildParams()
                .setParentEntity(instance)
                .setRecord(record);
        builder.build(params);          
    }
    
    private void buildIdentifiers() throws EntityBuilderException {
        convert_035();
    }

    private void convert_035() throws EntityBuilderException {

        // 035 is a repeating field
        List<MarcxmlDataField> fields = record.getDataFields(35);
        if (fields.isEmpty()) {
            return;
        }

        EntityBuilder builder = getBuilder(Ld4lIdentifierType.superClass());
        BuildParams params = new BuildParams()
                .setParentEntity(instance);
        for (MarcxmlDataField field : fields) {
            List<MarcxmlSubfield> subfields = field.getSubfields();
            params.setField(field);
            for (MarcxmlSubfield subfield : subfields) {
                params.setSubfield(subfield);
                builder.build(params);                
            }
        }   
    }
    
    private void buildTitles() throws EntityBuilderException { 
        
        // NB There may be multiple, so this isn't sufficient.
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.superClass());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(instance);
        builder.build(params);
    }
    
    private void buildWorks() throws EntityBuilderException {
        // NB There are special cases where one Instance has multiple Works.
        
        // For now, the work will take its title from the instance title
        // need to build a new title with all the same elements and attributes,
        // but new resources.
        // Need method of EntityBuilder.clone() or copy?
        
        EntityBuilder builder = getBuilder(Ld4lWorkType.superClass());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.superClass());

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(instance); 
        builder.build(params);
    }   
    
    private void buildActivities() throws EntityBuilderException  {
        buildPublisherActivities();
        buildManufacturerActivities();
        buildProviderActivities();
    } 
 
    private void buildPublisherActivities() throws EntityBuilderException {
        
        EntityBuilder builder = 
                getBuilder(Ld4lActivityType.PUBLISHER_ACTIVITY);

        BuildParams params = new BuildParams()
                .setParentEntity(instance)
                .setRecord(record);
        
        // First build current publisher activity from mandatory 008.        
        builder.build(params.setField(record.getControlField(8)));

        // 260 fields: build additional publisher activities and add data to 
        // current publisher activity.
        for (MarcxmlDataField field : record.getDataFields(260)) {
            builder.build(params.setField(field));
        }          
    }

    private void buildManufacturerActivities() throws EntityBuilderException {
        
    }
    
    private void buildProviderActivities() throws EntityBuilderException {
        
    }
    
    /**
     * Add responsibility statement to instance from 245$c.
     */
    private void buildResponsiblityStatement() {
        
        MarcxmlDataField field = record.getDataField(245);        
        if (field == null) {
            return;
        }
        
        MarcxmlSubfield subfield = field.getSubfield('c');
        if (subfield == null) {
            return;
        } 
        
        instance.addAttribute(Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT, 
                subfield.getTextValue());             
    }
    
    /**
     * Add provision activity statements from 260$a$b$c.
     */
    private void buildProvisionActivityStatement() {

        List<MarcxmlDataField> fields = record.getDataFields(260);    
        fields.addAll(record.getDataFields(264));
        
        if (fields.isEmpty()) {
            return;
        }
        
        // Each 260$a$b$c yields one provision activity statement.
        for (MarcxmlDataField field : fields) {
        
            String stmt = "";
            char[] codes = {'a', 'b', 'c'};
            for (char code : codes) {
                // Add the subfield text to the statement
                stmt += getProvActivityStmtPart(stmt, field, code);
            }
    
            // Remove final punctuation and whitespace
            if (stmt.length() > 0) {
                stmt = Bib2LodStringUtils.trim(stmt);
            }
    
            if (stmt.length() > 0) {           
                instance.addAttribute(Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT, 
                        stmt);   
            }
        }
    }
    
    private String getProvActivityStmtPart(String stmt, MarcxmlDataField field, 
            char code) {
        
        MarcxmlSubfield subfield = field.getSubfield(code);
        if (subfield == null) {
            return "";
        }
        String val = subfield.getTextValue().trim();
        
        /*
         * Provision activity statement uses any existing final punctuation in 
         * the subfield elements. If there is no existing punctuation, use ISBD
         * standard: ":" precedes $b value and "," precedes $c value.
         */
        String delim = "";
        if (stmt.length() > 0) {
            if ( ! Bib2LodStringUtils.endsWithPunct(stmt)) {
                delim = code == 'b' ? ":" : ","; 
            }
            delim += " ";
        }            
        return delim + val;
    }

}

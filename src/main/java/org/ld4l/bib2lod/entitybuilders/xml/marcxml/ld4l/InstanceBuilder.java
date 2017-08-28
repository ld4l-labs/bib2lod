/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities.ProviderActivityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.util.Bib2LodStringUtils;

/**
 * Builds an Instance from a Record.
 */
public class InstanceBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();

    private InstanceEntity instance;
    private MarcxmlRecord record;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        
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
        buildProvisionActivityStatements();
        buildResponsiblityStatement();
        buildWorks();
        buildItem();
       
        return instance;
    }
    
    private void reset() {
        this.instance = null;
        this.record = null;
    }
    
    private void buildAdminMetadata() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAdminMetadataType.superClass());
 
        BuildParams params = new BuildParams()
                .setParent(instance)
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
                .setParent(instance);
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
                .setParent(instance);
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
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.superClass());

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance); 
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
                .setParent(instance)
                .setRecord(record);
        
        // First build current publisher activity from mandatory 008.        
        builder.build(params.setField(record.getControlField(8)));

        // 260 fields: build additional publisher activities and add data to 
        // current publisher activity from 008.
        List<Character> publisherCodes = Arrays.asList('a', 'b', 'c');
        for (MarcxmlDataField field : record.getDataFields(260)) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = ProviderActivityBuilder.
                    getActivitySubfields(field, publisherCodes);

            for (List<RecordField> subfields : subfieldLists) {
                params.setSubfields(subfields);                     
                builder.build(params); 
            }
        }  
    }
    
    /**
     * Add provision activity statements from 260$a$b$c. Each 260
     * yields one provision activity statement. We do not build the
     * statement off of the activity Entity, since the current 
     * PublisherActivity also contains data from 008.
     * 
     * TODO: Awaiting guidance on what to do with multiple $a$b$c subfields
     * in a single 260. If only one provision activity statement is built,
     * keep this code, though if only the first set of $a$b$c should be 
     * used, a slight modification is needed (check current code < 
     * previous code). But if each should yield a separate statement, build
     * with the Activity entity and use it's data values rather than
     * getting them again from the MARC.
     * 
     */
    private void buildProvisionActivityStatements() {

        // Each 260$a$b$c yields a provision activity statement.
        List<MarcxmlDataField> fields = record.getDataFields(260);
        char[] codes = {'a', 'b', 'c'};
        
        // TODO: Include 264?
        // fields.addAll(record.getDataFields(264));

        for (MarcxmlDataField field : fields) {
        
            String stmt = "";
            for (char code : codes) {
                // Add the subfield text to the statement
                stmt += getProvActivityStmtPart(stmt, field, code);
            }
    
            // Remove final punctuation and whitespace
            if (stmt.length() > 0) {
                stmt = Bib2LodStringUtils.trim(stmt);
            }
    
            if (stmt.length() > 0) {           
                instance.addAttribute(
                        Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT, stmt);                           
            }
        }
    }
    
    private String getProvActivityStmtPart(  
            String stmt, MarcxmlDataField field, char code) {
        
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

    private void buildManufacturerActivities() throws EntityBuilderException {

        EntityBuilder builder = 
                getBuilder(Ld4lActivityType.MANUFACTURER_ACTIVITY);
        
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);

        // Build manufacturer activities from 260$e$f$g
        List<Character> manufacturerCodes = Arrays.asList('e', 'f', 'g');
        for (MarcxmlDataField field : record.getDataFields(260)) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = 
                    ProviderActivityBuilder.getActivitySubfields(
                            field, manufacturerCodes);

            for (List<RecordField> subfields : subfieldLists) {
                params.setField(field)
                      .setSubfields(subfields);
                builder.build(params); 
            }
        }  
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

}

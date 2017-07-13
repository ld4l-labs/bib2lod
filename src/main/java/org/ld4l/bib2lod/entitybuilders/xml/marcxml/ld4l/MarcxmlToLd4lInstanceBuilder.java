/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.ArrayList;
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

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlToLd4lInstanceBuilder extends BaseEntityBuilder {
    
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
        addResponsibilityStatement();
        addProvisionActivityStatement();
        buildWorks();
        buildItem();
       
        return instance;
    }
    
    private void buildAdminMetadata() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAdminMetadataType.class);
 
        BuildParams params = new BuildParams()
                .setParentEntity(instance)
                .setRecord(record);
        builder.build(params);          
    }
    
    private void buildIdentifiers() throws EntityBuilderException {
        convert035();
    }

    private void convert035() throws EntityBuilderException {

        // 035 is a repeating field
        List<MarcxmlDataField> fields = record.getDataFields(35);
        if (fields.isEmpty()) {
            return;
        }

        EntityBuilder builder = getBuilder(Ld4lIdentifierType.class);
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
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
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
        
        EntityBuilder builder = getBuilder(Ld4lWorkType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParentEntity(instance); 
        builder.build(params);
    }   
    
    private void buildActivities() throws EntityBuilderException  {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
        
        // Publication   
        BuildParams params = new BuildParams()
                .setRecord(record)     
                .setParentEntity(instance)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        builder.build(params);
     
    }
    
    /**
     * Add responsibility statement to instance from 245$c.
     */
    private void addResponsibilityStatement() {
        
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
    
    private void addProvisionActivityStatement() {

        MarcxmlDataField field = record.getDataField(260);        
        if (field == null) {
            return;
        }
        
        MarcxmlSubfield a = field.getSubfield('a');
        MarcxmlSubfield b = field.getSubfield('b');
        MarcxmlSubfield c = field.getSubfield('c');
        
        /*
         * Provision activity statement uses any existing punctuation in the
         * subfield elements. If there is no existing punctuation, use ISBD
         * standard: ":" precedes $b value and "," precedes $c value.
         */
        String pas = "";
        if (a != null) {
            pas += a.getTextValue();
        }
        if (b != null) {
            if (pas.length() > 0) {
                if (! pas.matches(".*\\p{Punct}$")) {
                    pas += ":";
                    
                }
                pas += " " + b.getTextValue();
            }
        }
        if (c != null) {
            if (pas.length() > 0) {
                if (! pas.matches(".*\\p{Punct}$")) {
                    pas += ",";
                    
                }
                pas += " " + c.getTextValue();
            }
        }
        
        if (pas.length() > 0) {
            instance.addAttribute(Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT, 
                    pas);   
        }
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
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
        
        buildIdentifiers();
        buildTitles();
        buildWorks();
        buildItem();
        buildActivities();
        addResponsibilityStatement();
        buildAdminMetadata();
        
        return instance;
    }
    
    private void buildIdentifiers() {
        
        EntityBuilder builder = getBuilder(Ld4lIdentifierType.class);
        
        MarcxmlControlField controlField001 = 
                record.getControlField(1);

        if (controlField001 != null) {
            BuildParams params = new BuildParams()
                    .setRelatedEntity(instance)
                    .setField(controlField001);
            buildAndCatchException(builder, params, 
                    "Error building instance identifier from control field 001.");
        } 
    }
    
    private void buildTitles() { 
        
        // NB There may be multiple, so this isn't sufficient.
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);
        buildAndCatchException(builder, params, 
                "Error building instance title.");
    }
    
    private void buildWorks() {
        // NB There are special cases where one Instance has multiple Works.
        
        // For now, the work will take its title from the instance title
        // need to build a new title with all the same elements and attributes,
        // but new resources.
        // Need method of EntityBuilder.clone() or copy?
        
        EntityBuilder builder = getBuilder(Ld4lWorkType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);
        buildAndCatchException(builder, params, 
                "Error building work for instance.");
    }
    
    private void buildItem() {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance); 
        buildAndCatchException(builder, params, 
                "Error building item for instance.");
    }   
    
    private void buildActivities()  {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
 
        MarcxmlControlField field008 = record.getControlField(8);
        
        // Publication 
        if (field008 != null) {        
            BuildParams params = new BuildParams()
                    .setRecord(record)     
                    .setField(field008)
                    .setRelatedEntity(instance)
                    .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
            buildAndCatchException(builder, params, 
                    "Error building instance publisher activity.");
        }
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
    
    private void buildAdminMetadata() {
 
        EntityBuilder builder = getBuilder(Ld4lAdminMetadataType.class);
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setRecord(record);
        buildAndCatchException(builder, params, 
                "Error building admin metadata.");        
    
    }
     
}

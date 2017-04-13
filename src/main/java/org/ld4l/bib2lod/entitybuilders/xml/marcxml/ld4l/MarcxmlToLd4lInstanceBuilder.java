/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlToLd4lInstanceBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private MarcxmlRecord record;
    private Entity instance;
  
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        // Use this if it generates better error messages 
        // this.record = (MarcxmlRecord.class.cast(params.getRecord()));
        this.record = (MarcxmlRecord) params.getRecord();  
        this.instance = new Entity(Ld4lInstanceType.superClass());
        
        // TODO Add instance subtypes 
        
        buildIdentifiers();
        buildTitles();
        buildWorks();
        buildItem();
        buildPublisherActivity();
        
        return instance;
    }
    
    private void buildIdentifiers() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lIdentifierType.class);
        
        MarcxmlControlField controlField001 = 
                record.getControlField("001");

        if (controlField001 != null) {
            BuildParams params = new BuildParams()
                    .setRelatedEntity(instance)
                    .setField(controlField001);
            builder.build(params);  
        } 
        
        // TODO Get other identifiers from other datafields

    }
    
    private void buildTitles() throws EntityBuilderException { 
        
        // NB There may be multiple, so this isn't sufficient.
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.class);
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);
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
                .setRelatedEntity(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.class);

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setRelatedEntity(instance);        
        builder.build(params);
    }   
    
    private void buildPublisherActivity() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lActivityType.class);
        
        MarcxmlControlField field008 = record.getControlField("008");
        if (field008 != null) {        
            BuildParams params = new BuildParams()
                    .setRecord(record)     
                    .setField(field008)
                    .setRelatedEntity(instance)
                    .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
            builder.build(params);
        }
    }
        
}

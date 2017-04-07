/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlToLd4lInstanceBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private final MarcxmlRecord record;

    /**
     * Constructor
     * @throws EntityBuilderException 
     */
    public MarcxmlToLd4lInstanceBuilder(Record record) 
            throws EntityBuilderException {
        this.record = (MarcxmlRecord) record;
        this.entity = new Entity(Ld4lInstanceType.superClass());
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder#build()
     */
    @Override
    public Entity build() throws EntityBuilderException {

        // TODO Add instance subtypes 
        
        buildIdentifiers();
        buildTitles();
        buildWorks();
        buildItem();
        
        return entity;
    }
    
    private void buildIdentifiers() throws EntityBuilderException {

        MarcxmlControlField controlField001 = 
                ((MarcxmlRecord) record).getControlField("001");

        if (controlField001 != null) {
            EntityBuilder.instance(
                    MarcxmlToLd4lIdentifierBuilder.class, controlField001, entity)
                .build();                
        }
        
        // TODO Convert other identifiers from data fields.

    }
    
    private void buildTitles() throws EntityBuilderException { 
        
        // NB There may be multiple, so this isn't sufficient.
        EntityBuilder.instance(MarcxmlToLd4lTitleBuilder.class, record, entity).build();
    }
    
    private void buildWorks() throws EntityBuilderException {
        // NB There are special cases where one Instance has multiple Works.
        
        // For now, the work will take its title from the instance title
        // need to build a new title with all the same elements and attributes,
        // but new resources.
        // Need method of EntityBuilder.clone() or copy?
        
        EntityBuilder.instance(MarcxmlToLd4lWorkBuilder.class, record, entity).build();
    }
    
    private void buildItem() throws EntityBuilderException {
        EntityBuilder.instance(MarcxmlToLd4lItemBuilder.class, record, entity).build();
    }   
        
}

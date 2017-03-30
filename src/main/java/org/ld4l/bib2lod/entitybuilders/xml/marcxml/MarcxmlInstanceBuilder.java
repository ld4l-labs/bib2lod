/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.InstanceClass;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlInstanceBuilder extends MarcxmlBibEntityBuilder {
    
    private final MarcxmlRecord record;

    /**
     * Constructor
     * @throws EntityBuilderException 
     */
    public MarcxmlInstanceBuilder(Record record) 
            throws EntityBuilderException {
        this.record = (MarcxmlRecord) record;
        this.entity = Entity.instance(InstanceClass.superClass());
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder#build()
     */
    @Override
    public Entity build() throws EntityBuilderException {

        // TODO Add instance subtypes 
        buildIdentifiers();
        buildTitles();
//        buildWorks();
        buildItem();
        
        return entity;
    }
    
    private void buildIdentifiers() throws EntityBuilderException {

        MarcxmlControlField controlField001 = 
                ((MarcxmlRecord) record).getControlField("001");

        if (controlField001 != null) {
            EntityBuilder.instance(
                    MarcxmlIdentifierBuilder.class, controlField001, entity)
                .build();                
        }
        
        // TODO Convert other identifiers from data fields.

    }
    
    private void buildTitles() throws EntityBuilderException {       
        // NB There may be multiple, so this isn't sufficient.
        EntityBuilder.instance(MarcxmlTitleBuilder.class, record, entity).build();
    }
    
    private void buildWorks() {
        // NB There are special cases where one Instance has multiple Works.
        throw new RuntimeException("Method not implemented.");
    }
    
    private void buildItem() throws EntityBuilderException {
        EntityBuilder.instance(MarcxmlItemBuilder.class, record, entity).build();
    }   
        
}

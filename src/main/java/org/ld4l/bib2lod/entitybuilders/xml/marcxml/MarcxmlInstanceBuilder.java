/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.entities.Item;
import org.ld4l.bib2lod.entities.Work;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlInstanceBuilder extends MarcxmlEntityBuilder {
 
    private Instance instance;
    private Work work;
    private Item item;

    /**
     * Constructor
     * @throws EntityBuilderException 
     */
    public MarcxmlInstanceBuilder(MarcxmlRecord record) 
            throws EntityBuilderException {
        super(record, null, null);
        this.instance = new Instance();
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder#build()
     */
    @Override
    public List<Entity> build() throws EntityBuilderException {

        List<Entity> entities = new ArrayList<Entity>();
        convertLeader();
        
        // TODO Need to build work first, since some values affect the work
        // as well as or instead of the instance. E.g., language value in 
        // control field 008.
        // Build the work, assign to this.work
        // Same for item
        
        entities.addAll(convertControlFields());
        entities.addAll(convertDataFields());         
        entities.add(instance);
        return entities;
    }
    
    private void convertLeader() {
        // TODO
    }
    
    /**
     * Convert this Instance's controlfields
     * @throws EntityBuilderException
     */
    private List<Entity> convertControlFields() throws EntityBuilderException {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        MarcxmlControlField controlField001 = record.getControlField("001");
        
        if (controlField001 != null) {
            entities.addAll(new MarcxmlIdentifierBuilder(controlField001, instance)
                    .build());    
            
        }
   
        // TODO Other control fields. Some affect work as well as instance
        // (e.g., language value in 008)
        
        return entities;
    }
    
    /**
     * Convert this Instance's datafields
     * @return
     * @throws EntityBuilderException
     */
    private List<Entity> convertDataFields() throws EntityBuilderException {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        //entities.addAll(new MarcxmlTitleBuilder(record, instance).build());

        return entities;
    }
        
}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.ontology.ItemClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlItemBuilder extends MarcxmlEntityBuilder {
    
    private Entity instance;
    private MarcxmlRecord record;
    
    /**
     * Constructor
     */
    public MarcxmlItemBuilder(Record record, Entity instance) {
        this.instance = instance;
        this.record = (MarcxmlRecord) record;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity item = Entity.instance(ItemClass.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addChild(
                OntologyProperty.HAS_ITEM.link(), item);
        return item; 
    }

}

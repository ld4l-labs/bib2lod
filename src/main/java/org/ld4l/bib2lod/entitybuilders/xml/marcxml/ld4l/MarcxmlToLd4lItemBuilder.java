package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lItemBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private final Entity instance;
    private final MarcxmlRecord record;
    
    /**
     * Constructor
     */
    public MarcxmlToLd4lItemBuilder(Record record, Entity instance) {
        this.instance = instance;
        this.record = (MarcxmlRecord) record;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity item = new Entity(Ld4lItemType.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addChild(Ld4lObjectProp.HAS_ITEM, item);
                
        return item; 
    }

}

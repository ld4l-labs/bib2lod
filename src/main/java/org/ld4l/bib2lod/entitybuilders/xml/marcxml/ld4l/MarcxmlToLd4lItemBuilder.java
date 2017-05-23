package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lItemBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private Entity instance;
    // Not sure if record is needed
    // private MarcxmlRecord record;
    private Entity item;
    

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        // this.record = (MarcxmlRecord) params.getRecord();
        this.instance = params.getRelatedEntity();
        this.item = new Entity(Ld4lItemType.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
                
        return item; 
    }

}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lItemBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private Entity instance;
    // Record will be needed to get other Item properties - e.g., shelf marks
    // and bar codes
    private MarcxmlRecord record;
    private Entity item;
    

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        this.record = (MarcxmlRecord) params.getRecord();
  
        this.item = new Entity(Ld4lItemType.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        this.instance = params.getRelatedEntity();
        if (instance != null) {
            instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
        }
                
        return item; 
    }

}

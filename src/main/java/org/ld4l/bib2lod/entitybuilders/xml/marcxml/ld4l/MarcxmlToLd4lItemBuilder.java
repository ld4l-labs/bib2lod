package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lItemBuilder extends BaseEntityBuilder {
    
    private Entity instance;
    // Record will be needed to get other Item properties - e.g., shelf marks
    // and bar codes
    private MarcxmlRecord record;
    private Entity item;
    

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.instance = params.getRelatedEntity();        
        if (instance == null) {
            throw new EntityBuilderException(
                    "A related instance is required to build an item.");
        }
        
        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build an item.");
        }
        
        this.item = new Entity(Ld4lItemType.superClass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
                
        return item; 
    }

}

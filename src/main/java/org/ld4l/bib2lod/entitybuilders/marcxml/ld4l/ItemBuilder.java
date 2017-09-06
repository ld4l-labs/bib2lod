package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

public class ItemBuilder extends BaseEntityBuilder {
    
    private Entity instance;
    private Entity item;
    // Record needed to get other Item properties - e.g., shelf marks and
    // barcodes
    private MarcxmlRecord record;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        parseBuildParams(params);
        
        this.item = new Entity(Ld4lItemType.superclass());
        
        // TODO Fill in other data about Item from other fields in the record
        
        instance.addRelationship(Ld4lObjectProp.HAS_ITEM, item);
                
        return item; 
    }
    
    private void reset() {
        this.instance = null;
        this.item = null;
        this.record = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.instance = params.getParent();        
        if (instance == null) {
            throw new EntityBuilderException(
                    "A related instance is required to build an item.");
        }
        
        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build an item.");
        }        
    }

}

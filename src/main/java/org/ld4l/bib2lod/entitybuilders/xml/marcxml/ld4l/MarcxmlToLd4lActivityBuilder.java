package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.Map;

import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lActivityBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private Entity bibEntity;
    private MarcxmlRecord record;
    private Entity activity;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        this.bibEntity = params.getRelatedEntity();
        this.record = (MarcxmlRecord) params.getRecord();
        activity = new Entity(Ld4lActivityType.superClass());
        
        // add year and place from 008
        
        return activity;
    }


}

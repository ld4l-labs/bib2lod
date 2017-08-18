package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlTaggedField;

public class ActivityBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.superClass();
    
    protected Entity bibEntity;
    protected MarcxmlRecord record;
    protected MarcxmlTaggedField field;
    protected Entity activity;
    protected Ld4lActivityType type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
               
        this.activity = null;
        this.type = getType();
        
        processBuildParams(params);
        
        build();
        
        activity.addAttribute(Ld4lDatatypeProp.LABEL, 
                new Attribute(type.label()));        
        
        bibEntity.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
        
        return activity;
    }
    
    private void processBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.bibEntity = params.getParentEntity();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related entity is required to build an activity.");
        }

        // May not ever need record - in any case, not required.
        this.record = (MarcxmlRecord) params.getRecord();

        RecordField field = params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "A field is required to build an activity.");
        }
        
        if (! (field instanceof MarcxmlTaggedField)) {
            throw new EntityBuilderException("A data field or control field " +
                    "is required to build an activity");
        }
        
        this.field = (MarcxmlTaggedField) field;
 
    }
    
    protected void build() throws EntityBuilderException {
        
    }
  
    protected Ld4lActivityType getType() {
        return TYPE;
    }

}

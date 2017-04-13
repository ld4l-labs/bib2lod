package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlToLd4lActivityBuilder extends MarcxmlToLd4lEntityBuilder {
    
    private Entity bibEntity;
    private MarcxmlRecord record;
    private MarcxmlField field;
    private Entity activity;
    private Type type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        this.bibEntity = params.getRelatedEntity();
        this.record = (MarcxmlRecord) params.getRecord();
        this.field = (MarcxmlField) params.getField();
        
        Type typeParam = params.getType();
        this.type = typeParam != null ? typeParam : Ld4lActivityType.superClass();
        this.activity = new Entity(type);
    
        // Add publication year and place from 008
        // TODO Move to a method once we see what other attributes we need to
        // deal with.
        addAgent();
        addDate();
        addLocation();

        bibEntity.addChild(Ld4lObjectProp.HAS_ACTIVITY, activity);
        return activity;
    }
    
    private void addAgent() {
        // Method not yet implemented
    }
    
    private void addLocation() {

        if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
            if (field instanceof MarcxmlControlField && 
                    ((MarcxmlControlField) field).getControlNumber().equals("008")) {    
                // We know that field.getTextValue() is not empty due to 
                // earlier validity checks.
                String location = field.getTextValue().substring(15,18);
                if (!location.isEmpty()) {
                    activity.addExternal(Ld4lObjectProp.IS_AT_LOCATION, 
                            Ld4lNamespace.LC_COUNTRIES.uri() + location);
                }
            }
        }       
    }
    
    private void addDate() {

        if (type.equals(Ld4lActivityType.PUBLISHER_ACTIVITY)) {
            if (field instanceof MarcxmlControlField && 
                    ((MarcxmlControlField) field).getControlNumber().equals("008")) {
                // We know that field.getTextValue() is not empty due to 
                // earlier validity checks.
                String year = field.getTextValue().substring(7,11);
                if (! year.isEmpty()) {
                    activity.addAttribute(Ld4lDatatypeProp.DATE, year);
                }
            }
        }        
    }

}

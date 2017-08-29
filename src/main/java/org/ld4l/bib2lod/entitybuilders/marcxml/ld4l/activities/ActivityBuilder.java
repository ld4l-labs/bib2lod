package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import java.util.ArrayList;
import java.util.List;

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
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlTaggedField;

public class ActivityBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.superClass();

    protected Entity activity;
    protected Entity parent;
    protected MarcxmlTaggedField field;
    protected List<MarcxmlSubfield> subfields;
    protected MarcxmlRecord record;
    protected Ld4lActivityType type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
               
        reset();      
        parseBuildParams(params);
        
        build();
        
        // The build() method may return without creating an Activity.
        if (activity != null) {
            activity.addAttribute(Ld4lDatatypeProp.LABEL, 
                    new Attribute(type.label()));        
            
            parent.addRelationship(Ld4lObjectProp.HAS_ACTIVITY, activity);
        }
        
        return activity;
    }
    
    private void reset() {
        this.activity = null;
        this.parent = null;
        this.field = null;
        this.subfields = new ArrayList<>();
        this.record = null;
        this.type = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {

        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "A parent entity is required to build an activity.");
        }

        // May not ever need record - in any case, not required.
        this.record = (MarcxmlRecord) params.getRecord();

        RecordField field = params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "A field is required to build an activity.");
        }
        
        if (! (field instanceof MarcxmlTaggedField)) {
            throw new EntityBuilderException("A data field or control " + 
                    "field is required to build an activity");
        }
        
        this.field = (MarcxmlTaggedField) field;
        
        /* *** KLUGE: this needs to be a list of MarcxmlSubfields in order
         * to get the codes, but in BuildParams it's just a list of 
         * RecordFields. Figure out how to handle this better.
         */
        for (RecordField subfield : params.getSubfields()) {
            this.subfields.add((MarcxmlSubfield) subfield);
        }
    }
    
    protected void build() throws EntityBuilderException {
        // TODO If we don't use this, make ActivityBuilder abstract.
        throw new EntityBuilderException("Method not implemented.");
    }

}

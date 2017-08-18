package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class PublisherActivityBuilder extends ProviderActivityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.PUBLISHER_ACTIVITY;
            
  
    @Override
    public void build() throws EntityBuilderException {
        
        if (field.getTag() == 8) {
            convert_008();
        } else if (field.getTag() == 260) {
            convert_260();
        }
        
    }

    private void convert_008() {
        
        MarcxmlControlField field008 = (MarcxmlControlField) field;
        
        this.activity = new Entity(TYPE);
      
        // Mark status as current
        activity.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                Ld4lNamedIndividual.CURRENT);

        // Publication date
        String year = field008.getTextSubstring(7, 11);
        if (! StringUtils.isBlank(year)) {
          activity.addAttribute(
                  Ld4lDatatypeProp.DATE, year, BibDatatype.EDTF);
        } 
        
        // Publication location
        String location = field008.getTextSubstring(15, 18);
        if (! StringUtils.isBlank(location)) {
            // Two or three characters - "ne", "nyu"
            location = location.trim();
            activity.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, 
                    Ld4lNamespace.LC_COUNTRIES.uri() + location);
        }        
    }

    private void convert_260() 
            throws EntityBuilderException {
        
        MarcxmlDataField datafield = (MarcxmlDataField) field;
        
        // Get all publisher activities built so far
        List<Entity> publisherActivities = bibEntity.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY, 
                    Ld4lActivityType.PUBLISHER_ACTIVITY);

        int field_260_Count = record.getDataFields(260).size();
        Integer ind1 = datafield.getFirstIndicator();
        // This is the current publisher activity
        if ( (ind1 != null && ind1 == 3) || 
                (ind1 == null && field_260_Count == 1) ) {
            /*
             * Get the previously created current activity (from 008) and
             * assign the subfield data to it rather than creating a new
             * activity. A current activity should always exist, but in 
             * case it doesn't, we'll create a new activity below.
             */
            for (Entity entity : publisherActivities) {
               if (entity.hasExternal(Ld4lObjectProp.HAS_STATUS, 
                       Ld4lNamedIndividual.CURRENT.uri())) {
                   this.activity = entity;
                   break;                  
               }
            }
        } 
        
        if (this.activity == null) {
            this.activity = new Entity(TYPE);
        }

        buildDate_260(datafield);
        buildLocation_260(datafield); 
        buildAgent_260(datafield);
  
        // TODO 264 with indicator for publisher - otherwise a different type,
        // but otherwise the same (mostly?)
    }

    private void buildDate_260(MarcxmlDataField field) {

        MarcxmlSubfield subfield = field.getSubfield('c');
        if (subfield == null) {
            return;
        }
        String date = subfield.getTrimmedTextValue();
        // Unlike the controlled 008 date, the 260$c date value is an 
        // untyped literal.
        activity.addAttribute(Ld4lDatatypeProp.DATE, date);
    }
    
    private void buildLocation_260(MarcxmlDataField field) 
            throws EntityBuilderException {  
        
        // TODO 260 and 264 same
        
        EntityBuilder builder = getBuilder(Ld4lLocationType.superClass());
        
        MarcxmlSubfield subfield = field.getSubfield('a');       
        if (subfield == null) {
            return;
        }
        
        BuildParams params = new BuildParams() 
              .setParentEntity(activity)
              .setSubfield(subfield)
              .setType(Ld4lLocationType.superClass());
        builder.build(params);
    }
    
    private void buildAgent_260(MarcxmlDataField field)
            throws EntityBuilderException {  
        
        // TODO 260 and 264 same
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.superClass());
        
        MarcxmlSubfield subfield = field.getSubfield('b');
        
        if (subfield == null) {
            return;
        }

        BuildParams params = new BuildParams() 
              .setParentEntity(activity)
              .setSubfield(subfield)
              .setType(Ld4lAgentType.superClass());
        builder.build(params);      
    }
    
    @Override
    protected Ld4lActivityType getType() {
        return TYPE;
    }

}

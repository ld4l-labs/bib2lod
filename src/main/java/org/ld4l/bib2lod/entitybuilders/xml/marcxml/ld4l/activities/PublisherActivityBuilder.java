package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;

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
        List<Entity> publisherActivities = parent.getChildren(
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

        buildLocation(datafield, 'a'); 
        buildAgent(datafield, 'b');
        buildDate(datafield, 'c');
  
        // TODO 264 with indicator for publisher - otherwise a different type,
        // but otherwise the same (mostly?)
    }


    @Override
    protected Ld4lActivityType getType() {
        return TYPE;
    }

}

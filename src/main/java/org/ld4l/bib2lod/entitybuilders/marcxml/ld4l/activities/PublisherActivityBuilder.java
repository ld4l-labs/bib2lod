package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

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
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class PublisherActivityBuilder extends ProviderActivityBuilder {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.PUBLISHER_ACTIVITY;
            
  
    @Override
    public void build() throws EntityBuilderException {
   
        this.type = TYPE;
        
        if (field.getTag() == 8) {
            convert_008();
        } else if (field.getTag() == 260) {
            convert_260();
        }           
    }

    private void convert_008() {
        
        MarcxmlControlField field_008 = (MarcxmlControlField) field;
        
        this.activity = new Entity(TYPE);
      
        // Mark status as current
        activity.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                Ld4lNamedIndividual.CURRENT);

        // Publication date
        String year = field_008.getTextSubstring(7, 11);
        if (! StringUtils.isBlank(year)) {
          activity.addAttribute(
                  Ld4lDatatypeProp.DATE, year, BibDatatype.EDTF);
        } 
        
        // Publication location
        String location = field_008.getTextSubstring(15, 18);
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
        
        this.activity = new Entity(TYPE);

        // Set current publisher activity status
        Integer ind1 = datafield.getFirstIndicator();
        // First indicator == 3
        if ( (ind1 != null && ind1 == 3) ||
                // This is the only 260
                record.getDataFields(260).size() == 1) {  
            activity.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                    Ld4lNamedIndividual.CURRENT);
        }

        buildLocation(MarcxmlSubfield.getSubfield(subfields, 'a')); 
        buildAgent(MarcxmlSubfield.getSubfield(subfields, 'b'));
        buildDate(MarcxmlSubfield.getSubfield(subfields, 'c'));
  
        // TODO 264 with indicator for publisher - otherwise a different type,
        // but otherwise the same (mostly?)
    }
    
}

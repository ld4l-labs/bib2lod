package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
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
        
        switch (field.getTag()) {
        case "008": 
            convert008();
            break;
        case "260":
            convert260();
            break;
        default:
            break;
        }
         
    }

    private void convert008() {
        
        MarcxmlControlField controlfield = (MarcxmlControlField) field;
        
        this.activity = new Entity(TYPE);
      
        // Mark status as current
        activity.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                Ld4lNamedIndividual.CURRENT);

        // Publication dates
        String year1 = controlfield.getTextSubstring(7, 11);
        if (! StringUtils.isBlank(year1)) {
          activity.addAttribute(
                  Ld4lDatatypeProp.DATE, year1, BibDatatype.EDTF);
        } 
        String year2 = controlfield.getTextSubstring(11, 15);
        if (! StringUtils.isBlank(year2)) {
          activity.addAttribute(
                  Ld4lDatatypeProp.DATE, year2, BibDatatype.EDTF);
        } 
        
        // Publication location
        String location = controlfield.getTextSubstring(15, 18);
        if (! StringUtils.isBlank(location)) {
            // Two or three characters - "ne", "nyu"
            location = location.trim();
            activity.addExternalRelationship(Ld4lObjectProp.HAS_LOCATION, 
                    Ld4lNamespace.LC_COUNTRIES.uri() + location);
        }        
    }

    private void convert260() 
            throws EntityBuilderException {
        
        MarcxmlDataField datafield = (MarcxmlDataField) field;
        
        this.activity = new Entity(TYPE);

        // Set current publisher activity status
        Integer ind1 = datafield.getFirstIndicator();
        // First indicator == 3
        if ( (ind1 != null && ind1 == 3) ||
                // This is the only 260
                record.getDataFields("260").size() == 1) {  
            activity.addExternalRelationship(Ld4lObjectProp.HAS_STATUS, 
                    Ld4lNamedIndividual.CURRENT);
        }

        buildLocation(MarcxmlSubfield.getSubfield(subfields, 'a')); 
        buildAgent(MarcxmlSubfield.getSubfield(subfields, 'b'));
        buildUntypedDate(MarcxmlSubfield.getSubfield(subfields, 'c'));
  
        // TODO 264 with indicator for publisher - otherwise a different type,
        // but otherwise the same (mostly?)
    }
    
    @Override
    protected void buildUntypedDate(MarcxmlSubfield subfield) 
            throws EntityBuilderException {
        
        if (subfield == null) {
            return;
        }
        
        String date = subfield.getTextValue();
        String[] dates = date.split(" &copy;");
        
        activity.addAttribute(Ld4lDatatypeProp.DATE, 
                XmlTextElement.trimFinalPunctAndWhitespace(dates[0]));
        
        if (dates.length > 1) {
            String copyright = dates[1].trim();
            EntityBuilder builder = getBuilder(Ld4lActivityType.defaultType());
            BuildParams params = new BuildParams() 
                    .setParent(parent)
                    .setValue(copyright)
                    .setProperty(Ld4lDatatypeProp.DATE)
                    .setType(Ld4lActivityType.COPYRIGHT_HOLDER_ACTIVITY);
            builder.build(params);
        }
    }
    
}

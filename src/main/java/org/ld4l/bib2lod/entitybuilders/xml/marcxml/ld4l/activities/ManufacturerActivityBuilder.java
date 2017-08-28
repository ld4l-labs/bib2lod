package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;

public class ManufacturerActivityBuilder extends ProviderActivityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.MANUFACTURER_ACTIVITY;
    
    @Override
    public void build() throws EntityBuilderException {
          
        this.type = TYPE;
        
        // Not sure if any other tags apply
        if (field.getTag() == 260) {
            convert_260();
        }   
    }
    
    private void convert_260() 
            throws EntityBuilderException {
        
        MarcxmlDataField datafield = (MarcxmlDataField) field;

        this.activity = new Entity(TYPE);
        
        buildLocation(datafield.getSubfield('e')); 
        buildAgent(datafield.getSubfield('f'));
        buildDate(datafield.getSubfield('g'));
    }
    
}

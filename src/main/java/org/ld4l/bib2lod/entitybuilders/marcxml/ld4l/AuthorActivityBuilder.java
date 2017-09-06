package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities.ActivityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;

public class AuthorActivityBuilder extends ActivityBuilder {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Ld4lActivityType TYPE = 
            Ld4lActivityType.AUTHOR_ACTIVITY;
            
  
    @Override
    public void build() throws EntityBuilderException {
   
        this.type = TYPE;
        
        switch (field.getTag()) {
        case "100": 
            convert_100();
            break;
        default:
            break;
        }
    }
        
 
    private void convert_100() {
        
        MarcxmlDataField datafield = (MarcxmlDataField) field;
        
        this.activity = new Entity(TYPE);
      

    }



    
}

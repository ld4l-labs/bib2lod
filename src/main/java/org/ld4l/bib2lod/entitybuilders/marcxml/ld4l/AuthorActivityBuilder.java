package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities.ActivityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;

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
        
 
    private void convert_100() throws EntityBuilderException {

        this.activity = new Entity(TYPE);
        EntityBuilder builder = getBuilder(Ld4lAgentType.defaultType());
        BuildParams params = new BuildParams()
                .setField(field)
                .setParent(activity);
        builder.build(params);
    }
   
}

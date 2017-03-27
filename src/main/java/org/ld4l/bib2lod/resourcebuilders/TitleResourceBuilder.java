package org.ld4l.bib2lod.resourcebuilders;

import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.entities.Title;
import org.ld4l.bib2lod.entities.TitleElement;

public class TitleResourceBuilder extends BaseResourceBuilder {

    public TitleResourceBuilder(Title entity, Model model) {
        super(entity, model);
    }
    
    public Resource build() {
        super.build();
        
        for (TitleElement element : ((Title) entity).getTitleElements()) {

        }
        
        // If only one title, use hasPreferredTitle predicate
        
        return resource;     
    }


}

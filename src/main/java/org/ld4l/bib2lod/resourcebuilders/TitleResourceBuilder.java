package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.entities.Title;

public class TitleResourceBuilder extends BaseResourceBuilder {

    public TitleResourceBuilder(Title entity, Model model) {
        super(entity, model);
    }
    
    public Resource build() {
        super.build();
        return resource;     
    }


}

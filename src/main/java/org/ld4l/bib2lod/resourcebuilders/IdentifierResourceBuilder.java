package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.entities.Entity;

public class IdentifierResourceBuilder extends BaseResourceBuilder {

    /**
     * Constructor
     */
    public IdentifierResourceBuilder(Entity entity, Model model) {
        super(entity, model);
    }
    
    public Resource build() {
        super.build();
        return resource;     
    }

}

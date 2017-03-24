/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.uris.UriService;

/**
 * An abstract implementation.
 */
public abstract class BaseResourceBuilder implements ResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    protected Entity entity;
    protected Resource resource;

    /**
     * Constructor
     * @param configuration 
     * @param entity 
     */
    public BaseResourceBuilder(Entity entity) {
        this.entity = entity;

    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.resourcebuilders.ResourceBuilder#build()
     */
    @Override
    public Resource build() {
        Model model = ModelFactory.createDefaultModel();
        this.resource = model.createResource(UriService.getUri(entity));
        addTypeAssertions();
        return resource;
    }
    
    /**
     * Add assertions of class membership to the Resource.
     */
    private void addTypeAssertions() {
        for (String uri : entity.getTypes()) {
            // TODO If we read ontologies into an OntModel, we can get the
            // property from the model using Model.createProperty(type);
            resource.addProperty(RDF.type, ResourceFactory.createResource(uri));
        }
    }

    
    // ----------------------------------------------------------------------
    // Utilities
    // ----------------------------------------------------------------------
    
    
   
// TODO Wait to define these until we see in the subclasses what we need.
//    protected Resource createResource(String uri, Model model) {
//        return model.createResource(uri);
//    }
//    
//    protected Property createProperty(String uri, Model model) {
//        return model.createProperty(uri);
//    }
//    
//    protected void addTypes(Resource resource, Entity entity) {
//        for (String type : entity.getTypes()) {
//            resource.addProperty(RDF.type, model.createProperty(type));
//        }
//    }

}

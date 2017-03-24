/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
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
    protected Model model;

    /**
     * Constructor
     * @param configuration 
     * @param entity 
     * @param model 
     */
    public BaseResourceBuilder(Entity entity, Model model) {
        this.entity = entity;
        this.model = model;
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.resourcebuilders.ResourceBuilder#build()
     */
    @Override
    public Resource build() {
        this.resource = model.createResource(UriService.getUri(entity));
        addTypeAssertions();
        return resource;
    }
    
    /**
     * Add assertions of class membership to the Resource.
     */
    private void addTypeAssertions() {
        for (String uri : entity.getTypes()) {
            // TODO If we read the ontologies into an OntModel, we can get the
            // property from the model using Model.createProperty(type);
            addObjectPropertyAssertion(RDF.type, uri);
        }
    }
    
    // ----------------------------------------------------------------------
    // Utilities
    // ----------------------------------------------------------------------
    
    // TODO All of this would be better if we read the ontologies into an
    // OntModel and get the properties and resources from there.
    
    /**
     * Adds an object property assertion to this Resource
     */
    protected void addObjectPropertyAssertion(String propertyUri, String objectUri) {
        addObjectPropertyAssertion(resource, ResourceFactory.createProperty(propertyUri), objectUri);
    }
    
    /**
     * Adds an object property assertion to this Resource
     */
    protected void addObjectPropertyAssertion(Property property, String objectUri) {
        addObjectPropertyAssertion(resource, property, objectUri);
    }
    
    /**
     * Adds an object property assertion to the specified Resource
     */
    protected static void addObjectPropertyAssertion(Resource resource, String propertyUri, String objectUri) {
        addObjectPropertyAssertion(resource, ResourceFactory.createProperty(propertyUri), objectUri);
    }
    
    /**
     * Adds an object property assertion to the specified Resource
     */    
    protected static void addObjectPropertyAssertion(Resource resource, Property property, String objectUri) {
        resource.addProperty(property, ResourceFactory.createResource(objectUri));
    }
    
}

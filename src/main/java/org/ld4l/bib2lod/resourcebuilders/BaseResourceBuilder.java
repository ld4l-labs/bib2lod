/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.BaseEntity;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Type;
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
        
        addRdfsLabel();
        
        addRdfValue();

        return resource;
    }

    private void addRdfsLabel() {
        String label = entity.getRdfsLabel();
        if (label != null) {
            resource.addProperty(RDFS.label, label);
        }
    }
    
    private void addRdfValue() {
        String value = entity.getRdfValue();
        if (value != null) {
            resource.addProperty(RDF.value, value);
        }
    }
    
    /**
     * Add assertions of class membership to the Resource.
     */
    private void addTypeAssertions() {
        for (Type type : entity.getTypes()) {
            // TODO If we read the ontologies into an OntModel, we can get the
            // property from the model using Model.createProperty(type);
            
            // TODO Don't add supertype unless there are no other types
            
            addObjectPropertyAssertion(RDF.type, type.uri());
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
        resource.addProperty(ResourceFactory.createProperty(propertyUri), 
                ResourceFactory.createResource(objectUri));
    }

    /**
     * Adds an object property assertion to this Resource
     */
    protected void addObjectPropertyAssertion(Property property, String objectUri) {
        resource.addProperty(property, 
                ResourceFactory.createResource(objectUri));
    }
    
    /**
     * Adds a datatype property assertion to this Resource
     */
    protected void addDatatypePropertyAssertion(String propertyUri, String value) {
        resource.addProperty(ResourceFactory.createProperty(propertyUri), value);
    }

    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.modelbuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseModelBuilder implements ModelBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    protected Entity entity;
    protected Model model;

    /**
     * Constructor
     */
    public BaseModelBuilder(Entity entity) {
        this.entity = entity;
        this.model = ModelFactory.createDefaultModel();
    }
    
    // Use model.createResource() rather than ResourceFactory.createResource(),
    // since the former will reuse a previously created Resource.
    protected Resource createResource(String uri, Model model) {
        return model.createResource(uri);
    }
    
    // Use model.createProperty() rather than ResourceFactory.createProperty(),
    // since the former will reuse a previously created Property.
    protected Property createProperty(String uri, Model model) {
        return model.createProperty(uri);
    }
    
    protected void addTypes(Resource resource) {
        for (String type : entity.getTypes()) {
            resource.addProperty(RDF.type, model.createProperty(type));
        }
    }

}

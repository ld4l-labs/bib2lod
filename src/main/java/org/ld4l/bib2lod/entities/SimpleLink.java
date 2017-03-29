package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

/**
 * Wraps a Property to link an Entity to a List of other Entities.
 */
public class SimpleLink implements Link {
    
    private final Property property;
    
    /**
     * Constructor
     */
    public SimpleLink(Property property) {
        this.property = property;
    }
    
    /**
     * Constructor
     */
    public SimpleLink(String uri) {
        this.property = ResourceFactory.createProperty(uri);
    }
    
    public Property getProperty() {
        return property;
    }

}

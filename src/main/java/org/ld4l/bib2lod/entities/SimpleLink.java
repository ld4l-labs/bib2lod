package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Property;
import org.ld4l.bib2lod.ontology.OntologyProperty;

/**
 * Wraps a Property to link an Entity to a List of other Entities.
 */
public class SimpleLink implements Link {
    
    private final Property property;
    
    /**
     * Constructors
     */
    public SimpleLink(Property property) {
        this.property = property;
    }
    
    public SimpleLink(OntologyProperty ontProperty) {
        this.property = ontProperty.property();
    }
    
    
    public Property getProperty() {
        return property;
    }

}

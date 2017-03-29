package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Property;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Represents a property that links two Entities.
 */
public interface Link {
    
    /**
     * Factory method
     */
    public static Link instance(Property property) {
        return Bib2LodObjectFactory.instance().createLink(property);
    }

    public Property getProperty();

}

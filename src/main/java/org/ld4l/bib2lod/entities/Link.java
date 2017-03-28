package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Property;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Represents a property that links two Entities.
 */
public interface Link {
    
    /**
     * Factory methods
     */
    public static Entity instance(Type type) {
        return Bib2LodObjectFactory.instance().createEntity(type);
    }
    
    public static Entity instance(String uri) {
        return Bib2LodObjectFactory.instance().createEntity(uri);
    }

    
    public Property getProperty();

}

package org.ld4l.bib2lod.resources;

import org.apache.jena.rdf.model.Property;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Represents a property that links two Entities.
 */
public interface Link {
    
    /**
     * Factory method
     */
    public static Entity instance(Type type) {
        return Bib2LodObjectFactory.instance().createEntity(type);
    }
    
    /**
     * Factory method
     */
    public static Entity instance(String uri) {
        return Bib2LodObjectFactory.instance().createEntity(uri);
    }

    public Property getProperty();

}

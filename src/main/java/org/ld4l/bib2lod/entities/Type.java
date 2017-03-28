package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Represents a type of an Entity.
 */
public interface Type {

    /**
     * Factory method
     */
    public static Type instance(Resource ontClass) {
        return Bib2LodObjectFactory.instance().createType(ontClass);
    }
    
    /**
     * Factory method
     */
    public static Type instance(String uri) {
        return Bib2LodObjectFactory.instance().createType(uri);
    }

    public String getUri();

    public Resource getOntClass();

}

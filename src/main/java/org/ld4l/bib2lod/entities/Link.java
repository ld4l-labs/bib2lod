package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Property;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.OntologyProperty;

/**
 * Represents a property that links two Entities.
 */
public interface Link {
    
    /**
     * Factory method
     */  
    public static Link instance(OntologyProperty property) {
        return Bib2LodObjectFactory.instance().createLink(property);
    }

    public Property getProperty();

}

package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entities.Type;

/**
 * Wraps a Resource representing a class in the ontology.
 */
public class SimpleType implements Type {
    
    // Could use OntClass instead of Resource if there are methods that would
    // be useful. Probably not useful unless/until we read in the ontology to
    // get subclasses and other axioms.
    private Resource ontClass;
    
    /**
     * Constructor
     */
    public SimpleType(Resource ontClass) {
        this.ontClass = ontClass;
    }
    
    /**
     * Constructor
     */
    public SimpleType(String uri) {
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public Resource getOntClass() {
        return ontClass;
    }

    @Override
    public String getUri() {
        return ontClass.getURI();
    }

}

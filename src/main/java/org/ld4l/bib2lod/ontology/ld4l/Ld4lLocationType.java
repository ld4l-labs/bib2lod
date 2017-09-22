package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Location types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lLocationType implements Type {
        
    /* List in alpha order */
    LOCATION(Ld4lNamespace.PROV, "Location");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lLocationType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 
    
    @Override
    public Type superclass() {
        return defaultType();
    }

    public static Type defaultType() {
        return LOCATION;
    }
}

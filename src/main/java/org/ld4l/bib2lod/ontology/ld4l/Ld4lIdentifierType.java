package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Identifier types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lIdentifierType implements Type {

    IDENTIFIER(Ld4lNamespace.BIBFRAME, "Identifier"),
    LOCAL(Ld4lNamespace.BIBFRAME, "Local");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lIdentifierType(Ld4lNamespace namespace, String name) {
        this.uri = namespace.uri() + name;
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

    public static Type superClass() {
        return IDENTIFIER;
    }

}

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Title types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lTitleType implements Type {

    /* List in alpha order */
    ABBREVIATED_TITLE(Ld4lNamespace.BIBLIOTEKO, "AbbreviatedTitle"),
    TITLE(Ld4lNamespace.BIBFRAME, "Title");
    
    private static final Type DEFAULT_TYPE = TITLE;
 
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lTitleType(Namespace namespace, String localName) {
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
        return DEFAULT_TYPE;
    }

    public static Type defaultType() {
        return DEFAULT_TYPE;
    }

}

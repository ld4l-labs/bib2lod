package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Title types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lTitleType implements Type {

    ABBREVIATED_TITLE(Ld4lNamespace.LD4L, "AbbreviatedTitle"),
    TITLE(Ld4lNamespace.BIBFRAME, "Title");
 
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lTitleType(Ld4lNamespace namespace, String localName) {
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

    public static Type superClass() {
        return TITLE;
    }

}

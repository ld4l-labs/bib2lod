package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Work types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lWorkType implements Type {

    TEXT(Ld4lNamespace.BIBFRAME, "Text"),
    WORK(Ld4lNamespace.BIBFRAME, "Work");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lWorkType(Ld4lNamespace namespace, String name) {
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
        return WORK;
    }
}

package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum TitleType implements Type {

    ABBREVIATED_TITLE(Namespace.LD4L, "AbbreviatedTitle"),
    TITLE(Namespace.BIBFRAME, "Title");
 
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    TitleType(Namespace namespace, String localName) {
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

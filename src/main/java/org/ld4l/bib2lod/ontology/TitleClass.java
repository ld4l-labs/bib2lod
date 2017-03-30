package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum TitleClass implements OntologyClass {

    ABBREVIATED_TITLE(Namespace.LD4L, "AbbreviatedTitle"),
    TITLE(Namespace.BIBFRAME, "Title");
 
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    TitleClass(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClassResource() {
        return ontClass;
    } 

    public static Resource superClass() {
        return TITLE.ontClass;
    }

}

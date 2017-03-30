package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum WorkClass implements OntologyClass {

    TEXT(Namespace.BIBFRAME, "Text"),
    WORK(Namespace.BIBFRAME, "Work");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    WorkClass(Namespace namespace, String name) {
        this.uri = namespace.uri() + name;
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

    public static OntologyClass superClass() {
        return WORK;
    }
}

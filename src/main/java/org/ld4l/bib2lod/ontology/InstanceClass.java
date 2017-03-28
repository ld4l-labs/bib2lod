package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum InstanceClass implements OntologyClass {
    
    ARCHIVAL(Namespace.BIBFRAME, "Archival"),
    ELECTRONIC(Namespace.BIBFRAME, "Electronic"),
    INSTANCE(Namespace.BIBFRAME, "Instance"),
    MANUSCRIPT(Namespace.BIBFRAME, "Manuscript"),
    PRINT(Namespace.BIBFRAME, "Print");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    InstanceClass(Namespace namespace, String name) {
        this.uri = namespace.uri() + name;
        ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 
    
    public static Resource superClass() {
        return INSTANCE.ontClass;
    }
}

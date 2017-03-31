package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum InstanceType implements Type {
    
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
    InstanceType(Namespace namespace, String localName) {
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
        return INSTANCE;
    }

}

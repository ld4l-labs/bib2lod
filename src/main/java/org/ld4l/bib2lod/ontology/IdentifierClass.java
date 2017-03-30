package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum IdentifierClass implements OntologyClass {

    IDENTIFIER(Namespace.BIBFRAME, "Identifier"),
    LOCAL(Namespace.BIBFRAME, "Local");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    IdentifierClass(Namespace namespace, String name) {
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

    public static Resource superClass() {
        return IDENTIFIER.ontClass;
    }

}

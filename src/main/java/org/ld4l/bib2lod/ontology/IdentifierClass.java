package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entities.Type;

public enum IdentifierClass implements OntologyClass {

    IDENTIFIER(Namespace.BIBFRAME, "Identifier"),
    LOCAL(Namespace.BIBFRAME, "Local");
    
    private String uri;
    private Resource ontClass;
    private Type type;
    
    /**
     * Constructor
     */
    IdentifierClass(Namespace namespace, String name) {
        this.uri = namespace.uri() + name;
        this.ontClass = ResourceFactory.createResource(uri);
        this.type = Type.instance(ontClass);
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
    public Type type() {
        return type;
    }
    
    public static Resource superClass() {
        return IDENTIFIER.ontClass;
    }

}

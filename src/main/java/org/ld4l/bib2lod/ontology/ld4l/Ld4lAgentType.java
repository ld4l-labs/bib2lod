package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lAgentType implements Type {
    
    /* List in alpha order */
    AGENT(Ld4lNamespace.FOAF, "Agent"),
    FAMILY(Ld4lNamespace.BIBFRAME, "Family"),
    PERSON(Ld4lNamespace.FOAF, "Person"),
    ORGANIZATION(Ld4lNamespace.FOAF, "Organization");
 
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lAgentType(Namespace namespace, String localName) {
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
        return defaultType();
    }

    public static Type defaultType() {
        return AGENT;
    }
}

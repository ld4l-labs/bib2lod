package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lActivityType implements Type {
    
    ACTIVITY(Ld4lNamespace.LD4L, "Activity"),
    PUBLISHER_ACTIVITY(Ld4lNamespace.LD4L, "PublisherActivity");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lActivityType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);        
    }

    @Override
    public String uri() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Resource ontClass() {
        // TODO Auto-generated method stub
        return null;
    }

    public static Type superClass() {
        return ACTIVITY;
    }
}

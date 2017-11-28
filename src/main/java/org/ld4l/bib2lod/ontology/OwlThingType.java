package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;

public enum OwlThingType implements Type {
    
    /* List in alpha order */
    THING(Ld4lNamespace.OWL, "Thing");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    OwlThingType(Namespace namespace, String localName) {
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
        return THING;
    }
}

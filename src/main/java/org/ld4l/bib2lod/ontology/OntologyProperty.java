package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entities.Link;

public enum OntologyProperty {

    IDENTIFIED_BY(Namespace.BIBFRAME, "identifiedBy");
    
    private String uri;
    private Property property;
    private Link link;
    
    /**
     * Constructor
     */
    OntologyProperty(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
        this.link = Link.instance(property);
    }
    
    public String uri() {
        return uri;
    }
    
    public Property property() {
        return property;
    }
    
    public Link link() {
        return link;
    }
    
}

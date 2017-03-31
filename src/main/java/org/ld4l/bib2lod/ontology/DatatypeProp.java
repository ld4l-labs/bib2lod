package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

//TODO Read in the ontology files to either replace or facilitate/enhance this.
//TODO - Probably want this to be an interface, with implementing enums for
//LD4L, BF, etc. Each enum would define, not just properties in that namespace,
//but the entire set of terms defined by the application profile/set of
//target ontologies.
public enum DatatypeProp implements OntologyProp {

    // rdfs:label is an AnnotationProperty, but it doesn't matter here.
    LABEL(Namespace.RDFS, "label"),
    RANK(Namespace.VIVO, "rank"),
    RESPONSIBILITY_STATEMENT(Namespace.BIBFRAME, "responsibilityStatement"),
    // rdf:value is an RDF property, not an owl:DatatypeProperty. However, we 
    // are using it with Literal objects. If it needs to be used with non-literal
    // objects, it can also be defined in ObjectProp.
    VALUE(Namespace.RDF, "value");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    DatatypeProp(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
    }
    
    public String uri() {
        return uri;
    }
    
    public Property property() {
        return property;
    }

}

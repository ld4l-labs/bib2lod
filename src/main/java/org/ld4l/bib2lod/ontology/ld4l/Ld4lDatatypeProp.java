package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.OntologyProp;

/**
 * Enumerates the datatype properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lDatatypeProp implements OntologyProp {

    // rdfs:label is an AnnotationProperty, but it doesn't matter here.
    LABEL(Ld4lNamespace.RDFS, "label"),
    RANK(Ld4lNamespace.VIVO, "rank"),
    RESPONSIBILITY_STATEMENT(Ld4lNamespace.BIBFRAME, "responsibilityStatement"),
    /*
     * rdf:value is an RDF property, not an owl:DatatypeProperty. However, we 
     * are using it with Literal objects. If it needs to be used with non-
     * literal objects, it can also be defined in ObjectProp.
     */
    VALUE(Ld4lNamespace.RDF, "value");
    
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    Ld4lDatatypeProp(Ld4lNamespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.property = ResourceFactory.createProperty(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }
    
    @Override
    public Property property() {
        return property;
    }

}

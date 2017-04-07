package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.OntologyProp;

/**
 * Enumerates the object properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
//TODO Read in the ontology files to either replace or facilitate/enhance this.
//TODO - Probably want this to be an interface, with implementing enums for
//LD4L, BF, etc. Each enum would define, not just properties in that namespace,
//but the entire set of terms defined by the application profile/set of
//target ontologies.
public enum Ld4lObjectProp implements OntologyProp {

    HAS_ITEM(Ld4lNamespace.BIBFRAME, "hasItem"),
    HAS_PART(Ld4lNamespace.DCTERMS, "hasPart"),
    HAS_PREFERRED_TITLE(Ld4lNamespace.LD4L, "hasPreferredTitle"),
    IDENTIFIED_BY(Ld4lNamespace.BIBFRAME, "identifiedBy"),
    INSTANCE_OF(Ld4lNamespace.BIBFRAME, "instanceOf"),
    RESPONSIBILITY_STATEMENT(Ld4lNamespace.BIBFRAME, "responsibilityStatement"),
    TITLE(Ld4lNamespace.BIBFRAME, "title");
   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    Ld4lObjectProp(Ld4lNamespace namespace, String localName) {
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

package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;

//TODO Read in the ontology files to either replace or facilitate/enhance this.
//TODO - Probably want this to be an interface, with implementing enums for
//LD4L, BF, etc. Each enum would define, not just properties in that namespace,
//but the entire set of terms defined by the application profile/set of
//target ontologies.
public enum ObjectProp {

    HAS_ITEM(Namespace.BIBFRAME, "hasItem"),
    HAS_PART(Namespace.DCTERMS, "hasPart"),
    HAS_PREFERRED_TITLE(Namespace.LD4L, "hasPreferredTitle"),
    IDENTIFIED_BY(Namespace.BIBFRAME, "identifiedBy"),
    INSTANCE_OF(Namespace.BIBFRAME, "instanceOf"),
    RESPONSIBILITY_STATEMENT(Namespace.BIBFRAME, "responsibilityStatement"),
    TITLE(Namespace.BIBFRAME, "title");
   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    ObjectProp(Namespace namespace, String localName) {
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

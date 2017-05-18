package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;

/**
 * Enumerates the object properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lObjectProp implements ObjectProp {

    HAS_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "hasActivity"),
    IS_ACTIVITY_OF(Ld4lNamespace.BIBLIOTEKO, "isActivityOf"),
    HAS_LANGUAGE(Ld4lNamespace.DCTERMS, "language"),
    HAS_INSTANCE(Ld4lNamespace.BIBFRAME, "hasInstance"),
    HAS_ITEM(Ld4lNamespace.BIBFRAME, "hasItem"),
    HAS_PART(Ld4lNamespace.DCTERMS, "hasPart"),
    HAS_PREFERRED_TITLE(Ld4lNamespace.BIBLIOTEKO, "hasPreferredTitle"),
    HAS_TITLE(Ld4lNamespace.BIBFRAME, "title"),
    IS_TITLE_OF(Ld4lNamespace.BIBLIOTEKO, "isTitleOf"),
    IDENTIFIES(Ld4lNamespace.BIBFRAME, "identifies"),
    IS_AT_LOCATION(Ld4lNamespace.BIBLIOTEKO, "atLocation"),
    IS_LOCATION_OF(Ld4lNamespace.BIBLIOTEKO, "isLocationOf"),
    IS_IDENTIFIED_BY(Ld4lNamespace.BIBFRAME, "identifiedBy"),
    IS_INSTANCE_OF(Ld4lNamespace.BIBFRAME, "instanceOf"),
    HAS_AGENT(Ld4lNamespace.BIBLIOTEKO, "hasAgent"),
    IS_AGENT_OF(Ld4lNamespace.BIBLIOTEKO, "isAgentOf"),
    HAS_BODY(Ld4lNamespace.OA, "hasBody"),
    HAS_ANNOTATION(Ld4lNamespace.BIBLIOTEKO, "hasAnnotation"),
    HAS_TARGET(Ld4lNamespace.OA, "hasTarget"),
    MOTIVATED_BY(Ld4lNamespace.OA, "motivatedBy"),
    CREATOR(Ld4lNamespace.DCTERMS, "creator");

   
    private String uri;
    private Property property;
    
    /**
     * Constructor
     */
    Ld4lObjectProp(Ld4lNamespace namespace, String localName) {
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

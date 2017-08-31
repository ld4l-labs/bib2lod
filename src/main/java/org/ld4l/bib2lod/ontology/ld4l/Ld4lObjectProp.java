package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.ObjectProp;

/**
 * Enumerates the object properties used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lObjectProp implements ObjectProp {

    /* List in alpha order, use initial verb form (HAS_ or IS_ if not already verbal in any form, including past and passive participles. */
    CREATED(Ld4lNamespace.DCTERMS, "created"),
    GENRE_FORM(Ld4lNamespace.BIBFRAME, "genreForm"),
    HAS_ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "hasActivity"),
    HAS_ADMIN_METADATA(Ld4lNamespace.BIBFRAME, "adminMetadata"),
    HAS_AGENT(Ld4lNamespace.BIBLIOTEKO, "hasAgent"),
    HAS_ANNOTATION(Ld4lNamespace.BIBLIOTEKO, "isTargetOf"),
    HAS_BODY(Ld4lNamespace.OA, "hasBody"),
    HAS_CREATOR(Ld4lNamespace.DCTERMS, "creator"),
    HAS_DESCRIPTION_CONVENTIONS(Ld4lNamespace.BIBFRAME, "descriptionConventions"),
    HAS_DESCRIPTION_MODIFIER(Ld4lNamespace.BIBFRAME, "descriptionModifier"),
    HAS_ELECTRONIC_LOCATOR(Ld4lNamespace.BIBFRAME, "electronicLocator"),
    HAS_EXTENT(Ld4lNamespace.BIBFRAME, "extent"),
    HAS_INSTANCE(Ld4lNamespace.BIBFRAME, "hasInstance"),
    HAS_ITEM(Ld4lNamespace.BIBFRAME, "hasItem"),
    HAS_LANGUAGE(Ld4lNamespace.DCTERMS, "language"),
    HAS_LOCATION(Ld4lNamespace.BIBLIOTEKO, "atLocation"),
    HAS_PART(Ld4lNamespace.DCTERMS, "hasPart"),
    HAS_PREFERRED_TITLE(Ld4lNamespace.BIBLIOTEKO, "hasPreferredTitle"),
    HAS_SOURCE(Ld4lNamespace.BIBLIOTEKO, "hasSource"),
    HAS_STATUS(Ld4lNamespace.BIBFRAME, "status"),
    HAS_SUBJECT(Ld4lNamespace.DCTERMS, "subject"),
    HAS_TARGET(Ld4lNamespace.OA, "hasTarget"),
    HAS_TITLE(Ld4lNamespace.BIBFRAME, "title"),
    IDENTIFIES(Ld4lNamespace.BIBFRAME, "identifies"),
    IS_ACTIVITY_OF(Ld4lNamespace.BIBLIOTEKO, "isActivityOf"),
    IS_AGENT_OF(Ld4lNamespace.BIBLIOTEKO, "isAgentOf"),
    IDENTIFIED_BY(Ld4lNamespace.BIBFRAME, "identifiedBy"),
    IS_INSTANCE_OF(Ld4lNamespace.BIBFRAME, "instanceOf"),
    IS_LOCATION_OF(Ld4lNamespace.BIBLIOTEKO, "isLocationOf"),
    IS_TITLE_OF(Ld4lNamespace.BIBLIOTEKO, "isTitleOf"),
    MOTIVATED_BY(Ld4lNamespace.OA, "motivatedBy"),
    /*
     * rdf:value is an RDF property with range rdfs:Resource. We define it
     * as both an Ld4lDatatypeProp and an Ld4lObjectProp for use with either 
     * a literal or non-literal object. For now it doesn't appear that the  
     * lack of technical accuracy will cause any problems.
     */
    VALUE(Ld4lNamespace.RDF, "value");

   
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

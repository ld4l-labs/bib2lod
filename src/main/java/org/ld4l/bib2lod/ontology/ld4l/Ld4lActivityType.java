package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lActivityType implements Type {
    
    /* List in alpha order */
    ACTIVITY(Ld4lNamespace.BIBLIOTEKO, "Activity", "Activity"),
    AUTHOR_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "AuthorActivity", "Author"),    
    COPYRIGHT_HOLDER_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "CopyrightHolderActivity", "C"),   
    DISTRIBUTOR_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "DistributorActivity", "Distributor"),
    MANUFACTURER_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "ManufacturerActivity", "Manufacturer"),
    ORIGINATOR_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "OriginatorActivity", "Originator"),
    PRODUCER_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "ProducerActivity", "Producer"),
    PROVIDER_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "ProviderActivity", "Producer"),
    PUBLISHER_ACTIVITY(
            Ld4lNamespace.BIBLIOTEKO, "PublisherActivity", "Publishing");

    private static final Type DEFAULT_TYPE = ACTIVITY;
    
    private final String uri;
    private final Resource ontClass;
    private final String label;
    
    Ld4lActivityType(Namespace namespace, String localName, String label) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri); 
        /*
         * TODO Here's a case where reading in the ontology file would be really
         * useful: the rdfs:label can be retrieved from the property definition
         * rather than hard-coded here.
         */
        this.label = label;
    }
    

    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    }
    
    public String label() {
        return label;
    }

    @Override
    public Type superclass() {
        return DEFAULT_TYPE;
    }

    public static Type defaultType() {
        return DEFAULT_TYPE;
    }

}

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lAnnotationType implements Type {
    
    /* List in alpha order */
    /* NB Unlike other Type enums, these don't represent a single class hierarchy.
     * TODO Is there a better way to do this?
     */
    ANNOTATION(Ld4lNamespace.OA, "Annotation"),
    MOTIVATION(Ld4lNamespace.OA, "Motivation"),
    TEXTUAL_BODY(Ld4lNamespace.OA, "TextualBody");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lAnnotationType(Namespace namespace, String localName) {
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
}

package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lTextualBodyType implements Type {

    /* List in alpha order */
    TEXTUAL_BODY(Ld4lNamespace.OA, "TextualBody");
    
    private static final Type DEFAULT_TYPE = TEXTUAL_BODY;
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lTextualBodyType(Namespace namespace, String localName) {
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
        return DEFAULT_TYPE;
    }
}

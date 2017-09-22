package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lExtentType implements Type {
    
    /* List in alpha order */
    EXTENT(Ld4lNamespace.BIBFRAME, "Extent");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lExtentType(Namespace namespace, String localName) {
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
        return defaultType();
    }
    
    public static Type defaultType() {
        return EXTENT;
    }
}

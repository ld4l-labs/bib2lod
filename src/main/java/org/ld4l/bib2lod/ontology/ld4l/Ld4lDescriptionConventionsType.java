package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

public enum Ld4lDescriptionConventionsType implements Type {

    /* List in alpha order */
    DESCRIPTION_CONVENTIONS(
            Ld4lNamespace.BIBFRAME, "DescriptionConventions");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lDescriptionConventionsType(Namespace namespace, String localName) {
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
    
    public static Type superclass() {
        return DESCRIPTION_CONVENTIONS;
    }

}

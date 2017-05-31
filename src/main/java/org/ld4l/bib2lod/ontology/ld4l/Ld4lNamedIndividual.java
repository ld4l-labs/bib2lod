package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates named individuals used in bibliotek-o data model.
 */
public enum Ld4lNamedIndividual implements NamedIndividual {
    
    /* List in alpha order */
    _134059638(Ld4lNamespace.VIAF, "134059638"),
	DESCRIBING(Ld4lNamespace.OA, "describing", Ld4lMotivationType.MOTIVATION),
	PROVIDING_PURPOSE(Ld4lNamespace.BIBLIOTEKO, "providingPurpose", Ld4lMotivationType.MOTIVATION),
	SUMMARIZING(Ld4lNamespace.BIBLIOTEKO, "summarizing", Ld4lMotivationType.MOTIVATION);

    private String uri;
    private Resource resource;
    private Type type;
    
    /**
     * Constructor
     */
    Ld4lNamedIndividual(Namespace namespace, String localName, Type type) {
        this.uri = namespace.uri() + localName;
        this.resource = ResourceFactory.createResource(uri);
        this.type = type;
    }
    
    Ld4lNamedIndividual(Namespace namespace, String localName) {
        this(namespace,  localName, null);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource resource() {
        return resource;
    } 
    
    @Override
    public Type type() {
        return type;
    }

}

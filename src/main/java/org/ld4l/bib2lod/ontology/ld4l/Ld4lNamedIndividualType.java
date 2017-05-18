package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.NamedIndividual;
import org.ld4l.bib2lod.ontology.Namespace;

/**
 * Enumerates named individuals related to Bibliotek-o.
 */
public enum Ld4lNamedIndividualType implements NamedIndividual {
    
	DESCRIBING(Ld4lNamespace.OA, "describing"),
	SUMMARIZING(Ld4lNamespace.BIBLIOTEKO, "summarizing"),
	PROVIDING_PURPOSE(Ld4lNamespace.BIBLIOTEKO, "providingPurpose"),
	_134059638(Ld4lNamespace.VIAF, "134059638");

    private String uri;
    private Resource resource;
    
    /**
     * Constructor
     */
    Ld4lNamedIndividualType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.resource = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource resource() {
        return resource;
    } 

}

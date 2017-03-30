package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Property;

/**
 * Represents the ontology properties defined in the target set of ontologies/
 * application profile.
 */
public interface OntologyProperty {

    public String uri();
    
    public Property property();
}

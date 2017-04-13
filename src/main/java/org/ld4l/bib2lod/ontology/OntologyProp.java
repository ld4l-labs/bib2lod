package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Property;

/**
 * Represents the ontology properties defined in the target set of ontologies/
 * application profile.
 */
/*
 * TODO Consider reading in the ontology files to either replace or 
 * facilitate/enhance this.
 */
public interface OntologyProp {

    public String uri();
    
    public Property property();
}

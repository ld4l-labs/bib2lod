package org.ld4l.bib2lod.ontology;

/**
 * Represents the ontology properties defined in the target set of ontologies/
 * application profile.
 */
/*
 * TODO Consider reading in the ontology files to either replace or 
 * facilitate/enhance this.
 */
public interface Namespace {

    public String uri();
    public String prefix();
}

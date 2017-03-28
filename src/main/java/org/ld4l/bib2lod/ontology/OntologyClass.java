package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;

/**
 * A set of ontology classes used for specific Entity types.
 */
// TODO Read in the ontology files to either replace or facilitate/enhance this.
public interface OntologyClass {
  
    public String uri();   
    public Resource ontClass();   
}

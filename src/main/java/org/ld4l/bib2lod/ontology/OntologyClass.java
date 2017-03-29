package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.entities.Type;

/**
 * The ontology classes used to type Entities. Implementing enums apply to a
 * specific type of Entity.
 */
// TODO Read in the ontology files to either replace or facilitate/enhance this.
public interface OntologyClass {
  
    public String uri();   
    public Resource ontClass();

}

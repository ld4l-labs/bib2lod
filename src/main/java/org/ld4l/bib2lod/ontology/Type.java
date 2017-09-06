package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;

/**
 * The ontology classes used to type Entities. Implementing enums apply to a
 * specific type of Entity.
 */
/*
 * TODO Consider reading in the ontology files to either replace or 
 * facilitate/enhance this.
 */
public interface Type {
  
    public String uri(); 
    
    // Possibly return an OntClass instead, if the functionality is useful 
    // (e.g., in subclass inferencing).
    public Resource ontClass();
    
//    public Type supertype();

}

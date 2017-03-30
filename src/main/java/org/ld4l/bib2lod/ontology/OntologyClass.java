package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;

/**
 * The ontology classes used to type Entities. Implementing enums apply to a
 * specific type of Entity.
 */
// TODO Read in the ontology files to either replace or facilitate/enhance this.
// Also consider an extending interface LD4LClass, which the current
// enums would implement. These enums would include not just classes in the 
// ld4l namespace, but the entire set of target terms from the LD4L application
// profile. 
public interface OntologyClass {
  
    public String uri(); 
    
    // Possibly return an OntClass instead, if the functionality is useful 
    // (e.g., in subclass inferencing).
    public Resource ontClassResource();

}

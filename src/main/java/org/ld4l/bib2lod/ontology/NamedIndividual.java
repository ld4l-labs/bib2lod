package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;

public interface NamedIndividual {
    
    public String uri(); 
    public Resource resource();
    public Type type();
}

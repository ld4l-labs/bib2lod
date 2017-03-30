package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.OntologyClass;

/**
 * Represents an Entity type.
 */
public interface Type {

    /**
     * Factory methods
     */
   
    public static Type instance(OntologyClass ontClass) {
        return Bib2LodObjectFactory.getFactory().createType(ontClass);
    }

    public static Type instance(Resource ontClass) {
        return Bib2LodObjectFactory.getFactory().createType(ontClass);
    }
    
    public String getUri();

    /**
     * Returns the ontology class represented by this Type
     */
    // TODO Consider changing Resource to OntClass
    public Resource getOntClass();





}

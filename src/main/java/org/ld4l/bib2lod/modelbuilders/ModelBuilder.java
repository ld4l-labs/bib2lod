/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.modelbuilders;

import org.apache.jena.rdf.model.Model;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Instance;

/**
 * Builds a Model from an Entity.
 */
public interface ModelBuilder {
    
    /**
     * Signals an error during instantiation of a ModelBuilder.
     */
    public static class ModelBuilderInstantiationException extends Exception {
        private static final long serialVersionUID = 1L;

        public ModelBuilderInstantiationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ModelBuilderInstantiationException(String message) {
            super(message);
        }

        public ModelBuilderInstantiationException(Throwable cause) {
            super(cause);
        }
    }    
    

    /**
     * Factory method
     * @param type - the Entity for which to instantiate a ModelBuilder
     * @throws ModelBuilderInstantiationException 
     */
    static ModelBuilder instance(Entity resource) 
            throws ModelBuilderInstantiationException {
        
        ModelBuilder builder = null;
        if (resource instanceof Instance) {
            return new InstanceModelBuilder(resource);
        }
        throw new ModelBuilderInstantiationException(
                "Model builders other than InstanceModelBuilder not implemented.");
    }
    
    // TODO Better to not set resource in constructor and pass it in here??
    public Model build();
    
}

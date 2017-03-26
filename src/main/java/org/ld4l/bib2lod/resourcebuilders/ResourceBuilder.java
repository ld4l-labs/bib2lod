/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Identifier;
import org.ld4l.bib2lod.entities.Instance;

/**
 * Builds a Model from an Entity.
 */
public interface ResourceBuilder {
    
    /**
     * Signals an error in building a Resource.
     */
    public static class ResourceBuilderException
            extends RecordConversionException {
        private static final long serialVersionUID = 1L;

        public ResourceBuilderException(String message, Throwable cause) {
            super(message, cause);
        }

        public ResourceBuilderException(String message) {
            super(message);
        }

        public ResourceBuilderException(Throwable cause) {
            super(cause);
        }
    }    
    

    /**
     * Factory method
     * @param type - the Entity for which to instantiate a ResourceBuilder
     * @param model - the Model to build the Resource in
     * @throws ResourceBuilderException 
     */
    static ResourceBuilder instance(Entity entity, Model model) 
            throws ResourceBuilderException {

        if (entity instanceof Instance) {
            return new InstanceResourceBuilder((Instance) entity, model);
        }
        if (entity instanceof Identifier) {
            return new IdentifierResourceBuilder((Identifier) entity, model);
        }
        // etc
        throw new ResourceBuilderException(
                Entity.class.getSimpleName() + " ResourceBuilder not implemented.");
    }
    
    /**
     * Returns a Resource.
     */
    public Resource build();
    
}

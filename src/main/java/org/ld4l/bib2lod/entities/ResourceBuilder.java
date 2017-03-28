/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;

/**
 * Builds a Resource from an Entity.
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
     */
    public static ResourceBuilder instance(Entity entity) {
        return Bib2LodObjectFactory.instance().createResourceBuilder(entity);
    }
    
    public Resource build();

}

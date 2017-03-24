/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entities.Entity;

/**
 * Builds an Entity from a Record
 */
public interface EntityBuilder {
    
    public static class EntityBuilderException extends RecordConversionException {
        private static final long serialVersionUID = 1L;

        public EntityBuilderException(String message, Throwable cause) {
            super(message, cause);
        }

        public EntityBuilderException(String message) {
            super(message);
        }

        public EntityBuilderException(Throwable cause) {
            super(cause);
        }
    }  
    
    /**
     * Builds an Entity.
     * @throws EntityBuilderException 
     */
    public Entity build() throws EntityBuilderException;

}

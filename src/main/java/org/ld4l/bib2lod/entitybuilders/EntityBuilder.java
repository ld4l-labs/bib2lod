/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.record.Record;

/**
 * Builds an Entity from a Record
 */
public interface EntityBuilder {
    
    public static class EntityBuilderException extends Exception {
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
     * Factory method
     * @param type - the type of EntityBuilder to instantiate (InstanceBuilder, 
     * WorkBuilder, etc.)
     * @throws EntityBuilderException 
     */
    static EntityBuilder instance(Class<?> builderClass) 
            throws EntityBuilderException {
        try {
            return (EntityBuilder) builderClass                            
                    .newInstance();
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | SecurityException e) {
            throw new EntityBuilderException(e);
        }                
    }
    
    /**
     * Builds an entity from a Record.
     * @throws EntityBuilderException 
     */
    public Entity build(Record record) throws EntityBuilderException;

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.Type;


/**
 * Builds an Entity from a Record
 */
public interface EntityBuilder {
    
    /**
     * Signals a problem when building an Entity
     */
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
     * @throws EntityBuilderException 
     */ 
    public static EntityBuilder instance(Class<? extends EntityBuilder> builderClass) 
            throws EntityBuilderException {
        try {
            return builderClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new EntityBuilderException(e);
        }
    }

    
    /**
     * Builds an Entity, including its dependent Entities (e.g., Identifiers
     * and Titles of Works and Instances).
     * @throws EntityBuilderException 
     */
    //public Entity build(Map<String, Object> params) throws EntityBuilderException;
    public Entity build(BuildParams params) throws EntityBuilderException;

    /**
     * Retrieves the EntityBuilder for the specified type from the 
     * EntityBuilderFactory instance.
     * @param type
     * @return
     */
    public EntityBuilder getBuilder(Class<? extends Type> type);

}

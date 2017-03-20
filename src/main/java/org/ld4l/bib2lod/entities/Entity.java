/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.List;

/**
 * Stores the data from a record that will be converted to a single 
 * org.ld4l.bib2lod.entities.Entity.
 */
public interface Entity {
    
    /**
     * Signals an exception during creation of an Entity. 
     */
    public static class EntityInstantiationException extends 
            RuntimeException {         
        private static final long serialVersionUID = 1L;

        public EntityInstantiationException(String msg, Throwable cause) {
            super(msg, cause);                 
        }
        
        public EntityInstantiationException(String msg) {
            super(msg);                 
        }   
        
        public EntityInstantiationException(Throwable cause) {
            super(cause);                 
        }    
    }

    /**
     * Factory method
     * @param type - the type of Entity to instantiate
     */
    static Entity instance(Class<? extends Entity> type) {
        try {
            return (Entity) type
                    .newInstance();
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | SecurityException e) {
            throw new EntityInstantiationException(e);
        }     
    }

    /**
     * Returns a list of the types of this Entity.
     */
    List<String> getTypes();

}

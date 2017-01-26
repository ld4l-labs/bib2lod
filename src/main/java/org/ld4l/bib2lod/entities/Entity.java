/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Stores the data from a record that will be converted to a single 
 * org.ld4l.bib2lod.entities.Entity.
 */
public interface Entity {
    
    /**
     * Signals an exception during creation of a Entity. 
     */
    public static class EntityInstantiationException extends 
            RuntimeException {         
        private static final long serialVersionUID = 1L;
        
        public EntityInstantiationException(String msg, Throwable cause) {
            super(msg, cause);                 
        }        
    }

    /**
     * Factory method
     * @param type - the type of Entity to instantiate
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    static Entity instance(Class<?> type) throws InstantiationException, 
            IllegalAccessException, ClassNotFoundException {
        return Bib2LodObjectFactory.instance().createEntity(type);
    }

    /**
     * @return
     */
    List<String> getTypes();

}

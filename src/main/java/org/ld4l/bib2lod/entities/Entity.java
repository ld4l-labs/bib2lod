/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.lang.reflect.InvocationTargetException;
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
     * Factory method
     * @param type - the type of Entity to instantiate
     * @param relatedEntity - the Entity to which this Entity is related when it
     * is built
     */
    static Entity instance(Class<? extends Entity> type, Entity relatedEntity) {
        try {
            return (Entity) type
                    .getConstructor(Entity.class)
                    .newInstance(relatedEntity);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | SecurityException | InvocationTargetException | 
                NoSuchMethodException e) {
            throw new EntityInstantiationException(e);
        }     
    }
    
    
    public void addType(String type);

    /**
     * Returns a list of the Entity's types.
     */
    public List<String> getTypes();
    
    /**
     * Return the super type - the type that all entities of this type belong 
     * to.
     */
    public String getSuperType();


}

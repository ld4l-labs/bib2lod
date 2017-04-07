/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.RecordField;

/**
 * This base class holds the factory instance which others will use.
 */
public abstract class Bib2LodObjectFactory {
    private static Bib2LodObjectFactory instance;

    /**
     * Set the factory instance. May be called only once.
     */
    public static void setFactoryInstance(Bib2LodObjectFactory factory) {
        if (instance == null) {
            instance = factory;
        } else {
            throw new IllegalStateException(
                    "Bib2LodObjectFactory instance has already been set");
        }
    }

    /**
     * Get the factory instance. Never returns null.
     */
    public static Bib2LodObjectFactory getFactory() {
        if (instance == null) {
            throw new IllegalStateException(
                    "Bib2LodObjectFactory instance has not been set");
        } else {
            return instance;
        }
    }

    /**
     * Return the first instance that was created for this interface. Never
     * returns null.
     * 
     * @throws ConfigurationException
     *             if no instances are found.
     */
    public abstract <T> T instanceForInterface(Class<T> interfaze);

    /**
     * Return the instances that were created for this interface. Never returns
     * empty or null.
     * 
     * @throws ConfigurationException
     *             if no instances are found.
     */
    public abstract <T> List<T> instancesForInterface(Class<T> interfaze);

    
    // ----------------------------------------------------------------------
    // Vestigial methods
    // ----------------------------------------------------------------------

  /**
  * A problem occurred when trying to create an Object in the Factory.
  */
 public static class Bib2LodObjectFactoryException extends RuntimeException {
     private static final long serialVersionUID = 1L;

     public Bib2LodObjectFactoryException(String message, Throwable cause) {
         super(message, cause);
     }

     public Bib2LodObjectFactoryException(String message) {
         super(message);
     }

     public Bib2LodObjectFactoryException(Exception cause) {
         super(cause);
     }
 }
 

    public EntityBuilder createEntityBuilder(
            Class<?> builderClass, Record record) {         
        try {
            return (EntityBuilder) builderClass
                    .getConstructor(Record.class)                     
                    .newInstance(record);
        } catch (IllegalAccessException | IllegalArgumentException
                | SecurityException | InvocationTargetException 
                | NoSuchMethodException | InstantiationException e) {
            throw new Bib2LodObjectFactoryException(e);
        } 
    }

    
    public EntityBuilder createEntityBuilder(Class<?> builderClass,
            RecordField field, Entity relatedEntity) {
        try {
            return (EntityBuilder) builderClass
                    .getConstructor(RecordField.class, Entity.class)                           
                    .newInstance(field, relatedEntity);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | SecurityException | InvocationTargetException 
                | NoSuchMethodException e) {
            throw new Bib2LodObjectFactoryException(e);
        } 
    }

    public EntityBuilder createEntityBuilder(Class<?> builderClass,
            Record record, Entity relatedEntity) {
        try {
            return (EntityBuilder) builderClass
                    .getConstructor(Record.class, Entity.class)                           
                    .newInstance(record, relatedEntity);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | SecurityException | InvocationTargetException 
                | NoSuchMethodException e) {
            throw new Bib2LodObjectFactoryException(e);
        }         
    }

    public abstract Entity createEntity(Type type);
    
//    public abstract Entity createEntity(Resource ontClass);

    public abstract Entity createEntity(Entity entity);
    
//    public abstract Type createType(OntologyClass ontClass);
//    
//    public abstract Type createType(Resource ontClass);
//    
//    public abstract Link createLink(Property property);
//
//    public abstract Link createLink(OntologyProperty ontProperty);

}

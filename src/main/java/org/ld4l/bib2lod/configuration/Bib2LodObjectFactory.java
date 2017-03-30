/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Link;
import org.ld4l.bib2lod.entities.Type;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
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

    public EntityBuilder createEntityBuilder(
            Class<?> builderClass, Record record) {         
        try {
            return (EntityBuilder) builderClass
                    .getConstructor(Record.class)                     
                    .newInstance(record);
        } catch (IllegalAccessException | IllegalArgumentException
                | SecurityException | InvocationTargetException 
                | NoSuchMethodException | InstantiationException e) {
            throw new ConfigurationException(e);
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
            throw new ConfigurationException(e);
        } 
    }
    

    public abstract Entity createEntity(Type type);
    
    public abstract Entity createEntity(Resource ontClass);
    
    public abstract Type createType(OntologyClass ontClass);
    
    public abstract Type createType(Resource ontClass);
    
    public abstract Link createLink(Property property);

    public abstract Link createLink(OntologyProperty ontProperty);


}

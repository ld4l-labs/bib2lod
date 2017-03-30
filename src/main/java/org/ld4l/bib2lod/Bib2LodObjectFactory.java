/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.lang.reflect.InvocationTargetException;

import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Type;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.RecordField;
import org.ld4l.bib2lod.uris.UriService;

/**
 * Factory class to instantiate Bib2Lod objects.
 * 
 * Use these methods instead of constructors. The best way to call them is from
 * static factory methods on the result classes.
 * 
 * The singleton instance may be replaced for unit tests.
 */
public abstract class Bib2LodObjectFactory {
    
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
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // NOTE: private but not final, so it can be changed during unit tests.
    private static Bib2LodObjectFactory instance = 
            new DefaultBib2LodObjectFactory();

    public static Bib2LodObjectFactory instance() {
        return instance;
    }  
    
    /**
     * @param args - the program arguments
     */
    public abstract OptionsReader createOptionsReader(String[] args);

    /**
     * @param args - the program arguments
     */
    public abstract Configuration createConfiguration(String[] args);

    public abstract Converter createConverter();
    
    public abstract Cleaner createCleaner();

    /**
     * @param className - the class name of the UriService to instantiate 
     * @param configuration - the program Configuration
     */
    // TODO - since we have the class name - move this to the interface instance() method
    public UriService createUriService(String className, 
            Configuration configuration) {
        
        try {
            Class<?> uriServiceClass = Class.forName(className);
            return (UriService) uriServiceClass                            
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new Bib2LodObjectFactoryException(e);
        }   
    }

    /**
     * @param configuration - the program Configuration
     */
    public InputService createInputService(Configuration configuration) {
        try {
            return (InputService) Class
                    .forName(configuration.getInputServiceClass())
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /**
     * @param configuration - the program Configuration
     * @throws Bib2LodObjectFactoryException
     */
    public OutputService createOutputService(Configuration configuration) {
        try {
            return (OutputService) Class
                    .forName(configuration.getOutputServiceClass())
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new Bib2LodObjectFactoryException(e);
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
    
    public abstract Entity createEntity(Resource ontClass);
    
    public abstract Entity createEntity(OntologyClass ontClass);
    
    public abstract Entity createEntity(Entity entity);
    
    public abstract Type createType(OntologyClass ontClass);
    
    public abstract Type createType(Resource ontClass);

}



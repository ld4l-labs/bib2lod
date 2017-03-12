/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.modelbuilders.InstanceModelBuilder;
import org.ld4l.bib2lod.modelbuilders.ModelBuilder;
import org.ld4l.bib2lod.uris.UriGetter;

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

    /**
     * Return a Bib2LodObjectFactory instance.
     * @return instance - the Bib2LodObjectFactory instance
     */
    public static Bib2LodObjectFactory instance() {
        return instance;
    }  
    
    /**
     * Returns an OptionsReader instance.
     * @param args - the program arguments
     * @return the OptionsReader instance
     */
    public abstract OptionsReader createOptionsReader(String[] args);

    /**
     * Returns a Configuration instance.
     * @param args - the program arguments
     * @return the Configuration instance
     */
    public abstract Configuration createConfiguration(String[] args);
 
    /**
     * Returns a Converter instance.
     * @param configuration - the Configuration instance 
     * @return the Converter instance
     */
    public abstract Converter createConverter(Configuration configuration);
    
    /**
     * Returns a Cleaner instance.
     * @param configuration - the Configuration instance 
     * @return the Cleaner instance
     */
    public abstract Cleaner createCleaner(Configuration configuration);

    /**
     * Returns a UriGetter instance.
     * @param className - the class name of the minter to instantiate 
     * @param configuration - the Configuration instance 
     * @return the UriGetter instance
     */
    public UriGetter createUriMinter(String className, 
            Configuration configuration) {
        
        try {
            Class<?> minterClass = Class.forName(className);
            return (UriGetter) minterClass                            
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
     * Returns a EntityBuilder of the specified type
     * @param type - the class to instantiate
     * @param configuration - the program Configuration
     * @return
     */
    public EntityBuilder createEntityBuilder(
            Class<?> minterClass, Configuration configuration) {
        
        try {
            return (EntityBuilder) minterClass                            
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new Bib2LodObjectFactoryException(e);
        }   
    }
    
    /**
     * Returns a ModelBuilder for the Entity
     * @param resource - the resource for which to create the ModelBuilder
     * @param configuration 
     * @return
     */
    public ModelBuilder createModelBuilder(
            Entity resource, Configuration configuration)  {
        
        ModelBuilder builder = null;
        if (resource instanceof Instance) {
            return new InstanceModelBuilder(resource, configuration);
        }
        return builder;
    }
    
    /**
     * Returns an Entity of the specified type
     * @param type - the class to instantiate
     * @return
     */
    public Entity createEntity(Class<? extends Entity> type) {
        try {
            return type.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /**
     * @param configuration
     * @return
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
     * @param configuration
     * @return
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

// Removing this because the converter knows what type of parser it needs and
// must ask for it specifically.
//    /**
//     * Returns an input parser.
//     * @param configuration - the Configuration instance
//     * @return the Parser instance
//     */
//    public abstract Parser createParser(Configuration configuration);
//
//    /**
//     * Returns a Marcxml input parser
//     * @param configuration - the Configuration instance
//     * @return the MarcxmlParser instance
//     */
//    public abstract MarcxmlParser 
//            createMarcxmlParser(Configuration configuration);
    

}



/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;
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
     * @param className - the class name of the UriGetter to instantiate 
     * @param configuration - the program Configuration
     */
    // TODO - since we have the class name - move this to the interface instance() method
    public UriGetter createUriGetter(String className, 
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
            String className =  configuration.getOutputServiceClass();
            Class<?> cls = Class.forName(className);
            Constructor<?> con = cls.getConstructor(Configuration.class);
            OutputService os = (OutputService) con.newInstance(configuration);
            return os;
//            return (OutputService) Class
//                    .forName(configuration.getOutputServiceClass())
//                    .getConstructor(Configuration.class)
//                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

}



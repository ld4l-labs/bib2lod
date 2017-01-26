/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.io.InputBuilder;
import org.ld4l.bib2lod.io.OutputWriter;
import org.ld4l.bib2lod.modelbuilders.InstanceModelBuilder;
import org.ld4l.bib2lod.modelbuilders.ModelBuilder;
import org.ld4l.bib2lod.uri.UriMinter;

/**
 * Factory class to instantiate Bib2Lod objects.
 * 
 * Use these methods instead of constructors. The best way to call them is from
 * static factory methods on the result classes.
 * 
 * The singleton instance may be replaced for unit tests.
 */
public abstract class Bib2LodObjectFactory {
    
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
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    public abstract Configuration createConfiguration(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
                ParseException, InstantiationException, IllegalAccessException, 
                    IllegalArgumentException, InvocationTargetException, 
                        NoSuchMethodException, SecurityException;
 
    /**
     * Returns a Converter instance.
     * @param configuration - the Configuration instance 
     * @return the Converter instance
     */
    public abstract Converter createConverter(Configuration configuration);
    
    public OutputWriter createOutputWriter(Configuration configuration) throws 
            ClassNotFoundException, NoSuchMethodException, 
            SecurityException, InstantiationException, 
            IllegalAccessException, IllegalArgumentException, 
            InvocationTargetException {
        
        Class<?> writerClass = Class.forName(configuration.getOutputWriter());
        return (OutputWriter) writerClass
                .getConstructor(Configuration.class)
                .newInstance(configuration);
    }
    
    /**
     * Returns an InputBuilder instance.
     * @return the InputBuilder instance.
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    // This is probably how it should be done, but for now just pass in the
    // class name.
     //    public abstract InputBuilder createInputBuilder(Configuration configuration);
    public InputBuilder createInputBuilder(String className) throws 
            InstantiationException, IllegalAccessException, 
            ClassNotFoundException {
        return (InputBuilder) Class.forName(className).newInstance();
    }
    
    /**
     * Returns a Cleaner instance.
     * @param configuration - the Configuration instance 
     * @return the Cleaner instance
     */
    public abstract Cleaner createCleaner(Configuration configuration);

    /**
     * Returns a UriMinter instance.
     * @param className - the class name of the minter to instantiate 
     * @param configuration - the Configuration instance 
     * @return the UriMinter instance
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public UriMinter createUriMinter(String className, 
            Configuration configuration) throws InstantiationException, 
                IllegalAccessException, IllegalArgumentException, 
                    InvocationTargetException, NoSuchMethodException, 
                        SecurityException, ClassNotFoundException {
        
        Class<?> minterClass = Class.forName(className);
        return (UriMinter) minterClass                            
                .getConstructor(Configuration.class)
                .newInstance(configuration);   
    }
    
    
    /**
     * Returns a EntityBuilder of the specified type
     * @param type - the class to instantiate
     * @param configuration - the program Configuration
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    public EntityBuilder createEntityBuilder(
            Class<?> minterClass, Configuration configuration) throws 
                InstantiationException, IllegalAccessException, 
                    ClassNotFoundException, IllegalArgumentException, 
                        InvocationTargetException, 
                            NoSuchMethodException, SecurityException {
        
        return (EntityBuilder) minterClass                            
                .getConstructor(Configuration.class)
                .newInstance(configuration);   
    }
    
    /**
     * Returns a ModelBuilder for the Entity
     * @param resource - the resource for which to create the ModelBuilder
     * @param configuration 
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public ModelBuilder createModelBuilder(
            Entity resource, Configuration configuration) throws 
                InstantiationException, IllegalAccessException, 
                ClassNotFoundException {
        
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
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public Entity createEntity(Class<?> type) throws 
            InstantiationException, IllegalAccessException, 
            ClassNotFoundException {
        return (Entity) type.newInstance();
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



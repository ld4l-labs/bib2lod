/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputBuilder;
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
     */
    public abstract Configuration createConfiguration(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
            ParseException, InstantiationException, IllegalAccessException;
 
    /**
     * Returns a Converter instance.
     * @param configuration - the Configuration instance 
     * @return the Converter instance
     */
    public abstract Converter createConverter(Configuration configuration);
    
    /**
     * Returns an InputBuilder instance.
     * @return the InputBuilder instance.
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
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
     * @param configuration - the Configuration instance 
     * @return the UriMinter instance
     */
    public abstract UriMinter createUriMinter(Configuration configuration);

}



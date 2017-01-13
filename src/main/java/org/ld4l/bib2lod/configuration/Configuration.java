/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Provides program configuration values.
 */
public interface Configuration {
    
    /**
     * Factory method
     * @param args
     * @return
     * @throws ClassNotFoundException
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ParseException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    static Configuration instance(String[] args) throws ClassNotFoundException,
            FileNotFoundException, IOException, ParseException, 
            InstantiationException, IllegalAccessException {
        return Bib2LodObjectFactory.instance().createConfiguration(args);
    }

    /**
     * Gets the configured local namespace.
     * @return localNamespace - the local namespace
     */
    String getLocalNamespace();

    /**
     * Gets the configured list of input readers.
     * @return input - the list of input readers
     */
    // TODO Generalize to List<Reader>?
    List<BufferedReader> getInput();
    
    /**
     * Gets the configured output destination.
     * @return the output destination
     */
    File getOutputDestination();
      
    /**
     * Gets the configured output format.
     * @return the output format
     */
    String getOutputFormat();

    /**
     * Gets the class name of the UriMinter specified in the configuration.
     * @return uriMinter - the class name of the UriMinter
     */
    String getUriMinter();
    
    /**
     * Gets the class name of the Writer specified in the configuration.
     * @return writer - the class name of the Writer
     */
    String getWriter();   
    
    /**
     * Gets the class name of the Cleaner specified in the configuration.
     * @return
     */
    String getCleaner();
    
    /**
     * Gets the class name of the Converter specified in the configuration.
     * @return converter - the class name of the converter
     */
    String getConverter();
    
    /**
     * Gets the list of class names of the reconcilers specified in the 
     * configuration.
     * @return reconcilers - the ordered class names of the reconcilers
     */
    List<String> getReconcilers();
    
    /**
     * Override toString() for debugging and logging.
     * @return string representation of the Configuration object
     */
    public String toString();
}

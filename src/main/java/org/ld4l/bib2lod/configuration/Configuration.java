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
import org.ld4l.bib2lod.uri.UriMinter;

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
     * @return the local namespace
     */
    String getLocalNamespace();

    /**
     * Gets the configured list of input readers.
     * @return the list of input readers
     */
    // TODO Try to generalize to List<Reader>? There were problems doing so.
    List<BufferedReader> getInput();
    
    /**
     * Gets the configured output destination.
     * @return the output destination
     */
    String getOutputDestination();
      
    /**
     * Gets the configured output format.
     * @return the output format
     */
    String getOutputFormat();

    /**
     * Gets a list of class names of UriMinter specified in the configuration.
     * @return the list of UriMinter class names
     */
    List<String> getUriMinters();
    
    /**
     * Returns the class name of the OutputWriter specified in the 
     * configuration.
     * @return the class name of the OutputWriter
     */
    String getOutputWriter();
    
    /**
     * Gets the class name of the Cleaner specified in the configuration.
     * @return the class name of the cleaner
     */
    String getCleaner();
    
    /**
     * Gets the class name of the Converter specified in the configuration.
     * @return the class name of the converter
     */
    String getConverter();
    
    /**
     * Gets the list of class names of the reconcilers specified in the 
     * configuration.
     * @return the ordered class names of the reconcilers
     */
    List<String> getReconcilers();
    
    /**
     * Override toString() for debugging and logging.
     * @return string representation of the Configuration object
     */
    public String toString();
}

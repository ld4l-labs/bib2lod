/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * An object providing program configuration values
 */
public interface Configuration {
    
    /**
     * Factory method
     */
    static Configuration instance(String[] args) throws ClassNotFoundException,
            FileNotFoundException, IOException, ParseException {
        return Bib2LodObjectFactory.instance().createConfiguration(args);
    }

    /**
     * Gets the configured local namespace.
     * @return localNamespace - the local namespace
     */
    String getLocalNamespace();
    
    /**
     * Gets the configured input source (file or directory).
     * @return
     */
    // TODO Perhaps shouldn't be part of the interface. It's only used for
    // the toString() method for logging.
    File getInputSource();

    /**
     * Gets the configured list of input files.
     * @return input - the list of input files
     */
    List<File> getInputFiles();

    /**
     * Gets the configured input format.
     * @return format - the input format
     */
    String getInputFormat();
    
    /**
     * Gets the configured output directory.
     * @return the output directory
     */
    File getOutputDirectory();
      
    /**
     * Gets the configured output format.
     * @return the output format
     */
    String getOutputFormat();

    /**
     * Gets the class name of the UriMinter specified in the configuration
     * @return uriMinter - the class name of the UriMinter
     */
    String getUriMinter();
    
    /**
     * Gets the class name of the Writer specified in the configuration
     * @return writer - the class name of the Writer
     */
    String getWriter();   
    
    /**
     * Gets the list of class names of the converters specified in the 
     * configuration.
     * @return converters - the ordered class names of the converters
     */
    // TODO Change to List (ordered) of converter classnames, getConverters()
    List<String> getConverters();
    
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

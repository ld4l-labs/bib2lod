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
     * Gets the class name of the UriMinter specified in the configuration
     * @return uriMinter - the class name of the UriMinter
     */
    String getUriMinter();

    /**
     * Gets the configured list of input files.
     * @return input - the list of input files
     */
    // TODO Or just return the input string from config file? Still want to 
    // check the validity of the path here.
    List<File> getInput();

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

}

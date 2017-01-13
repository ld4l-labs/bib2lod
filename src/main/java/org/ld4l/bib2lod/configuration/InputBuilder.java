/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Builds the list of input readers based on the input source specified in the
 * configuration.
 */
public interface InputBuilder {
    
    /**
     * Factory method
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    static InputBuilder instance(String className) throws ClassNotFoundException,
            FileNotFoundException, IOException, ParseException, 
            InstantiationException, IllegalAccessException {
        return Bib2LodObjectFactory.instance().createInputBuilder(className);
    }
    
    /**
     * Build list of input readers from specified source.
     * @param source - input source
     * @return a list of readers
     */
    public List<BufferedReader> buildInputList(String source);
    
    /**
     * Build list of input readers from specified source. Used when source is 
     * file-based and a file extension is specified.
     * @param source
     * @param extension
     * @return a list of readers
     */
    // TODO Wrong to have this in the interface, since a file extension is not
    // relevant to non-file-based input. How to handle this?
    public List<BufferedReader> buildInputList(String source, String extension);

}

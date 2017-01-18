/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

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
    static InputBuilder instance(Configuration configuration) throws 
            ClassNotFoundException, FileNotFoundException, IOException,  
            ParseException, InstantiationException, IllegalAccessException {
        return Bib2LodObjectFactory.instance().createInputBuilder(configuration);
    }

    /**
     * Build list of input readers from specified source.
     * @return a list of readers
     * @throws IOException 
     */
    public List<BufferedReader> buildInputList() throws IOException;


}

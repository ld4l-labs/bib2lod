/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.manager;

import java.io.Reader;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;


/** 
 * Orchestrates conversion of a directory of files or a single file.
 */
public final class SimpleManager {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /** 
     * Main method: gets a Configuration object and calls conversion method.
     * @param args - commandline arguments
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            Configuration configuration = Configuration.instance(args);
            convertFiles(configuration);
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            LOGGER.error("CONVERSION FAILED TO COMPLETE");
        } 
    }
    
    
    /**
     * Converts a list of input readers.
     * @param configuration - the Configuration object
     */
    private static void convertFiles(Configuration configuration) {
        
        // Converter converter = configuration.getConverter();

        List<Reader> input = configuration.getInput();

        for (Reader reader : input) {
            
            // create Reader and send to converter
            // Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            // NO - reader must be created in Configuration, so its getInput method 
            // (change from getInputFiles) is neutral as to input type (file, stream, etc.)
            
            // Converter returns a Writer
            // Manager writes to output file (other managers would output in
            // other ways).

        }       
    }
      
}

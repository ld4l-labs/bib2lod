/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.Converter;


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
     * @throws IOException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ParseException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    private static void convertFiles(Configuration configuration) throws 
            IOException, InstantiationException, IllegalAccessException, 
            ClassNotFoundException, NoSuchMethodException, SecurityException, 
            IllegalArgumentException, InvocationTargetException, 
            ParseException {
        
        Converter converter = Converter.instance(configuration);

        List<BufferedReader> input = configuration.getInput();

        for (BufferedReader reader : input) {
            // Converter writes its own output
            converter.convert(reader);            
            reader.close();
        }       
    }
      
}

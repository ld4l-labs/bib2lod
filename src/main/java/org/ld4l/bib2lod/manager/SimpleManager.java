/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

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
     */
    private static void convertFiles(Configuration configuration) throws 
            IOException, InstantiationException, IllegalAccessException, 
            ClassNotFoundException {
        
        Converter converter = Converter.instance(configuration);

        List<BufferedReader> input = configuration.getInput();
         
        File destination = configuration.getOutputDestination();

        int count = 0;
        for (BufferedReader reader : input) {
            count++;
            StringBuffer buffer = converter.convert(reader); 
            File file = new File(destination, "output-" + count);
            
            // Should get this from configuration? Or can each manager just
            // hard-code the writer? In that case, probably remove writer 
            // specification from configuration. Maybe also rename this class 
            // something like FileOutputManager. Or do the same for the
            // InputBuilder type, and call this FileIOManager?
            Writer writer = new PrintWriter(file);
            writer.write(buffer.toString());
            
            reader.close();
            writer.close();
        }       
    }
      
}

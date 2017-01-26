/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.Converter;
import org.omg.CORBA.portable.OutputStream;
import org.xml.sax.SAXException;


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
     * @throws SAXException 
     * @throws ParserConfigurationException 
     */
    private static void convertFiles(Configuration configuration) throws 
            IOException, InstantiationException, IllegalAccessException, 
            ClassNotFoundException, NoSuchMethodException, SecurityException, 
            IllegalArgumentException, InvocationTargetException, 
            ParseException, ParserConfigurationException, SAXException {
        
        Converter converter = Converter.instance(configuration);

        List<BufferedReader> input = configuration.getInput();

        int count = 0;
        for (BufferedReader reader : input) {
            
            // TEMPORARY till we generalize to different output streams.
            // and implement NamedReader (see Jim's code)
            File file = new File(
                    configuration.getOutputDestination(), "output-" + ++count);
            FileOutputStream outputStream = new FileOutputStream(file);
            
            converter.convert(reader, outputStream);            
            reader.close();
            outputStream.close();
        }       
    }
    
      
}

package org.ld4l.bib2lod.manager;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.context.Configuration;
import org.ld4l.bib2lod.context.JsonConfigConfiguration;

public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);
    
    private static Configuration configuration;

    
    /** 
     * Read in program options and call appropriate conversion functionality.
     * @param args
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            configuration = buildConfiguration(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return;
        } 
        
        // TODO: Temporarily hard-coding the input file. Get the input file or
        // directory from the configuration.
        // TODO: can be either a file or a directory
        String input = "src/test/resources/input/102063.xml";
       
        convertFiles(input);
        
        // parse the xml into records

        LOGGER.info("END CONVERSION.");
    }
    
    private static void convertFiles(String input) {
        
        // TODO: this will be a loop on files, then a loop on directories
        // For now: assume a single file
        
        // TODO this will be a loop through records.
        // For now: a single record
        
        
        
    }
    
    /**
     * 
     */
    private static Configuration buildConfiguration(String[] args) throws IOException, ParseException, ReflectiveOperationException {

        Configuration configuration = new JsonConfigConfiguration(args);
        return configuration;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
        
}

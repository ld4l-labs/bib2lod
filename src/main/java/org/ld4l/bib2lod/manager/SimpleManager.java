package org.ld4l.bib2lod.manager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonConfigConfiguration;

// TODO Put common methods (like getInputFiles() into a base class or a 
// utility class?
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
       
        convertFiles();
        
        // parse the xml into records

        LOGGER.info("END CONVERSION.");
    }
    
    
    /**
     * Convert a list of input files
     */
    private static void convertFiles() {
        
        List<File> inputFiles = configuration.getInput();
        for (File file : inputFiles) {
            convertFile(file);
        }
        
    }
    
    /**
     * Convert a single file
     * @param File file
     */
    private static void convertFile(File file) {
        
    }
    

    /**
     * 
     */
    private static Configuration buildConfiguration(String[] args) 
            throws IOException, ParseException, ReflectiveOperationException {

        Configuration configuration = new JsonConfigConfiguration(args);
        return configuration;
    }
    
    public Configuration getConfiguration() {
        return configuration;
    }
    

        
}

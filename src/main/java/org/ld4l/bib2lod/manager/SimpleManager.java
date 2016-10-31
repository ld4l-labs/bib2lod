package org.ld4l.bib2lod.manager;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.RecordConverter;

// TODO Put common methods (like getInputFiles() into a base class or a 
// utility class?
public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);
    
//    private static Configuration configuration;
    
    /** 
     * Read in program options and call appropriate conversion functionality.
     * @param args
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            Configuration configuration = new Configuration(args);
            convertFiles(configuration);
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } 
    }
    
    
    /**
     * Convert a list of input files
     */
    private static void convertFiles(Configuration configuration) {
          
        //RecordConverter recordConverter = new RecordConverter(configuration);
        List<File> inputFiles = configuration.getInput();
        for (File file : inputFiles) {
            
            //String converted = recordConverter.convertFile(file);
            // TODO get back output and write it (use configuration writer)
        }       
    }
      
}

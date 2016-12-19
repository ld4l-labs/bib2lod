package org.ld4l.bib2lod.manager;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;


/** 
 * Simple manager to orchestrate conversion of a directory of files or a single
 * file.
 *
 */
public final class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);

    
    /** 
     * Main method: gets a Configuration object and calls conversion method.
     * @param args - commandline arguments
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            // TODO Use dependency injection here with factory or factory methods
            Configuration configuration = new Configuration(args);
            // convertFiles(configuration);
            LOGGER.info("END CONVERSION.");
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            LOGGER.error("CONVERSION FAILED TO COMPLETE");
        } 
    }
    
    
    /**
     * Converts a list of input files.
     * @param configuration - the Configuration object
     * @throws IOException 
     */
    private static void convertFiles(Configuration configuration) {

        List<File> inputFiles = configuration.getInput();

        for (File file : inputFiles) {
            
            // TODO Make work as a loop for a chain of converters?
            // Then need to first convert to string, so each converter takes a
            // string as input and returns a string, which is input to next
            // converter.
//            try {
                
                //*** TODO - Need to get constructor and pass in arg
                //LOGGER.debug(configuration.getConverter().getClass().getName());
                // configuration.getConverter().convertFile(file); 

                
            // TODO Log record/id to error file to pass to LTS. Ideally log
            // bib id and the line or field where error occurred.
//            } catch (ParserConfigurationException e) {
//                LOGGER.error(e.getMessage());
//                e.printStackTrace();       
//                
//            // TODO Log record/id to error file to pass to LTS. Ideally log
//            // bib id and the line or field where error occurred.
//            } catch (SAXException e) {
//                LOGGER.error(e.getMessage());
//                e.printStackTrace();
//                
//            } catch (IOException e) {
//                LOGGER.error(e.getMessage());
//                e.printStackTrace();
                
 //           } // continue to next record

        }       
    }
      
}

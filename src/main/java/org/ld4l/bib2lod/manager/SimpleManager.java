package org.ld4l.bib2lod.manager;

import java.io.IOException;
import java.util.UUID;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.context.Context;
import org.ld4l.bib2lod.context.JsonConfigContext;
import org.ld4l.bib2lod.util.MurmurHash;

public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);
    
    private static Context context;

    
    /** 
     * Read in program options and call appropriate conversion functionality.
     * @param args
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");

        try {
            context = setContext(args);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return;
        } 
        
        // TODO: Temporarily hard-coding the input file. Get the input file or
        // directory from the context.
        // TODO: can be either a file or a directory
        String input = "src/test/resources/input/102063.xml";
       
        // TODO This will need to get passed the Context object
        convertFiles(input);
        
        // parse the xml into records

        LOGGER.info("END CONVERSION.");
    }
    
    private static void convertFiles(String input) {
        
        // TODO: this will be a loop on files, then a loop on directories
        // For now: assume a single file
        
        
    }
    
    private static Context setContext(String[] args) throws IOException, 
            ParseException {

        Context context = new JsonConfigContext(args);
        return context;
    }
    
    public Context getContext() {
        return context;
    }
        
}

package org.ld4l.bib2lod.manager;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.context.Bib2LodContext;

public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);
    
    private static Bib2LodContext context;

    
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
    
    private static Bib2LodContext setContext(String[] args) throws IOException, 
            ParseException {

        Bib2LodContext context = new Bib2LodContext(args);
        return context;
    }
    
    public Bib2LodContext getContext() {
        return context;
    }
        
}

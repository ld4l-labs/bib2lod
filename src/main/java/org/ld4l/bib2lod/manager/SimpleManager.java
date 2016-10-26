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

import com.google.gson.JsonObject;

public class SimpleManager {

    private static final Logger LOGGER = 
            LogManager.getLogger(SimpleManager.class);

    
    /** 
     * Read in program options and call appropriate conversion functionality.
     * @param args
     */
    public static void main(String[] args) {

        LOGGER.info("START CONVERSION.");
        
        JsonObject jsonConfig = null;
        
        try {
            jsonConfig = new Configurer(args).getConfig();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        LOGGER.debug(jsonConfig.toString());   
            
        // TODO Create Context object: configuration plus services (reader, 
        // writer, uri minter, logger, error handler)
        
        // TODO: Temporarily hard-coding the input file. Get the input file or
        // directory from the config file.
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
    
    // TODO Move all to a Configurer object
        
    /**
     * Define the commandline options accepted by the program.
     * @return an Options object
     */
    private static Options getOptions() {
        
        Options options = new Options();

        options.addOption(Option.builder("c")
                .longOpt("config")
                .required(false)
                .hasArg()
                .argName("config")
                .desc("Config file location. Defaults to /src/main/resources/config.json")
                .build());           

        return options;
    }

    /**
     * Parse commandline options.
     * @param options
     * @param args
     * @return
     */
    private static CommandLine getCommandLine(Options options, String[] args) {
        
        // Parse program arguments
        CommandLineParser parser = new DefaultParser();
        try {
            return parser.parse(options, args);
        } catch (MissingOptionException e) {
            LOGGER.fatal(e.getMessage());
            printHelp(options);
        } catch (UnrecognizedOptionException e) {
            LOGGER.fatal(e.getMessage());
            printHelp(options);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            LOGGER.fatal(e.getStackTrace().toString());
        }
        return null;
    }

    /**
     * Print help text.
     * @param options
     */
    private static void printHelp(Options options) {
        
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(80);
        formatter.printHelp("java -jar Bib2Lod.jar", options, true);
    }
    
    // END Move all to a Configurer object
        
}

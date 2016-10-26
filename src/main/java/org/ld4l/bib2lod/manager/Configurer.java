package org.ld4l.bib2lod.manager;

import java.io.File;
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
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Configurer {

    private static final Logger LOGGER = 
            LogManager.getLogger(Configurer.class);
    
    private static final String DEFAULT_CONFIG_FILE = 
            "src/main/resources/config.json";
    
    private String[] args;
    
    public Configurer(String[] args) {
        
        this.args = args;

    }
    
    // TODO Using this approach - returning a gson JsonObject rather than a 
    // file, filename, or string - requires users to also use gson rather than
    // using whatever json library they choose. However, if we are also using
    // gson inside the core converter, it doesn't matter. Also, this is 
    // probably just within the manager package, so if someone writes their 
    // own package they can do it differently.
    public JsonObject getConfig() throws IOException  {
        
        JsonObject jsonConfig = null;

        Options options = getOptions();
        
        // Get commandline options
        CommandLine cmd = getCommandLine(options, args);

        String config = cmd.getOptionValue("config");
        
        // If no commandline config file arg, use default location
        if (config == null) {
            config = DEFAULT_CONFIG_FILE;
        }        
        
        File configFile = new File(DEFAULT_CONFIG_FILE);

        String jsonString = FileUtils.readFileToString(configFile);
        JsonParser parser = new JsonParser();
        jsonConfig = parser.parse(jsonString).getAsJsonObject();

        return jsonConfig;
    }


    /**
     * Define the commandline options accepted by the program.
     * @return an Options object
     */
    private Options getOptions() {
        
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
    private CommandLine getCommandLine(Options options, String[] args) {
        
        // Parse program arguments
        CommandLineParser parser = new DefaultParser();
        
        // TODO What to do here? throw the exception?
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
    private void printHelp(Options options) {
        
        HelpFormatter formatter = new HelpFormatter();
        formatter.setWidth(80);
        formatter.printHelp("java -jar Bib2Lod.jar", options, true);
    }
    
}

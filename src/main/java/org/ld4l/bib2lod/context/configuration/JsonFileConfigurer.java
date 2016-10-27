package org.ld4l.bib2lod.context.configuration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonFileConfigurer extends BaseConfigurer {

    private static final Logger LOGGER = 
            LogManager.getLogger(JsonFileConfigurer.class); 
    
    private static final String DEFAULT_CONFIG_FILE = 
            "src/main/resources/config.json";
    
    private String[] args;
    
    public JsonFileConfigurer(String[] args) {
        
        this.args = args;

    }
    
    // TODO Using this approach - returning a gson JsonObject rather than a 
    // file, filename, or string - requires users to also use gson rather than
    // using whatever json library they choose. However, if we are also using
    // gson inside the core converter, it doesn't matter. Consider this later.
    /** 
     * Get config parameters, either from config file or from commandline option
     * values. 
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public JsonObject getConfig() throws IOException, ParseException {
        
        JsonObject jsonConfig = null;

        Options options = buildOptions();
        
        // Get commandline options
        CommandLine cmd = getCommandLine(options, args);

        String configFilename = cmd.getOptionValue("config");
        
        // If no commandline config file arg, use default location
        if (configFilename == null) {
            configFilename = DEFAULT_CONFIG_FILE;
        }        
        
        File configFile = new File(configFilename);

        try {
            String jsonString = FileUtils.readFileToString(configFile);
            JsonParser parser = new JsonParser();
            jsonConfig = parser.parse(jsonString).getAsJsonObject();
            
            // Note: currently the only commandline option is the config file 
            // location. Later others may be supported, in which case this 
            // will method override the config file values with the commandline 
            // option values and return the result.
            return jsonConfig;
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new IOException("Error reading config file " +
                    "configFilename", e);
        }

    }


    /**
     * Define the commandline options accepted by the program.
     * @return an Options object
     */
    private Options buildOptions() {
        
        Options options = new Options();

        options.addOption(Option.builder("c")
                .longOpt("config")
                .required(false)
                .hasArg()
                .argName("config")
                .desc("Config file location. Defaults to " +
                        "/src/main/resources/config.json")
                .build());           

        return options;
    }

    /**
     * Parse commandline options.
     * @param options
     * @param args
     * @return
     * @throws ParseException
     */
    private CommandLine getCommandLine(Options options, String[] args) 
            throws ParseException {
        
        // Parse program arguments
        CommandLineParser parser = new DefaultParser();
        
        // TODO Throws MissingOptionException, UnrecognizedOptionException, and
        // general ParseException. Check if error messages are specific enough.
        // If not, catch, add message, and throw.
        return parser.parse(options, args);           
    }

//    /**
//     * Print help text.
//     * @param options
//     */
    // TODO Currently not used. What is best way to do this? If print here,
    // need to catch exceptions and return null up the chain. If print from
    // manager or elsewhere, need to supply a method to provide the help string.
    // See HelpFormatter.renderOptions() or HelpFormatter.renderWrappedText().
//    private void printHelp(Options options) {
//        
//        HelpFormatter formatter = new HelpFormatter();
//        formatter.setWidth(80);
//        formatter.printHelp("java -jar Bib2Lod.jar", options, true);
//    }

    
}

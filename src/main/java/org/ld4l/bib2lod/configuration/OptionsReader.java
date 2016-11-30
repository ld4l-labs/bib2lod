package org.ld4l.bib2lod.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Reads commandline arguments and gets configuration option values from either
 * these or the configuration file.
 * Currently the only commandline arguments supported is the configuration file
 * path. Other values must be defined in the file.
 * @author rjy7
 *
 */
public class OptionsReader {

    private static final Logger LOGGER = 
            LogManager.getLogger(OptionsReader.class); 

    protected String[] args;
    
    public OptionsReader(String[] args)  {
        this.args = Objects.requireNonNull(args);
    }

    
    /**
     * Gets the defined options, gets the configuration file from the program
     * arguments, and reads the file into a JSON object.
     * @return JsonNode
     * @throws IOException
     * @throws ParseException
     */
    // TODO Using this approach - returning a Jackson JsonNode rather than a 
    // file, filename, or string - requires users to also use Jackson rather 
    // than using whatever json library they choose. However, if we are also 
    // using Jackson inside the core converter, it doesn't matter. Consider this 
    // later. 
    public JsonNode configure() throws IOException, ParseException {
        
        // Get the defined options
        Options options = buildOptions();
        
        // Get commandline option values
        CommandLine cmd = parseCommandLine(options, args);
        
        Reader reader = findConfigFile(cmd); // add a test 
        JsonNode node = parseConfigFile(reader); // add a test with new StringReader()
        return node;
        
    }

    /**
     * Defines the commandline options accepted by the program.
     * @return Options
     */
    private Options buildOptions() {
        
        Options options = new Options();

        options.addOption(Option.builder("c")
                .longOpt("config")
                .required(false)
                .hasArg()
                .argName("config")
                .desc("Config file location")
                .build());           

        return options;
    }
    
    /**
     * Gets the configuration file location from the commandline option values.
     * Returns a Reader for the file.
     * @return Reader
     * @throws ParseException
     * @throws FileNotFoundException
     */
    private Reader findConfigFile(CommandLine cmd) 
            throws ParseException, FileNotFoundException {
        
        String configFilename = cmd.getOptionValue("config");
        
        // If no commandline config file arg, use default location
        // TODO Remove and throw an error instead
        if (configFilename == null) {
            // configFilename = DEFAULT_CONFIG_FILE;
            throw new IllegalArgumentException();
        } 
        
        Reader reader = new FileReader(configFilename);
        return reader;
    }
    
    /**
     * Parses the configuration file into a JSON object.
     * @param reader
     * @return JsonNode
     * @throws JsonParseException
     * @throws JsonProcessingException
     * @throws IOException
     */
    private JsonNode parseConfigFile(Reader reader) throws JsonParseException, 
            JsonProcessingException, IOException {
        
        JsonNode config = null;
        
        ObjectMapper mapper = new ObjectMapper();
        config = mapper.readTree(reader);
        if (config.isNull()) {
            throw new IOException("Encountered empty JSON config file");                   
        }
        
        // Note: currently the only commandline option is the config file 
        // location. Later others may be supported, in which case this 
        // will method override the config file values with the commandline 
        // option values and return the result.
        return config;
        
    }


    /**
     * Parses commandline options.
     * @param options
     * @param args
     * @return CommandLine
     * @throws ParseException
     */
    protected CommandLine parseCommandLine(Options options, String[] args) 
            throws ParseException {
        
        // Parse program arguments. parser.parse() throws 
        // UnrecognizedOptionException for unsupported options, so easier to 
        // follow this than try to ignore undefined options.
        CommandLineParser parser = new DefaultParser();    
        return parser.parse(options, args);     
    }

}

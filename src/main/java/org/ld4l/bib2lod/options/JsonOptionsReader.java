package org.ld4l.bib2lod.options;

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
 * Get configuration values from a JSON config file. The location of the config
 * file is required as a commandline argument.
 */
public class JsonOptionsReader extends AbstractOptionsReader {

    private static final Logger LOGGER = 
            LogManager.getLogger(JsonOptionsReader.class); 

    protected String[] args;
    
    public JsonOptionsReader(String[] args)  {
        this.args = Objects.requireNonNull(args);
    }
    
    
    /**
     * Gets the defined options, gets the configuration file from the program
     * arguments, and reads the file into a JSON object.
     * @return a JsonNode built from config file plus commandline arguments
     * @throws IOException
     * @throws ParseException
     */
    public JsonNode configure() throws IOException, ParseException {
        
        // Get the defined options
        Options options = buildOptions();

        // Get the commandline values for these options
        CommandLine cmd = parseCommandLineArgs(options, args); 
        
        // Parse the config file
        return parseConfigFile(cmd);    
    }

    /**
     * Defines the commandline options accepted by the program.
     * @return options - the program options
     */
    Options buildOptions() {
        
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
     * 
     * @param options - the supported options 
     * @param args - the commandline arguments
     * @return the list of options and commandline values
     * @throws ParseException
     */
    CommandLine parseCommandLineArgs(Options options, String[] args) 
            throws ParseException {
        
        // parser.parse() throws UnrecognizedOptionException for unsupported 
        // options, so follow this rather than try to ignore undefined options. 
        CommandLineParser parser = new DefaultParser();    
        return parser.parse(options, args);  
    }
    
    /**
     * Retrieves and parses the config file specified in the program arguments.
     * @param cmd - the commandline values
     * @return a JsonNode containing the config file values
     * @throws ParseException
     * @throws JsonParseException
     * @throws JsonProcessingException
     * @throws IOException
     */
    JsonNode parseConfigFile(CommandLine cmd) 
            throws ParseException, JsonParseException, JsonProcessingException, 
            IOException {
        
        Reader reader = findConfigFile(cmd); 
        return parseConfigFile(reader);        
    }
    
    /**
     * Gets the configuration file location from the commandline option values.
     * @return a Reader to the file
     * @throws ParseException
     * @throws FileNotFoundException
     */
    private Reader findConfigFile(CommandLine cmd) 
            throws ParseException, FileNotFoundException {
        
        String configFilename = cmd.getOptionValue("config");
        
        if (configFilename == null) {
            throw new IllegalArgumentException();
        } 
        
        Reader reader = new FileReader(configFilename);
        return reader;
    }
    
    /**
     * Parses the configuration file into a JSON object.
     * @param reader
     * @return config - a JsonNode containing the config file values
     * @throws JsonParseException
     * @throws JsonProcessingException
     * @throws IOException
     */
    private JsonNode parseConfigFile(Reader reader) throws JsonParseException, 
            JsonProcessingException, IOException {
        
        JsonNode config = null;
        
        ObjectMapper mapper = new ObjectMapper();
        config = mapper.readTree(reader);
        return config;
        
    }
    

}

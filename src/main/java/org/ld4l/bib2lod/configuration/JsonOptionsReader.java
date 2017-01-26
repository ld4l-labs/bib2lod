/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
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
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Reads commandline arguments, reads in the config file, and sets configuration 
 * options. Any supported commandline option values override those specified in 
 * the config file.
 * 
 * Currently the only commandline argument supported is the config file path.
 * All others must be defined in the config file.
 */
public class JsonOptionsReader implements OptionsReader {

    private static final Logger LOGGER = LogManager.getLogger();
             
    private String[] args;
    
    /**
     * Constructor. 
     * @param args - the program arguments
     */
    public JsonOptionsReader(String[] args)  {
        this.args = Objects.requireNonNull(args);
    }
    
    /**
     * Gets the defined options, gets the configuration file from the program
     * arguments, reads the file into a JSON object, and applies any overrides
     * from commandline values.
     * @return a JsonNode built from config file plus commandline arguments
     * @throws IOException
     * @throws ParseException
     */
    @Override
    public JsonNode configure() throws IOException, ParseException {
        
        Options options = defineOptions();

        CommandLine cmd = parseCommandLineArgs(options, args); 
        
        JsonNode node = parseConfigFile(cmd);

        return applyCommandLineOverrides(node, cmd);      
    }

    /**
     * Defines the commandline options accepted by the program.
     * @return options - the program options
     */
    Options defineOptions() {
        
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
        
        String configFileName = cmd.getOptionValue("config");
        
        if (configFileName == null) {
            throw new IllegalArgumentException();
        } 
        
        Reader reader = new FileReader(configFileName);
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
    
    /**
     * Overrides config file values with commandline option values.
     * @param jsonNode - the JSON node containing config file values
     * @param cmd - the commandline options
     * @return objNode - a JsonNode built by overriding config file values with
     * corresponding commandline values
     */
    JsonNode applyCommandLineOverrides(JsonNode jsonNode, CommandLine cmd) {
            
        // A JsonNode is immutable, so cast to mutable ObjectNode
        ObjectNode objNode = (ObjectNode) jsonNode;
        
        // Give preference to commandline option value over config option value.
        Iterator<Option> it = cmd.iterator();
        while (it.hasNext()) {
            Option opt = it.next();
            String optName = opt.getLongOpt();
            LOGGER.debug("arg name = " + optName);
            // Makes no sense for config file to specify config file!
            if (! optName.equals("config")) {
                objNode.put(optName, cmd.getOptionValue(optName));
            } 
        }
        
        return objNode;  
    }

}

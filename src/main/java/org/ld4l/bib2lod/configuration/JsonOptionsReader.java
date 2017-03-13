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
    public JsonNode configure() {
        
        Options options = defineOptions();

        CommandLine cmd;
        cmd = parseCommandLineArgs(options, args);
        JsonNode node = parseConfigFile(cmd);
        return applyCommandLineOverrides(node, cmd);     
    }

    /**
     * Defines the commandline options accepted by the program.
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
    

    CommandLine parseCommandLineArgs(Options options, String[] args) {
        
        CommandLineParser parser = new DefaultParser();    
        try {
            return parser.parse(options, args);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            throw new OptionsReaderException(e);
        }  
    }
    
    /**
     * Retrieves and parses the config file specified in the program arguments.
     * @param cmd - the commandline values
     * @return a JsonNode containing the config file values
     */
    JsonNode parseConfigFile(CommandLine cmd) {
        
        Reader reader;
        reader = findConfigFile(cmd);
        return parseConfigFile(reader);                     
    }
    
    /**
     * Gets the configuration file location from the commandline option values.
     * @return a Reader to the file
     */
    private Reader findConfigFile(CommandLine cmd) {
        
        String configFileName = cmd.getOptionValue("config");
        
        if (configFileName == null) {
            throw new IllegalArgumentException("Config file cannot be null");
        } 
        
        try {
            return new FileReader(configFileName);
        } catch (FileNotFoundException e) {
            throw new OptionsReaderException(e);
        }        
    }
    
    /**
     * Parses the configuration file into a JSON object.
     * @param reader - a Reader to the configuration file
     */
    private JsonNode parseConfigFile(Reader reader) {
        
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readTree(reader);
        } catch (IOException e) {
            throw new OptionsReaderException(e);
        }
        
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

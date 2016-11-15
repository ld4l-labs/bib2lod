package org.ld4l.bib2lod.configuration;

import java.io.File;
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
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OptionsReader {

    private static final Logger LOGGER = 
            LogManager.getLogger(OptionsReader.class); 
    
    private static final String DEFAULT_CONFIG_FILE = 
            "src/main/resources/config.json";

    protected String[] args;
    
    public OptionsReader(String[] args)  {
        this.args = Objects.requireNonNull(args);
    }

    
    /**
     * Get configuration option values from config file or commandline;
     * commandline overrides config file.
     * @return
     * @throws IOException
     * @throws ParseException
     */
    // TODO Using this approach - returning a Jackson JsonNode rather than a 
    // file, filename, or string - requires users to also use Jackson rather 
    // than using whatever json library they choose. However, if we are also 
    // using Jackson inside the core converter, it doesn't matter. Consider this 
    // later. 
    public JsonNode configure() throws IOException, ParseException {
        
        Reader reader = findConfigFile(); // add a test 
        JsonNode node = processConfigFile(reader); // add a test with new StringReader()
        return node;
        
    }

    private JsonNode processConfigFile(Reader reader) throws IOException {
        
        JsonNode config = null;
        
        try {
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
            
        } catch (JsonParseException e) {
            throw new IOException(
                    "Encountered ill-formed JSON in config file", e);

        } catch (JsonProcessingException e) {
            throw new IOException(
                    "Error encountered processing JSON config file", e);
               
        } catch (IOException e) {
            throw new IOException("Error reading config file " +
                    "configFilename", e);
        } 

    }


    private Reader findConfigFile() throws ParseException, FileNotFoundException  {
        
        Options options = buildOptions();
        
        // Get commandline options
        CommandLine cmd = getCommandLine(options, args);

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
                .desc("Config file location")
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
    protected CommandLine getCommandLine(Options options, String[] args) 
            throws ParseException {
        
        // Parse program arguments
        CommandLineParser parser = new DefaultParser();    
        return parser.parse(options, args);           
    }

}

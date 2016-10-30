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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFileConfigurer extends BaseConfigurer {



    private static final Logger LOGGER = 
            LogManager.getLogger(JsonFileConfigurer.class); 
    
    private static final String DEFAULT_CONFIG_FILE = 
            "src/main/resources/config.json";

 
    public JsonFileConfigurer(String[] args) {
        super(args);
    }

    /**
     * 
     * @return
     * @throws IOException
     * @throws ParseException
     */
    // TODO Using this approach - returning a Jackson JsonNode rather than a 
    // file, filename, or string - requires users to also use Jackson rather 
    // than using whatever json library they choose. However, if we are also 
    // using Jackson inside the core converter, it doesn't matter. Consider this 
    // later. 
    public JsonNode getConfig() throws IOException, ParseException {
        
        JsonNode jsonConfig = null;
        
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
            ObjectMapper mapper = new ObjectMapper();
            jsonConfig = mapper.readTree(configFile);
            
            // Note: currently the only commandline option is the config file 
            // location. Later others may be supported, in which case this 
            // will method override the config file values with the commandline 
            // option values and return the result.
            return jsonConfig;
            
        } catch (JsonParseException e) {
            throw new IOException("Encountered non-well-formed JSON in config file", e);

        } catch (JsonProcessingException e) {
            throw new IOException("Error encountered processing JSON config file", e);
               
        } catch (IOException e) {
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





    
}

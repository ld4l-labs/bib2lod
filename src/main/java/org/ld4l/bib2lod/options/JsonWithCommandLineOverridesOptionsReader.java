package org.ld4l.bib2lod.options;

import java.util.Iterator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Reads commandline arguments and gets configuration option values from either
 * these or the configuration file.
 * Currently the only commandline arguments supported is the configuration file
 * path. Other values must be defined in the file.
 *
 */
public class JsonWithCommandLineOverridesOptionsReader 
        extends JsonOptionsReader {
        

    private static final Logger LOGGER = 
            LogManager.getLogger(
                    JsonWithCommandLineOverridesOptionsReaderTest.class); 

    protected String[] args;
    
    public JsonWithCommandLineOverridesOptionsReader(String[] args)  {
        super(args);
    }
    
    /**
     * Defines the commandline options accepted by the program.
     * @return options - the program options
     */
    Options buildOptions() {
        
        Options options = super.buildOptions();

        // TODO What happens if an option is already defined in 
        // super.buildOptions()? Will this override it? Maybe we should not
        // invoke super.buildOptions().
        options.addOption(Option.builder("l")
                .longOpt("local_namespace")
                .required(false)
                .hasArg()
                .argName("local_namespace")
                .desc("Local namespace for minting URIs")
                .build()); 
        
        // TODO Add options for remaining config properties

        return options;
    }
    
    /**
     * Override config file values with commandline option values.
     * @param jsonNode
     * @param cmd
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

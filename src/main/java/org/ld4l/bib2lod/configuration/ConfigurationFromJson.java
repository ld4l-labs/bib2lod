/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.utils.JsonUtils;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * Implementation built from a JSON configuration file.
 */
public class ConfigurationFromJson extends BaseConfiguration {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Represents keys in the incoming JSON configuration.
     */
    protected enum Key {
        
        CONVERTER("converter"),
        INPUT("input"),
        INPUT_SOURCE("source"),
        LOCAL_NAMESPACE("local_namespace"),
        URI_GETTER("uri_getter");
        
        final String string;
        
        Key(String string) {
            this.string = string;
        }
    }


    /**
     * Constructor
     * @param args - the program arguments 
     */
    public ConfigurationFromJson(String[] args) {
        
        // Get the configuration values from the commandline values and 
        // specified config file as a JSON object.
        JsonNode config = OptionsReader.instance(args).configure();
        LOGGER.debug(config.toString());
        setLocalNamespace(config);
        
        // TODO Add same for other config elements, if/when we support other
        // commandline options...

    }


    /**                                                                                                                          
     * Sets the local namespace, if valid. An exception is thrown down the
     * chain if the local namespace is invalid.                   
     * @param config - the JsonNode containing the configuration values
     * @return void
     */
    protected void setLocalNamespace(JsonNode config) {
        
        // TODO BaseConfiguration.setLocalNamespace will need to test for
        // non-existent/empty/null values, to apply to any type of Configuration.
        // So this test is redundant. Should we eliminate the JsonUtils tests
        // and just get the value? The move JsonUtilsTest tests to 
        // BaseConfigurationTest.

        String localNamespace = JsonUtils.getRequiredStringValue(
                config, Key.LOCAL_NAMESPACE.string);
        
        super.setLocalNamespace(localNamespace);                       
    }

}

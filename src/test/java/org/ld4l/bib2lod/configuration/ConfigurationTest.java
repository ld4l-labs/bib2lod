package org.ld4l.bib2lod.configuration;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Test plan: 
 * 
 * No configuration object returned from Configuration constructor - exception
 * No input file list returned from Configuration.getInput() - exception
 * File rather than list of files returned from Configuration.getInput()
 * 
 * No local namespace in config - exception
 * Local namespace empty - exception
 * Local namespace null - exception
 * Local namespace not a string - exception
 * Local namespace not well-formed URI - exception
 * 
 * No input in config - exception
 * Input value empty - exception
 * Input value null - exception
 * Input not a string - exception
 * Input location doesn't exist - exception
 * Input location a directory but is empty - succeed, does nothing
 * IO exception - exception
 * 
 * No output in config - exception
 * output value empty - exception
 * output value null - exception
 * output not an object - exception
 * 
 * No output location in config - exception
 * Location value empty - exception
 * Location value null - exception
 * Location value not a string - exception
 * output location doesn't exist - exception
 * output location not a directory
 * output location not writable
 * Any IO exception - exception
 * 
 * No format in output object - ok - use default
 * Format null - ok - use default
 * format empty - ok - use default
 * format not a string - ok - use default
 * format not a valid rdf serialization value - ok - use default
 * 
 * No log in config - exception
 * Log value empty - exception
 * Log value null - exception
 * Log value not a string - exception
 * Log value doesn't exist - exception
 * Log value not a directory - exception
 * Log value not writable - exception
 * Any IO exception - exception
 * 
 * No services in config
 * Services empty
 * Services null
 * Services not an object
 * 
 * No UriMinter in config - exception
 * UriMinter empty - exception
 * UriMinter null - exception
 * URI minter class doesn't exist - exception
 * Uri minter class doesn't extend AbstractUriMinter - exception
 * Can't create Uri minter instance - exception
 * 
 * No writer in config - exception
 * writer empty - exception
 * writer null - exception
 * writer class doesn't exist - exception
 * writer class doesn't extend AbstractWriter - exception
 * Can't create writer instance - exception
 * 
 * TODO later: add same other services
 * 
 * No converter in config - succeed and do nothing (may only to cleaning, for example)
 * Converter empty - succeed and do nothing (may only to cleaning, for example)
 * converter null - succeed and do nothing (may only to cleaning, for example)
 * Converter not a string - exception
 * TODO Change to an array or a string
 * converter class doesn't exist - exception
 * converter doesn't extend AbstractConverter - exception
 * Convert can't be instantiated - exception
 * 
 * No reconcilers in config - succeed
 * reconcilers empty - succeed
 * reconcilers null - succeed
 * reconciler not an array - exception
 * reconciler an empty array - succeed and do nothing 
 * 
 */
public class ConfigurationTest extends AbstractTestClass {

    private static JsonNode CONFIG = null;

    @Before
    public void setUpConfiguration() throws Exception {

        File configFile = new File("src/test/resources/config/config.json");
        ObjectMapper mapper = new ObjectMapper();
        CONFIG = mapper.readTree(configFile);
    }

    @Test
    public void setLocalNamespace() {

    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.ld4l.bib2lod.test.AbstractTestClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;


/**
 * Tests of org.ld4l.bib2lod.configuration.JsonOptionsReader
 */
public class JsonOptionsReaderTest extends AbstractTestClass {
   
    private static final String LOCAL_NAMESPACE = 
            "http://local.namespace.org/test/";
    
    private static final String TEST_CONFIG_DIR = 
            "src/test/resources/configuration/options_reader/";
    
    private static final String INVALID_OPTION = "--invalid";
    private static final String INVALID_OPTION_VALUE = "invalid option value";
            
    private enum Path {

        VALID_CONFIG_FILE(TEST_CONFIG_DIR + "valid_config.json"),
        MISSING_CONFIG_FILE(TEST_CONFIG_DIR + "no_config.json"),
        EMPTY_CONFIG_FILE(TEST_CONFIG_DIR + "empty_config.json"),
        MALFORMED_CONFIG_FILE(TEST_CONFIG_DIR + "malformed_config.json"),
        NON_JSON_EXTENSION(TEST_CONFIG_DIR + "config.txt"),
        CONFIG_DIR(TEST_CONFIG_DIR + "config_dir");
               
        final String name;

        Path(String name) {
            this.name = name;
        }
    }
    
    JsonOptionsReader reader;
    JsonNode node;

    private void configureOptionsReader(String[] args) throws Exception {
        reader = new JsonOptionsReader(args);
        node = reader.configure();
    }

    /*
     * Args tests
     */
    
    @Test (expected = NullPointerException.class)
    public void argsIsNull_ThrowsException() {
        new JsonOptionsReader(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void argsIsEmpty_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {});
    }
    
    @Test 
    public void providingPathUsingShortForm_Succeeds() throws Exception {       
        configureOptionsReader(
                new String[] {"-c", Path.VALID_CONFIG_FILE.name});
        assertNotNull(node);
    }
    
    @Test 
    public void providingPathUsingLongForm_Succeeds() throws Exception {
        configureOptionsReader(
                new String[] {"--config", Path.VALID_CONFIG_FILE.name});
        assertNotNull(node);
    }
    
    @Test (expected = MissingArgumentException.class)
    public void providingNoPath_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {"--config"});
    }
    
    @Test (expected = ParseException.class)
    public void invalidOption_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {INVALID_OPTION, INVALID_OPTION_VALUE});
    }
   
    @Test (expected = ParseException.class)
    public void invalidOptionWithValidOption_ThrowsException() 
            throws Exception {
        configureOptionsReader(new String[] {"--config", 
                Path.VALID_CONFIG_FILE.name, 
                INVALID_OPTION, INVALID_OPTION_VALUE});
                           
    }
    
    @Test (expected = ParseException.class)
    public void invalidOptionWithoutValue_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {INVALID_OPTION}); 
    }
    
    
    /*
     * Config file tests
     */
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotFound_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", Path.MISSING_CONFIG_FILE.name});
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotReadable_ThrowsException() throws Exception {
        JsonOptionsReader reader = new JsonOptionsReader(
                new String[] {"--config", Path.VALID_CONFIG_FILE.name});
        File file = new File(Path.VALID_CONFIG_FILE.name);
        file.setReadable(false);
        try {
            reader.configure();
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();
        } finally {
            file.setReadable(true);
        }        
    }
    
    @Test (expected = JsonProcessingException.class)
    public void configFileEmpty_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", Path.EMPTY_CONFIG_FILE.name});
    }
    
    @Test (expected = JsonParseException.class)
    public void configFileMalformed_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", Path.MALFORMED_CONFIG_FILE.name});
    }
    
    @Test 
    public void nonJsonExtension_Succeeds() throws Exception {
        configureOptionsReader(
                new String[] {"--config", Path.NON_JSON_EXTENSION.name});
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileIsDirectory_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {"--config", Path.CONFIG_DIR.name});
    }
    
    /*
     * Commandline overrides
     */
    
    @Test 
    public void commandLineOverridesConfigFile() throws Exception { 
 
        String[] args = new String[] {"-c", Path.VALID_CONFIG_FILE.name, 
                "--local_namespace", LOCAL_NAMESPACE};
        
        reader = new JsonOptionsReader(args);
        
        Options options = reader.defineOptions();
        
        options.addOption(Option.builder("l")
                .longOpt("local_namespace")
                .required(false)
                .hasArg()
                .argName("local_namespace")
                .desc("Local namespace used to build URIs")
                .build());    
        
        // Get the commandline values for these options
        CommandLine cmd = reader.parseCommandLineArgs(options, args); 
        
        // Parse the config file
        JsonNode jsonNode = reader.parseConfigFile(cmd);

        // Commandline option values override config file values
        JsonNode node = reader.applyCommandLineOverrides(jsonNode, cmd);
        String localNamespace = node.get("local_namespace").textValue();
        assertEquals(LOCAL_NAMESPACE, localNamespace);
              
    }
  
}
    
//    Stub out the test:
//    @Test
//    public void sampleTest() {
//        fail("sampleTest not implemented");
//    }



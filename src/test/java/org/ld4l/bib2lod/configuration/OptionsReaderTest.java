package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Tests for org.ld4l.bib2lod.configuration.OptionsReader
 * 
 * @author rjy7
 *
 */
public class OptionsReaderTest extends AbstractTestClass {
    
    private static final String LOCAL_NAMESPACE = 
            "http://local.namespace.org/test/";
    private static final String CONFIG_FILENAME = 
            "src/test/resources/config/config.json";
    private static final String MISSING_CONFIG_FILENAME = 
            "src/test/resources/config/no_config.json";
    private static final String CONFIG_DIRNAME = 
            "src/test/resources/config/config_dir";
    private static final String UNREADABLE_CONFIG_FILENAME = 
            "src/test/resources/config/unreadable_config.json";
    private static final String EMPTY_CONFIG_FILENAME = 
            "src/test/resources/config/empty_config.json";
    private static final String MALFORMED_CONFIG_FILENAME = 
            "src/test/resources/config/malformed_config.json";
    private static final String INVALID_OPTION = "--invalid";
    private static final String INVALID_OPTION_VALUE = 
            "invalid option value";
    
    private JsonNode configureOptionsReader(String[] args) throws Exception {
        OptionsReader reader = new OptionsReader(args);
        JsonNode node = reader.configure();
        return node;
    }

    @Test (expected = NullPointerException.class)
    public void argsIsNull_ThrowsException() {
        new OptionsReader(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void argsIsEmpty_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {});
    }
    
    @Test 
    public void providingPathUsingShortForm_Succeeds() throws Exception {       
        JsonNode config = configureOptionsReader(
                new String[] {"-c", CONFIG_FILENAME});
        assertNotNull(config);
    }
    
    @Test 
    public void providingPathUsingLongForm_Succeeds() throws Exception {
        JsonNode config = configureOptionsReader(
                new String[] {"--config", CONFIG_FILENAME});
        assertNotNull(config);
    }
    
    @Test (expected = MissingArgumentException.class)
    public void providingNoPath_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {"--config"});
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotFound_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", MISSING_CONFIG_FILENAME});
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotReadable_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(
                new String[] {"--config", UNREADABLE_CONFIG_FILENAME});
        File file = new File(UNREADABLE_CONFIG_FILENAME);
        file.setReadable(false);
        try {
            reader.configure();
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException();
        } finally {
            file.setReadable(true);
        }        
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileIsDirectory_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {"--config", CONFIG_DIRNAME});
    }
    
    @Test (expected = IOException.class)
    public void emptyConfigFile_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", EMPTY_CONFIG_FILENAME});
    }
    
    @Test (expected = JsonParseException.class)
    public void malFormedConfigFile_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", MALFORMED_CONFIG_FILENAME});
    }
    
    @Test (expected = ParseException.class)
    public void invalidOption_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {INVALID_OPTION, INVALID_OPTION_VALUE});
    }
   
    @Test (expected = ParseException.class)
    public void invalidOptionWithValidOption_ThrowsException() throws Exception {
        configureOptionsReader(
                new String[] {"--config", CONFIG_FILENAME, INVALID_OPTION, 
                        INVALID_OPTION_VALUE});   
    }
    
    @Test (expected = ParseException.class)
    public void invalidOptionWithoutArgument_ThrowsException() throws Exception {
        configureOptionsReader(new String[] {INVALID_OPTION}); 
    }
    
    @Test 
    public void commandLineOverridesConfigFile() throws Exception { 
 
        String[] args = new String[] {"-c", CONFIG_FILENAME, 
                "--localNamespace", LOCAL_NAMESPACE};
        
        OptionsReader reader = new OptionsReader(args);
        
        Options options = reader.buildOptions();
        
        options.addOption(Option.builder("l")
                .longOpt("localNamespace")
                .required(false)
                .hasArg()
                .argName("localNamespace")
                .desc("Local namespace used to build URIs")
                .build());    
        
        // Get the commandline values for these options
        CommandLine cmd = reader.parseCommandLineArgs(options, args); 
        
        // Parse the config file
        JsonNode jsonNode = reader.parseConfigFile(cmd);

        // Commandline option values override config file values
        JsonNode node = reader.applyCommandLineOverrides(jsonNode, cmd);
        String localNamespace = node.get("localNamespace").textValue();
        assertEquals(LOCAL_NAMESPACE, localNamespace);
              
    }
       
    
//    Start the test this way.
//    @Test
//    public void sampleTest() {
//        fail("sampleTest not implemented");
//    }
    
}

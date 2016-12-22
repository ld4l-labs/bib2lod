package org.ld4l.bib2lod.configuration.options;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.ParseException;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.options.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.options.OptionsReader;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

/**
 * Tests for org.ld4l.bib2lod.configuration.JsonConfigOptionsReader
 *
 */
public class JsonOptionsReaderTest extends AbstractTestClass {

    private static final String TEST_CONFIG_DIR = 
            "src/test/resources/options_reader/";
    
    private static final String VALID_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "valid_config.json";
    
    private static final String MISSING_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "no_config.json";
    private static final String CONFIG_DIRNAME = 
            TEST_CONFIG_DIR + "config_dir";
    private static final String UNREADABLE_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "unreadable_config.json";
    private static final String EMPTY_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "empty_config.json";
    private static final String MALFORMED_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "malformed_config.json";
    private static final String NON_JSON_EXTENSION = 
            TEST_CONFIG_DIR + "config.txt";
    
    private static final String INVALID_OPTION = "--invalid";
    private static final String INVALID_OPTION_VALUE = 
            "invalid option value";
    
//    private JsonNode configureOptionsReader(String[] args) throws Exception {
//        OptionsReader reader = new JsonOptionsReader(args);
//        JsonNode node = reader.configure();
//        return node;
//    }
//
//    @Test (expected = NullPointerException.class)
//    public void argsIsNull_ThrowsException() {
//        new JsonOptionsReader(null);
//    }
//
//    @Test (expected = IllegalArgumentException.class)
//    public void argsIsEmpty_ThrowsException() throws Exception {
//        configureOptionsReader(new String[] {});
//    }
//    
//    @Test 
//    public void providingPathUsingShortForm_Succeeds() throws Exception {       
//        JsonNode config = configureOptionsReader(
//                new String[] {"-c", VALID_CONFIG_FILENAME});
//        assertNotNull(config);
//    }
//    
//    @Test 
//    public void providingPathUsingLongForm_Succeeds() throws Exception {
//        JsonNode config = configureOptionsReader(
//                new String[] {"--config", VALID_CONFIG_FILENAME});
//        assertNotNull(config);
//    }
//    
//    @Test (expected = MissingArgumentException.class)
//    public void providingNoPath_ThrowsException() throws Exception {
//        configureOptionsReader(new String[] {"--config"});
//    }
//    
//    @Test (expected = FileNotFoundException.class)
//    public void configFileNotFound_ThrowsException() throws Exception {
//        configureOptionsReader(
//                new String[] {"--config", MISSING_CONFIG_FILENAME});
//    }
//    
//    @Test (expected = FileNotFoundException.class)
//    public void configFileNotReadable_ThrowsException() throws Exception {
//        JsonOptionsReader reader = new JsonOptionsReader(
//                new String[] {"--config", UNREADABLE_CONFIG_FILENAME});
//        File file = new File(UNREADABLE_CONFIG_FILENAME);
//        file.setReadable(false);
//        try {
//            reader.configure();
//        } catch(FileNotFoundException e) {
//            throw new FileNotFoundException();
//        } finally {
//            file.setReadable(true);
//        }        
//    }
//    
//    @Test (expected = FileNotFoundException.class)
//    public void configFileIsDirectory_ThrowsException() throws Exception {
//        configureOptionsReader(new String[] {"--config", CONFIG_DIRNAME});
//    }
//    
//    @Test (expected = JsonProcessingException.class)
//    public void emptyConfigFile_ThrowsException() throws Exception {
//        configureOptionsReader(
//                new String[] {"--config", EMPTY_CONFIG_FILENAME});
//    }
//    
//    @Test (expected = JsonParseException.class)
//    public void malFormedConfigFile_ThrowsException() throws Exception {
//        configureOptionsReader(
//                new String[] {"--config", MALFORMED_CONFIG_FILENAME});
//    }
//    
//    @Test 
//    public void nonJsonExtension_Succeeds() throws Exception {
//        configureOptionsReader(
//                new String[] {"--config", NON_JSON_EXTENSION});
//    }
//    
//    @Test (expected = ParseException.class)
//    public void invalidOption_ThrowsException() throws Exception {
//        configureOptionsReader(
//                new String[] {INVALID_OPTION, INVALID_OPTION_VALUE});
//    }
//   
//    @Test (expected = ParseException.class)
//    public void invalidOptionWithValidOption_ThrowsException() throws Exception {
//        configureOptionsReader(
//                new String[] {"--config", VALID_CONFIG_FILENAME, INVALID_OPTION, 
//                        INVALID_OPTION_VALUE});   
//    }
//    
//    @Test (expected = ParseException.class)
//    public void invalidOptionWithoutArgument_ThrowsException() throws Exception {
//        configureOptionsReader(new String[] {INVALID_OPTION}); 
//    }
//    

    
//    Start the test this way.
//    @Test
//    public void sampleTest() {
//        fail("sampleTest not implemented");
//    }
    
}

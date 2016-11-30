package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.MissingArgumentException;
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
    private static final String UNSUPPORTED_OPTION = "--invalid";
    private static final String UNSUPPORTED_OPTION_VALUE = 
            "invalid option value";
    

    @Test (expected = NullPointerException.class)
    public void argsNull_ThrowsException() {
        new OptionsReader(null);
             
    }

    @Test (expected = IllegalArgumentException.class)
    public void argsIsEmpty_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {});
        reader.configure();
    }
    
    @Test 
    public void providingPathUsingShortForm_Succeeds() throws Exception {
        OptionsReader reader = 
                new OptionsReader(new String[] {"-c", CONFIG_FILENAME});
        JsonNode config = reader.configure();
        assertNotNull(config);
    }
    
    @Test 
    public void providingPathUsingLongForm_Succeeds() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", CONFIG_FILENAME});
        JsonNode config = reader.configure();
        assertNotNull(config);
    }
    
    @Test (expected = MissingArgumentException.class)
    public void providingNoPath_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config"});
        reader.configure();
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotFound_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", MISSING_CONFIG_FILENAME});
        reader.configure();
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileNotReadable_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", UNREADABLE_CONFIG_FILENAME});
        File file = new File(UNREADABLE_CONFIG_FILENAME);
        file.setReadable(false);
        try {
            reader.configure();
        } catch(FileNotFoundException e) {
            // System.out.println("Found unreadable file");
            throw new FileNotFoundException();
        } finally {
            file.setReadable(true);
        }        
    }
    
    @Test (expected = FileNotFoundException.class)
    public void configFileIsDirectory_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", CONFIG_DIRNAME});
        reader.configure();
    }
    
    @Test (expected = IOException.class)
    public void emptyConfigFile_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", EMPTY_CONFIG_FILENAME});
        reader.configure();
    }
    
    @Test (expected = JsonParseException.class)
    public void malFormedJsonConfigFile_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", MALFORMED_CONFIG_FILENAME});
        reader.configure();
    }
    
    @Test (expected = ParseException.class)
    public void invalidOption_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {UNSUPPORTED_OPTION, UNSUPPORTED_OPTION_VALUE});
        reader.configure();  
    }
   
    @Test (expected = ParseException.class)
    public void invalidOptionWithValidOption_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", CONFIG_FILENAME, UNSUPPORTED_OPTION, UNSUPPORTED_OPTION_VALUE});
        reader.configure();
   
    }
    
    @Test (expected = ParseException.class)
    public void invalidOptionWithoutArgument_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {UNSUPPORTED_OPTION});
        reader.configure();     
    }

    
//    Start the test this way.
//    @Test
//    public void sampleTest() {
//        fail("sampleTest not implemented");
//    }
    
}

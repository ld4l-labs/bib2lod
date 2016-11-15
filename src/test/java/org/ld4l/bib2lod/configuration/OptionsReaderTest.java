package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.MissingArgumentException;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Test plan
 * 
 * invalid config file (not well-formed json)
 * empty config file
 * ignore invalid arguments
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
        OptionsReader reader = new OptionsReader(new String[] {"-c", CONFIG_FILENAME});
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

    
//    Start the test this way.
//    @Test
//    public void sampleTest() {
//        fail("sampleTest not implemented");
//    }
    
}

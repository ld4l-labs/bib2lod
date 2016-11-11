package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.apache.commons.cli.MissingArgumentException;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Test plan
 * 
 * config file not specified
 * config file not found
 * invalid config file (not well-formed json)
 * empty config file
 * ignore invalid arguments
 * @author rjy7
 *
 */
public class OptionsReaderTest extends AbstractTestClass {

    @Test (expected = NullPointerException.class)
    public void argsNullThrowsException() {
        new OptionsReader(null);
             
    }

    @Test (expected = IllegalArgumentException.class)
    public void argsIsEmpty() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {});
        reader.configure();
    }
    
    @Test 
    public void providingPathUsingShortFormSucceeds() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"-c", "src/test/resources/config/config.json"});
        JsonNode config = reader.configure();
        assertNotNull(config);
    }
    
    @Test 
    public void providingPathUsingLongFormSucceeds() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config", "src/test/resources/config/config.json"});
        JsonNode config = reader.configure();
        assertNotNull(config);
    }
    
    @Test (expected = MissingArgumentException.class)
    public void providingNoPath_ThrowsException() throws Exception {
        OptionsReader reader = new OptionsReader(new String[] {"--config"});
        reader.configure();
    }
    
//    @Test
//    public void test() {
//        fail("test not implemented");
//    }
}

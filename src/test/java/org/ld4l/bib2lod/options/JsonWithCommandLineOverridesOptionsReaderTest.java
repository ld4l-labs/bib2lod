package org.ld4l.bib2lod.options;

import static org.junit.Assert.assertEquals;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.Test;
import org.ld4l.bib2lod.options.JsonOptionsReader;
import org.ld4l.bib2lod.options.JsonWithCommandLineOverridesOptionsReader;
import org.ld4l.bib2lod.options.OptionsReader;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonWithCommandLineOverridesOptionsReaderTest 
        extends AbstractTestClass{
    
    private static final String TEST_CONFIG_DIR = 
            "src/test/resources/options_reader/";
    
    private static final String LOCAL_NAMESPACE = 
            "http://local.namespace.org/test/";
    
    private static final String VALID_CONFIG_FILENAME = 
            TEST_CONFIG_DIR + "valid_config.json";
    

    @Test 
    public void commandLineOverridesConfigFile() throws Exception { 

        String[] args = new String[] {"-c", VALID_CONFIG_FILENAME, 
                "--local_namespace", LOCAL_NAMESPACE};
      
        OptionsReader reader = 
                new JsonWithCommandLineOverridesOptionsReader(args);
      
        Options options = ((JsonWithCommandLineOverridesOptionsReader) reader)
                .buildOptions();
      
        // Get the commandline values for these options
        CommandLine cmd = ((JsonOptionsReader) reader)
                .parseCommandLineArgs(options, args); 
      
        // Parse the config file
        JsonNode jsonNode = ((JsonOptionsReader) reader).parseConfigFile(cmd);

        // Commandline option values override config file values
        // Only applies to JsonConfigWithCommandLineOverrides
        JsonNode node = ((JsonWithCommandLineOverridesOptionsReader) reader)
                .applyCommandLineOverrides(jsonNode, cmd);
      
        String localNamespace = jsonNode.get("local_namespace").textValue();
        assertEquals(LOCAL_NAMESPACE, localNamespace);
            
  }
     

}

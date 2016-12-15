package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration.RequiredKeyMissingException;
import org.ld4l.bib2lod.configuration.Configuration.RequiredValueEmptyException;
import org.ld4l.bib2lod.configuration.Configuration.RequiredValueNullException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/*
 * Test plan: 

 * 
 * No local namespace in config - exception
 * Local namespace empty - exception
 * Local namespace null - exception
 * Local namespace not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * Local namespace not well-formed URI - exception
 * 
 * No input in config - exception
 * Input value empty - exception
 * Input value null - exception
 * Input not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * Input location doesn't exist - exception
 * Input location a directory but is empty - succeed, does nothing
 * IO exception - exception
 * 
 * No output in config - exception
 * output value empty - exception
 * output value null - exception
 * output not an object - exception
 * ?? Test all possible data types: number, boolean, array, string?
 * 
 * No output location in config - exception
 * Location value empty - exception
 * Location value null - exception
 * Location value not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
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
 * ?? Test all possible data types: number, boolean, array, object?
 * Log value doesn't exist - exception
 * Log value not a directory - exception
 * Log value not writable - exception
 * Any IO exception - exception
 * 
 * No services in config
 * Services empty
 * Services null
 * Services not an object
 * ?? Test all possible data types: number, boolean, array, string?
 * 
 * No UriMinter in config - exception
 * UriMinter empty - exception
 * UriMinter null - exception
 * UriMinter not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * URI minter class doesn't exist - exception
 * Uri minter class doesn't extend AbstractUriMinter - exception
 * Can't create Uri minter instance - exception
 * 
 * No writer in config - exception
 * writer empty - exception
 * writer null - exception
 * writer not a string - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * writer class doesn't exist - exception
 * writer class doesn't extend AbstractWriter - exception
 * Can't create writer instance - exception
 * 
 * TODO later: add same for other services
 * 
 * No converters in config - succeed and do nothing (may only to cleaning, for example)
 * Converters empty - succeed and do nothing (may only to cleaning, for example)
 * converters null - succeed and do nothing (may only to cleaning, for example)
 * Converters not an array - exception
 * ?? Test all possible data types: number, boolean, string, object?
 * converter class doesn't exist - exception
 * converter doesn't extend AbstractConverter - exception
 * Convert can't be instantiated - exception
 * 
 * No reconcilers in config - succeed
 * reconcilers empty - succeed
 * reconcilers null - succeed
 * reconciler not an array - exception
 * ?? Test all possible data types: number, boolean, array, object?
 * reconciler an empty array - succeed and do nothing 
 * 
 */
public class ConfigurationTest extends AbstractTestClass {
    
    private static final String CONFIG_LOCAL_NAMESPACE_MISSING = 
            "src/test/resources/config/local_namespace_missing.json";
    private static final String CONFIG_LOCAL_NAMESPACE_EMPTY = 
            "src/test/resources/config/local_namespace_empty.json";
    private static final String CONFIG_LOCAL_NAMESPACE_NULL = 
            "src/test/resources/config/local_namespace_null.json";
    
    /* 
     * Local namespace tests 
     */
    
    @Test (expected = RequiredKeyMissingException.class)
    public void missingLocalNamespace_ThrowsException() throws Exception {
        new Configuration(new String[] {"-c", CONFIG_LOCAL_NAMESPACE_MISSING});               
    }
    
    @Test (expected = RequiredValueEmptyException.class)
    public void emptyLocalNamespace_ThrowsException() throws Exception {
        new Configuration(new String[] {"-c", CONFIG_LOCAL_NAMESPACE_EMPTY});        
    }
    
    @Test (expected = RequiredValueNullException.class)
    public void nullLocalNamespace_ThrowsException() throws Exception {
        // fail("nullLocalNamespace_ThrowsException not implemented");
        new Configuration(new String[] {"-c", CONFIG_LOCAL_NAMESPACE_NULL});  
    }   
    
    @Test
    public void localNamespaceNotString_ThrowsException() {
        fail("localNamespaceNotString_ThrowsException not implemented");
    }   
  
    
    @Test
    public void localNamespaceMalFormedUri_ThrowsException() {
        fail("localNamespaceNotWellFormedUri_ThrowsException not implemented");
    }   
    
    
    

//  Start the test this way.
//  @Test
//  public void sampleTest() {
//      fail("sampleTest not implemented");
//  }

}

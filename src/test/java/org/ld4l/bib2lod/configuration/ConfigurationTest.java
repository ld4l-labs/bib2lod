package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.options.InvalidTypeException;
import org.ld4l.bib2lod.configuration.options.InvalidValueException;
import org.ld4l.bib2lod.configuration.options.RequiredKeyMissingException;
import org.ld4l.bib2lod.configuration.options.RequiredValueEmptyException;
import org.ld4l.bib2lod.configuration.options.RequiredValueNullException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/*
 * Test plan: 
 *
 * General:
 * Required but missing - exception
 * Required but null - exception
 * Required wrong type - exception
 * Required empty - exception
 * Required malformed or bad in some way (as many as needed) - exception
 * Required and everything OK - succeed
 * * * * 
 * Optional and missing - succeed
 * Optional and null - succeed
 * Optional but wrong type - exception
 * Optional and empty - succeed
 * Optional bad in some way - exception
 * Optional and everything OK - succeed
 * 

 * 
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
    
    private static final String TEST_CONFIG_DIR = 
            "src/test/resources/configuration/";

    private Configuration configureLocalNamespace(String filename) 
            throws Exception {
        return new Configuration(new String[] {"-c", TEST_CONFIG_DIR + 
                "local_namespace/" + filename});
    }
    
    private Configuration configureInput(String filename) throws Exception {
        
        return new Configuration(new String[] {"-c", TEST_CONFIG_DIR + 
                "input/" + filename});
    }
    
    /*
     * Required value
     */
    @Test (expected = RequiredKeyMissingException.class)
    public void requiredKeyMissing_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_missing.json");               
    }   

    /* 
     * Required string value
     */
    
    @Test (expected = RequiredValueNullException.class)
    public void requiredStringValueNull_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_null.json");  
    }   

    @Test (expected = InvalidTypeException.class)
    public void requiredStringValueInvalidType_ThrowsException() throws Exception {                                                  
        configureLocalNamespace("local_namespace_invalid_type.json");        
    }

    @Test (expected = RequiredValueEmptyException.class)
    public void requiredStringValueEmpty_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_empty_string.json");        
    }
    
    @Test 
    public void requiredStringValuePresent_Succeeds() throws Exception {
        fail("requiredStringValuePresent_Succeeds");        
    }
    
    /*
     * Optional value
     */

    @Test 
    public void optionalKeyMissing_Succeeds() throws Exception {
        fail("optionalKeyMissing_Succeeds not implemented");         
    }
    
    /*
     * Optional string value
     */
         
    @Test 
    public void optionalStringValueNull_Succeeds() throws Exception {
        fail("optionalStringValueNull_Succeeds not implemented");         
    }
    
    @Test 
    public void optionalStringValueEmpty_Succeeds() throws Exception {
        fail("optionalStringValueEmpty_Succeeds not implemented");         
    }
    
    @Test 
    public void optionalStringValueInvalidType_ThrowsException() throws Exception {
        fail("optionalStringValueInvalidType_ThrowsException not implemented");         
    }
    
    @Test 
    public void optionalStringValuePresent_Succeeds() throws Exception {
        fail("optionalStringValuePresent_Succeeds not implemented");         
    }  
    
    /* 
     * Required object value
     */

    @Test 
    public void requiredObjectValueNull_ThrowsException() throws Exception {
        fail("requiredObjectValueNull_ThrowsException not implemented");  
    }   

    @Test 
    public void requiredObjectValueInvalidType_ThrowsException() throws Exception {                                                  
        fail("requiredObjectValueInvalidType_ThrowsException not implemented");        
    }

    @Test 
    public void requiredObjectValueEmpty_ThrowsException() throws Exception {
        fail("requiredObjectValueEmpty_ThrowsException not implemented");        
    }

    @Test 
    public void optionalObjectValuePresent_Succeeds() throws Exception {
        fail("optionalObjectValuePresent_Succeeds not implemented");         
    }  
    
    /*
     * Optional object value
     */
    
    @Test 
    public void optionalObjectValueNull_Succeeds() throws Exception {
        fail("optionalObjectValueNull_Succeeds not implemented");         
    }
    
    @Test 
    public void optionalObjectValueEmpty_Succeeds() throws Exception {
        fail("optionalObjectValueEmpty_Succeeds not implemented");         
    }
    
    @Test 
    public void optionalObjectValueInvalidType_ThrowsException() throws Exception {
        fail("optionalObjectValueInvalidType_ThrowsException not implemented");         
    }
    
    
    /* 
     * Local namespace format
     */

    @Test (expected = InvalidValueException.class)
    public void localNamespaceNoFinalSlash_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_no_final_slash.json");              
    }  
    
    @Test (expected = InvalidValueException.class)
    public void localNamespaceMalformedUri_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_malformed_uri.json");                
    }  
    
    @Test 
    public void localNamespaceValidUri_Succeeds() throws Exception {
        Configuration config = 
                configureLocalNamespace("local_namespace_valid.json");
        assertNotNull(config.getLocalNamespace());             
    }
    
    
    /*
     * Location
     */
    
    @Test 
    public void requiredLocationNotFound_ThrowsException() throws Exception {
        fail("requiredLocationNotFound_ThrowsException not implemented");         
    }
    
    /*
     * Input location
     */
    
    @Test 
    public void requiredInputFileNotReadable_ThrowsException() throws Exception {
        fail("requiredInputFileNotReadable_ThrowsException not implemented");         
    }
    
    @Test 
    public void requiredInputDirectoryNotReadable_ThrowsException() throws Exception {
        fail("requiredInputDirectoryNotReadable_ThrowsException not implemented");         
    }

    @Test 
    public void requiredInputFileEmpty_Succeeds() throws Exception {
        fail("requiredInputFileEmpty_Succeeds not implemented");         
    }
    
    @Test 
    public void requiredInputDirectoryEmpty_Succeeds() throws Exception {
        fail("requiredInputDirectoryEmpty_Succeeds not implemented");         
    }
    
    @Test 
    public void requiredInputFileNotEmpty_Succeeds() throws Exception {
        fail("requiredInputFileEmpty_Succeeds not implemented");         
    }
    
    @Test 
    public void requiredInputDirectoryNotEmpty_Succeeds() throws Exception {
        fail("requiredInputDirectoryNotEmpty_Succeeds not implemented");         
    }
    
    
    
    /*
     * Input serialization
     */
    
    @Test
    public void inputSerializationInvalidValue_ThrowsException() throws Exception {
        fail("inputSerializationInvalidValue_ThrowsException not implemented");         
    } 
    
    @Test
    public void inputSerializationValid_Succeeds() throws Exception {
        fail("inputSerializationValid_Succeeds not implemented");         
    }     




//  Start the test this way.
//  @Test
//  public void sampleTest() {
//      fail("sampleTest not implemented");
//  }

}

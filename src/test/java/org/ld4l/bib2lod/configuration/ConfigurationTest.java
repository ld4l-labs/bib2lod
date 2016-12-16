package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration.InvalidTypeException;
import org.ld4l.bib2lod.configuration.Configuration.InvalidValueException;
import org.ld4l.bib2lod.configuration.Configuration.RequiredKeyMissingException;
import org.ld4l.bib2lod.configuration.Configuration.RequiredValueEmptyException;
import org.ld4l.bib2lod.configuration.Configuration.RequiredValueNullException;
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
 * Local namespace missing - RequiredKeyMissingException - DONE
 * Local namespace null - RequiredValueNullException - DONE
 * Local namespace not a string - InvalidTypeException - DONE 
 * Local namespace empty string - RequiredValueEmptyException - DONE
 * Local namespace no final slash - InvalidValueException
 * Local namespace malformed URI - Jena MalformedURIException
 * Local namespace valid URI - succeeds
 * 
 * 
 * Input missing - RequiredKeyMissingException 
 * Input value null - RequiredValueNullException
 * Input not an object - InvalidTypeException
 * Input value empty - RequiredValueEmptyException
 * Input location missing
 * Input location null - RequiredValueNullException
 * Input location not a string - InvalidTypeException
 * Input location empty string - RequiredValueEmptyException
 * Input location doesn't exist - exception (IOException?)
 * Input location not readable - exception 
 * Input location an empty directory - succeeds, will do nothing
 * Input location directory and correct, no serialization) - succeeds
 * Input location empty file - succeeds, will do nothing
 * Input location file and correct (no serialization)
 * Input serialization missing - succeeds
 * Input serialization null - succeeds
 * Input serialization not a string - InvalidTypeException
 * Input serialization empty - succeeds
 * Input serialization is not one of TURTLE, TTL, N-TRIPlES, N-TRIPLE, NT, RDF/XML, N3, JSON-LD, RDF/JSON (values accepted by Jena) - InvalidValueException
 * Input serialization specified but doesn't match serialization of file NO: Put in RdfReaderTest
 * Input location and serialization correct - succeeds
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
    
    private static final String VALID_CONFIG_FILENAME = 
            "src/main/resources/config.json";
    
    /* 
     * Local namespace tests 
     */
    
    private Configuration configureLocalNamespace(String filename) 
            throws Exception {
        return new Configuration(new String[] {"-c", TEST_CONFIG_DIR + 
                "local_namespace/" + filename});
    }
    
    @Test (expected = RequiredKeyMissingException.class)
    public void localNamespaceMissing_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_missing.json");               
    }
    
    @Test (expected = RequiredValueNullException.class)
    public void localNamespaceNull_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_null.json");  
    }   

    @Test (expected = InvalidTypeException.class)
    public void localNamespaceNotString_ThrowsException() throws Exception {                                                  
        configureLocalNamespace("local_namespace_boolean.json");        
    }
    
//    
//    @Test (expected = InvalidTypeException.class)
//    public void localNamespaceNonEmptyObject_ThrowsException() throws Exception {
//        configureLocalNamespace("local_namespace_non_empty_object.json");        
//    }
//
//    @Test (expected = InvalidTypeException.class)
//    public void localNamespaceEmptyArray_ThrowsException() throws Exception {                                                  
//        configureLocalNamespace("local_namespace_empty_array.json");        
//    }
//   
//    @Test (expected = InvalidTypeException.class)
//    public void localNamespaceEmptyObject_ThrowsException() throws Exception {
//        configureLocalNamespace("local_namespace_empty_object.json");        
//    }
//    
//    @Test (expected = InvalidTypeException.class)
//    public void localNamespaceBoolean_ThrowsException() throws Exception {
//        configureLocalNamespace("local_namespace_boolean.json");
//    } 
//    
//    @Test (expected = InvalidTypeException.class)
//    public void localNamespaceNumber_ThrowsException() throws Exception {
//        configureLocalNamespace("local_namespace_number.json");
//    } 

    @Test (expected = RequiredValueEmptyException.class)
    public void localNamespaceEmptyString_ThrowsException() throws Exception {
        configureLocalNamespace("local_namespace_empty_string.json");        
    }

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
     * Input location tests 
     */
    
    private Configuration configureInput(String filename) throws Exception {
            
        return new Configuration(new String[] {"-c", TEST_CONFIG_DIR + 
                "input/" + filename});
    }
    
    @Test //(expected = RequiredKeyMissingException.class)
    public void inputMissing_ThrowsException() throws Exception {
        fail("inputMissing_ThrowsException not implemented.");              
    }
    
    @Test //(expected = RequiredValueNullException.class)
    public void inputNull_ThrowsException() throws Exception {
        fail("inputNull_ThrowsException not implemented.");  
    }   
   
    @Test // (expected = InvalidTypeException.class)
    public void inputNotObject_ThrowsException() throws Exception {                                                  
        fail("inputNotObject_ThrowsException not implemented");      
    }
    
    @Test // (expected = )
    public void inputEmptyObject_ThrowsException() throws Exception {
        fail("inputEmptyObject_ThrowsException not implemented");
    }
   
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationMissing_ThrowsException() throws Exception {
        fail("inputLocationMissing_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationNull_ThrowsException() throws Exception {
        fail("inputLocationNull_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationNotString_ThrowsException() throws Exception {
        fail("inputLocationNotString_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationEmptyString_ThrowsException() throws Exception {
        fail("inputLocationEmptyString_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationNotFound_ThrowsException() throws Exception {
        fail("inputLocationNotFound_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputLocationNotReadable_ThrowsException() throws Exception {
        fail("inputLocationNotReadable_ThrowsException not implemented");         
    }
    
    @Test 
    public void inputLocationEmptyDirectory_Succeeds() throws Exception {
        fail("inputLocationEmptyDirectory_Succeeds not implemented");         
    }
    
    @Test 
    public void inputLocationNonEmptyDirectory_Succeeds() throws Exception {
        fail("inputLocationDirectory_Succeeds not implemented");         
    }
    
    @Test 
    public void inputLocationEmptyFile_Succeeds() throws Exception {
        fail("inputLocationEmptyFile_Succeeds not implemented");         
    }
    
    @Test 
    public void inputLocationNonEmptyFile_Succeeds() throws Exception {
        fail("inputLocationNonEmptyFile_Succeeds not implemented");         
    }
    
    @Test 
    public void inputSerializationMissing_Succeeds() throws Exception {
        fail("inputSerializationMissing_Succeeds not implemented");         
    }
    
    @Test 
    public void inputSerializationNull_Succeeds() throws Exception {
        fail("inputSerializationNull_Succeeds not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputSerializationNotString_ThrowsException() throws Exception {
        fail("inputSerializationEmpty_ThrowsException not implemented");         
    }
    
    @Test //(expected = RequiredValueEmptyException.class)
    public void inputSerializationEmptyString_Succeeds() throws Exception {
        fail("inputSerializationEmptyString_Succeeds not implemented");         
    }
    
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

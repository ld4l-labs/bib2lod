/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.MockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ObjectNode;

/*                
 * Test plan
 * 
 * Input source 
 *    - doesn't exist - Exception - for now - until we handle other types of input
 *    - not readable - Exception
 *    - empty directory - succeed
 *    - a readable directory with no readable files - succeed
 *    - a readable directory - succeeds
 *    - a readable file - succeeds

 *    
 * Input format - Postpone. Will just hard-code correct value for now
 *    - doesn't exist: succeeds - base on file contents
 *    - invalid value (i.e not Ntriples etc) - InvalidValueException
 *    - doesn't match file format (can this be tested)? - exception
 *    - serialization doesn't match file extension - exception
 *    - valid - succeeds
 *    - any IO exception - exception
 * 
 * Output destination - Postpone. Will just assume valid for now.
 *    - not a directory
 *    - doesn't exist and can't create - Exception
 *    - not readable - Exception
 *    - exists and readable - success
 *    - doesn't exist but can create - success
 *    - valid - success
 * 
 * Output format - Postpone. Will just assume valid for now
 *     - invalid - exception
 *     - valid - succeeds            
 * 
 * Log directory - Postpone. Don't use or assume valid for now
 *     - not a directory - exception
 *     - doesn't exist and can't create - exception
 *     - doesn't exist but can create - succeed and create
 *     - not writable - exception
 *     - any IO exception - exception
 *     - valid - succeeds
 * 
 * Classes not found, can't instantiate, or don't extend base class - not done 
 * here, do in tests for those classes (testing instantiation methods)
 * 
 */


/**
 * Tests class BaseConfiguration.
 */
public class BaseConfigurationTest extends AbstractTestClass {

    private MockBib2LodObjectFactory factory;
    private Configuration config;
    private ObjectNode optionsNode;

    @Before
    public void setupForSuccess() {
        optionsNode = jsonObject();
        optionsNode.set("input",
                jsonObject() 
                        .put("source", 
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml"));
        optionsNode.set("output",
                jsonObject()
                        .put("destination",
                                "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/")
                        .put("format", "ntriples"));
        optionsNode.put("local_namespace", "http://data.ld4l.org/cornell/");
        optionsNode.put("uri_minter", "org.ld4l.bib2lod.uris.RandomUriGetter");                        
        optionsNode.put("writer", "org.ld4l.bib2lod.io.SimpleRdfWriter");
        optionsNode.set("converters", jsonArray().add(
                "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlToLd4lRdf"));
        optionsNode.set("reconcilers", jsonArray());

        factory = new MockBib2LodObjectFactory();
        factory.setOptionsReader(new MockOptionsReader(optionsNode));
    }
    
    /*
     * Optional value missing/null/empty/invalid type/invalid value/valid value   
     * TODO Not sure if these are needed, or if we want tests for all the
     * specific values.                           
     */
    @Test
    @Ignore
    public void optionalValueMissing_Succeeds() {
        fail("optionalValueMissing_Succeeds not yet implemented.");
    }
    
    @Test     
    @Ignore
    public void optionalValueNull_Succeeds() {
        fail("optionalValueNull_Succeeds not yet implemented.");
    }
    
    @Test
    @Ignore
    public void optionalValueEmpty_Succeeds() {
        fail("optionalValueEmpty_Succeeds not yet implemented.");
    }
    
    @Test
    @Ignore
    public void optionalValueInvalidType_ThrowsException() {
        fail("optionalValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void optionalValueInvalid_ThrowsException() {
        fail("optionalValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void optionalArrayEmpty_Succeeds() {
        fail("optionalArrayEmpty_Succeeds not yet implemented");
    }
    
    @Test
    @Ignore
    public void optionalObjectEmpty_Succeeds() {
        fail("optionalObjectEmpty_Succeeds not yet implemented");
    }
    
    @Test
    @Ignore
    public void optionalValueValid_Succeeds() {
        fail("optionalValueValid_Succeeds not yet implemented");
    }
    
    /*
     * Required value missing/null/empty/invalid type/invalid value/valid value
     */
    @Test
    @Ignore
    public void requiredValueMissing_ThrowsException() {
        fail("requiredValueMissing_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void requiredValueNull_ThrowsException() {
        fail("requiredValueNull_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void requiredValueEmpty_ThrowsException() {
        fail("requiredValueEmpty_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void requiredValueInvalidType_ThrowsException() {
        fail("requiredValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void requiredValueInvalid_ThrowsException() {
        fail("requiredValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    @Ignore
    public void requiredValueValid_Succeeds() {
        fail("requiredValueValid_Succeeds not yet implemented");
    }
    

    
    /*
     * Local namespace tests 
     */
    
    @Test
    @Ignore
    public void localNamespaceMissing_ThrowsException() {
        fail("localNamespaceMissing_ThrowsException not yet implemented.");
//        setNamespace(JSON_REMOVE);
//        instantiateAndExpectException(RequiredKeyMissingException.class);
    }

    @Test
    @Ignore
    public void localNamespaceNull_ThrowsException() {
        fail("localNamespaceNull_ThrowsException not yet implemented");
//        setNamespace(null);
//        instantiateAndExpectException(RequiredValueNullException.class);
    }

    @Test
    @Ignore
    public void localNamespaceEmptyString_ThrowsException() {
        fail("localNamespaceEmptyString_ThrowsException not yet implemented");
//        setNamespace("");
//        instantiateAndExpectException(RequiredValueEmptyException.class);
    }

    @Test
    @Ignore
    public void localNamespaceEmptyArray_ThrowsException() {
        fail("localNamespaceEmptyArray_ThrowsException not yet implemented");
//        setNamespace(jsonArray());
//        instantiateAndExpectException(InvalidTypeException.class);
    }

    @Test
    @Ignore
    public void localNamespaceEmptyObject_ThrowsException() {
        fail("localNamespaceEmptyObject_ThrowsException not yet implemented");
//        setNamespace(jsonObject());
//        instantiateAndExpectException(InvalidTypeException.class);
    }

    @Test
    @Ignore
    public void localNamespaceInvalidType_ThrowsException() {
        fail("localNamespaceInvalidType_ThrowsException not yet implemented");
//        optionsNode.put("local_namespace", true);
//        instantiateAndExpectException(InvalidTypeException.class);
    }
    
    @Test
    @Ignore
    public void localNamespaceMalformedUri_ThrowsException() {
        fail("localNamespaceMalformedUri_ThrowsException not yet implemented");
//        setNamespace("http://mal formed uri.org");
//        instantiateAndExpectException(IRIImplException.class);
    }
    
    @Test
    @Ignore
    public void localNamespaceNoFinalSlash_ThrowsException() {
        fail("localNamespaceNoFinalSlash_ThrowsException not yet implemented");
//        setNamespace("http://data.ld4l.org/cornell");
//        instantiateAndExpectException(InvalidValueException.class);
    }
    
    @Test 
    @Ignore
    public void localNamespaceIsWebPage_ThrowsException() {
//        setNamespace("http://data.ld4l.org/cornell/index.html");
//        instantiateAndExpectException(InvalidValueException.class);
    }

    @Test
    @Ignore
    public void localNamespaceValidUri_Succeeds() throws Exception {
        fail("localNamespaceValidUri_Succeeds not yet implemented");
//        config = Configuration.instance(new String[0]);
//        assertEquals("http://data.ld4l.org/cornell/", 
//                config.getLocalNamespace());
    }
    
    /*
     * Input source tests
     */

    @Test 
    @Ignore
    public void inputSourceDoesntExist_ThrowsException() {
        fail("inputSourceDoesntExist_ThrowsException not yet implemented");
//        setInputSource("non_existent_source");
//        instantiateAndExpectException(InvalidInputSourceException.class);     
    }
    
    @Test 
    @Ignore
    public void inputSourceNotReadable_ThrowsException() {
        fail("inputSourceNotReadable_ThrowsException not yet implemented");
    }
    
    @Test
    @Ignore
    public void inputDirectoryEmpty_Succeeds() {
        fail("inputDirectoryEmpty_Succeeds not yet implemented");
    }
    
    @Test
    @Ignore
    public void allInputFilesUnreadable_Succeeds() {
        fail("inputDirectoryWithNoReadableFiles_Succeeds not yet implemented");
    }
    
    @Test 
    @Ignore
    public void readableInputFile_Succeeds() {
       fail("inputDirectoryWithNoReadableFiles_Succeeds not yet implemented");
    }

    
    /*
     * Helper methods
     */
    
    private void setNamespace(Object newValue) {
        setFieldValue(optionsNode, "local_namespace", newValue);
    }
    
    /* Will use with input source tests */
    private void setInputSource(Object newValue) {
        ObjectNode source = jsonObject();
        setFieldValue(source, "source", newValue);
        setFieldValue(optionsNode, "input", source);
    }
    
    private void instantiateAndExpectException(
            Class<? extends Exception> expected) {
        try {
            // Or just instantiate the MockConfiguration instance directly for
            // testing BaseConfiguration methods? 
            // config = new MockConfiguration(new String[0]);
            config = Configuration.instance(new String[0]);
            fail("Expected exception '" + expected.getSimpleName());
        } catch (Exception e) {
            if (!e.getClass().equals(expected)) {
                fail("Expected exception '" + expected.getSimpleName()
                        + ", but exception was '" + e.getClass().getSimpleName()
                        + "'");
            }
        }
    }
}

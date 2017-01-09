/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.jena.iri.impl.IRIImplException;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.BaseConfiguration.InvalidValueException;
import org.ld4l.bib2lod.test.AbstractTestClass;

/*                
 * Test plan
 * 
 * Input source - Postpone. Will just assume correct value for now
 *    - doesn't exist - Exception
 *    - not readable - Exception
 *    - empty directory - succeeds
 *    - a directory - succeeds
 *    - a file - succeeds
 *    - empty file - succeeds
 *    - valid non-empty file - succeeds
 *    - valid non-empty directory - succeeds
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
 * Tests of org.ld4l.bib2lod.configuration.BaseConfiguration
 */
public class BaseConfigurationTest extends AbstractTestClass {


    private final String MALFORMED_URI = "this is not a valid uri";
    private final String WEB_PAGE_URL = 
            "http://data.ld4l.org/cornell/index.html";
    private final String LOCAL_NAMESPACE_NO_FINAL_SLASH = 
            "http://data.ld4l.org/cornell";
    private final String VALID_LOCAL_NAMESPACE = 
            "http://data.ld4l.org/cornell/";
           
    /*
     * Optional value missing/null/empty/invalid type/invalid value/valid value                              
     */
    @Test
    public void optionalValueMissing_Succeeds() {
//        fail("optionalValueMissing_Succeeds not yet implemented.");
    }
    
    @Test                              
    public void optionalValueNull_Succeeds() {
//        fail("optionalValueNull_Succeeds not yet implemented.");
    }
    
    @Test
    public void optionalValueEmpty_Succeeds() {
//        fail("optionalValueEmpty_Succeeds not yet implemented.");
    }
    
    @Test
    public void optionalValueInvalidType_ThrowsException() {
//        fail("optionalValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    public void optionalValueInvalid_ThrowsException() {
//        fail("optionalValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    public void optionalArrayEmpty_Succeeds() {
//        fail("optionalArrayEmpty_Succeeds not yet implemented");
    }
    
    @Test
    public void optionalObjectEmpty_Succeeds() {
//        fail("optionalObjectEmpty_Succeeds not yet implemented");
    }
    
    @Test
    public void optionalValueValid_Succeeds() {
//        fail("optionalValueValid_Succeeds not yet implemented");
    }
    
    /*
     * Required value missing/null/empty/invalid type/invalid value/valid value
     */
    @Test
    public void requiredValueMissing_ThrowsException() {
//        fail("requiredValueMissing_ThrowsException not yet implemented.");
    }
    
    @Test
    public void requiredValueNull_ThrowsException() {
//        fail("requiredValueNull_ThrowsException not yet implemented.");
    }
    
    @Test
    public void requiredValueEmpty_ThrowsException() {
//        fail("requiredValueEmpty_ThrowsException not yet implemented.");
    }
    
    @Test
    public void requiredValueInvalidType_ThrowsException() {
//        fail("requiredValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    public void requiredValueInvalid_ThrowsException() {
//        fail("requiredValueInvalidType_ThrowsException not yet implemented.");
    }
    
    @Test
    public void requiredValueValid_Succeeds() {
//        fail("requiredValueValid_Succeeds not yet implemented");
    }
    


    /*
     * Local namespace tests - alternate implementations
     */
    /*
    @Test (expected = IRIImplException.class)
    public void localNamespaceMalformedUri_ThrowsException() throws Exception {
        BaseConfiguration.isValidLocalNamespace(MALFORMED_URI);
    }
    
    @Test (expected = InvalidValueException.class)
    public void localNamespaceWebPageUrl_ThrowsException() throws Exception {
        BaseConfiguration.isValidLocalNamespace(WEB_PAGE_URL);
    }
                                                                 
    @Test (expected = InvalidValueException.class)
    public void localNamespaceNoFinalSlash_ThrowsException() {
        BaseConfiguration.isValidLocalNamespace(LOCAL_NAMESPACE_NO_FINAL_SLASH);
    }
    */
    
    /*
     * Local namespace tests 
     */
    
    @Test 
    public void localNamespaceMalformedUri_ThrowsException() {
        validateLocalNamespaceAndExpectException(
                MALFORMED_URI, IRIImplException.class);
    }
    
    @Test
    public void localNamespaceWebPageUrl_ThrowsException() {
        validateLocalNamespaceAndExpectException(
                WEB_PAGE_URL, InvalidValueException.class);
    }
                                                                 
    @Test 
    public void localNamespaceNoFinalSlash_ThrowsException() {
        validateLocalNamespaceAndExpectException(
                LOCAL_NAMESPACE_NO_FINAL_SLASH, InvalidValueException.class);
    }
    
    @Test
    public void localNamespaceValidUri_Succeeds() {
        assertTrue(BaseConfiguration.isValidLocalNamespace(
                VALID_LOCAL_NAMESPACE));       
    }

    
    /*
     * Helper methods
     */
    private void validateLocalNamespaceAndExpectException(String localNamespace,
            Class<? extends Exception> expected) {
        try {
            BaseConfiguration.isValidLocalNamespace(localNamespace);
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

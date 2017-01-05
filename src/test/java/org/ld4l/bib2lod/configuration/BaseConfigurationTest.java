/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.jena.iri.impl.IRIImplException;
import org.junit.Test;
import org.ld4l.bib2lod.test.AbstractTestClass;
import org.ld4l.bib2lod.configuration.BaseConfiguration.InvalidValueException;


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
     * Local namespace tests
     */
    
    /* Alternate implementations
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

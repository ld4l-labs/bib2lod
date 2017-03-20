/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests abstract class BaseConverter.
 */
/*
 * test plan:
 * - invalid record is ignored
 * - invalid input is ignored (or in SimpleManagerTest?)
 */
public class BaseConverterTest extends AbstractTestClass {

    private Parser parser;
    private MockConverter converter;

    private static final String OPEN_ROOT_ELEMENT = "<collection>";
    private static final String CLOSE_ROOT_ELEMENT = "</collection>";

    private static final String VALID_RECORD_1 = 
            "<record><child>valid record 1</child></record>"; 
    
    private static final String VALID_RECORD_2 = 
            "<record><child>valid record 2</child></record>"; 
    
    private static final String VALID_RECORD_3 = 
            "<record><child>valid record 3</child></record>"; 
     
    private static final String RECORDS = OPEN_ROOT_ELEMENT + VALID_RECORD_1 +
               VALID_RECORD_2 + VALID_RECORD_3 + CLOSE_ROOT_ELEMENT;
     
//    private static final String INVALID_RECORD = "<record>invalid record</record>"; 
//    
//    private static final String RECORDS = OPEN_ROOT_ELEMENT + VALID_RECORD_1 +
//                 INVALID_RECORD + VALID_RECORD_2 + CLOSE_ROOT_ELEMENT;

    @Before
    public void setup() {
        converter = new MockConverter();  
    }        
   
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    // NOTE: Ignoring an invalid record during parsing is tested by the 
    // parser tests.

    @Ignore
    @Test
    public void entityBuilderException_IgnoresRecord() throws Exception {
        // TODO Test what happens when buildEntities() throws an error -
        // record should be skipped.
        fail("entityBuilderException_IgnoresRecord not yet implemented.");
    }

    @Ignore
    @Test
    public void emptyEntityList_Succeeds() throws Exception {
        // Test what happens when no entities are returned from buildEntities()
        fail("emptyEntityList_Succeeds not yet implemented.");
    }
    
    @Ignore
    @Test
    public void modelBuilderException_IgnoresRecord() {
        // TODO Test what happens when buildModels() throws an error -
        // record should be skipped.
        fail("modelBuilderException_IgnoresRecord not yet implemented.");
    }
    
    @Ignore
    @Test
    public void emptyModel_Succeeds() {
        // TODO Test what happens if an empty Model is returned from buildModel()
        fail("emptyModel_Succeeds not yet implemented.");
    }
}

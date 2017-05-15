/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests abstract class BaseConverter.
 */
/*
 * test plan:
 * - invalid record is ignored
 * - invalid input is ignored (or in SimpleManagerTest?)
 */
public class BaseConverterTest extends AbstractTestClass {
 
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------
    
    public static class MockConverter extends BaseConverter {

        @Override
        protected Entity buildEntity(Record record)
                throws EntityBuilderException {
            throw new RuntimeException("Method not yet implemented.");
        }
    }
    
    public static class MockXmlParser extends XmlParser {

        private static final String RECORD_TAG_NAME = "record";   
        private static final Class<?> RECORD_CLASS = MockXmlRecord.class;

        @Override
        protected String getRecordTagName() {
            return RECORD_TAG_NAME;
        }

        @Override
        protected Class<?> getRecordClass() {
            return RECORD_CLASS;
        }
    }

    public static class MockXmlRecord implements Record { // extends BaseXmlRecord {
        
        private String textValue;

        public MockXmlRecord(Element record) {
            //super(record);
            textValue = record.getFirstChild().getTextContent();    
        }

        @Override
        public boolean isValid() {
            if (! textValue.isEmpty()) {
                return false;
            }
            return true;
        }
    }
    
    public static class MockEntityBuilder extends BaseEntityBuilder {
        
        private Record record;

        public MockEntityBuilder(Record record) {
            this.record = record;
        }

        @Override
        public Entity build(BuildParams params) throws EntityBuilderException {
            // TODO Auto-generated method stub
            return null;
        }
    }

    public static class MockInputDescriptor implements InputDescriptor {
        
        private final String inputString;
        
        public MockInputDescriptor(String input) {
            this.inputString = input;
        }

        @Override
        public InputMetadata getMetadata() {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized InputStream getInputStream() throws IOException {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized void close() throws InputServiceException, IOException {
            throw new RuntimeException("Method not implemented.");          
        }
    }
    

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
    public void setUp() {
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

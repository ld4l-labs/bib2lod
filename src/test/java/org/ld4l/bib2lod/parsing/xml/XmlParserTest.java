/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.Parser.ParserException;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Tests class XmlParser.
 */
public class XmlParserTest extends AbstractTestClass {
    
    // ---------------------------------------------------------------------
    // Mocking infrastructure
    // ---------------------------------------------------------------------
    
    public static class MockInputDescriptor implements InputDescriptor {
        
        private final String inputString;
        private InputStream inputStream;
        
        public MockInputDescriptor(String input) {
            this.inputString = input;
        }

        @Override
        public InputMetadata getMetadata() {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized InputStream getInputStream() throws IOException {
            if (inputStream == null) {
                inputStream = IOUtils.toInputStream(inputString, "UTF-8");
            }
            return inputStream;
        }

        @Override
        public synchronized void close() throws InputServiceException, IOException {
            throw new RuntimeException("Method not implemented.");         
        }
    }
    
    /*
     * A concrete implementation needed to test abstract class XmlParser.
     */
    public static class MockXmlParser extends XmlParser {

        private static final String RECORD_TAG_NAME = "record";

        @Override
        protected String getRecordTagName() {
            return RECORD_TAG_NAME;
        }

        protected XmlRecord createRecord(Element recordElement)
                throws RecordException {
            return new MockXmlRecord(recordElement);

        }
    }

    public static class MockXmlRecord implements XmlRecord { 
        
        private List<MockXmlRecordElement> children;

        public MockXmlRecord(Element record) throws RecordException {
            children = buildChildren(record);
            isValid();
        }
        
        private List<MockXmlRecordElement> buildChildren(Element record) {
            List<MockXmlRecordElement> children = 
                    new ArrayList<MockXmlRecordElement>();
            NodeList nodes = record.getElementsByTagName("child");
            for (int i = 0; i < nodes.getLength(); i++) {
                children.add(
                        new MockXmlRecordElement ((Element) nodes.item(i)));
            }
            return children;
        }

        private void isValid() throws RecordException {      
            if (children.isEmpty()) {
                throw new RecordException("No children");
            }
        }
    }

    public static class MockXmlRecordElement extends BaseXmlElement {

        public MockXmlRecordElement(Element element) {
            super(element);
        }

    }    
    
    
    private static final String ROOT_ELEMENT_OPEN = "<collection>";
    private static final String ROOT_ELEMENT_CLOSE = "</collection>";

    private static final String VALID_RECORD = 
            "<record><child>valid record</child></record>";   
     
    private static final String INVALID_RECORD = "<record>invalid record</record>"; 
    
    private static final String RECORDS = ROOT_ELEMENT_OPEN + VALID_RECORD +
                 INVALID_RECORD + ROOT_ELEMENT_CLOSE;
    
    private static final String NO_RECORDS = ROOT_ELEMENT_OPEN + ROOT_ELEMENT_CLOSE;
 
    private Parser parser;

    @Before
    public void setUp() {
        parser = new MockXmlParser(); 
    }  
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void invalidRecord_Ignored() throws Exception {
        List<Record> records = getRecords(RECORDS);
        Assert.assertEquals(1, records.size());       
    }
    
    @Test
    public void noRecords_Succeeds() throws Exception {
        List<Record> records = getRecords(NO_RECORDS);
        Assert.assertTrue(records.isEmpty());     
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private List<Record> getRecords(String input) throws ParserException {
        InputDescriptor descriptor = new MockInputDescriptor(input);
        return parser.parse(descriptor);
    }
 
}

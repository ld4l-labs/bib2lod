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
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.BaseXmlElement;
import org.ld4l.bib2lod.record.xml.XmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


/**
 * Tests class XmlParser.
 */
public class XmlParserTest extends AbstractTestClass {
    
    
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------
    
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

    public static class MockXmlRecord implements XmlRecord { 
        
        private List<MockXmlRecordElement> children;

        public MockXmlRecord(Element record) {
            children = buildChildren(record);
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

        @Override
        public boolean isValid() {      
            if (children.isEmpty()) {
                return false;
            }
            return true;
        }
    }
    

    public static class MockXmlRecordElement extends BaseXmlElement {

        public MockXmlRecordElement(Element element) {
            super(element);
        }

        @Override
        public boolean isValid() {
            // This suffices for current tests
            return true;
        }
    }    
    
    private Parser parser;
    
    private static final String OPEN_ROOT_ELEMENT = "<collection>";
    private static final String CLOSE_ROOT_ELEMENT = "</collection>";

    private static final String VALID_RECORD = 
            "<record><child>valid record</child></record>";   
     
    private static final String INVALID_RECORD = "<record>invalid record</record>"; 
    
    private static final String RECORDS = OPEN_ROOT_ELEMENT + VALID_RECORD +
                 INVALID_RECORD + CLOSE_ROOT_ELEMENT;

    @Before
    public void setup() {
        parser = Parser.instance(MockXmlParser.class);
    }        

    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void invalidRecord_Ignored() throws Exception {
        InputDescriptor descriptor = new MockInputDescriptor(RECORDS);
        List<Record> records = parser.parse(descriptor);
        Assert.assertEquals(1,  records.size());       
    }
    
}

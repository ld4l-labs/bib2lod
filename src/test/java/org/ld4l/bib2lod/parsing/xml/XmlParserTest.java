/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
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
    
    /*
     * A concrete implementation needed to test abstract class XmlParser.
     */
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
    
    
    private static final String ROOT_ELEMENT_OPEN = "<collection>";
    private static final String ROOT_ELEMENT_CLOSE = "</collection>";

    private static final String VALID_RECORD = 
            "<record><child>valid record</child></record>";   
     
    private static final String INVALID_RECORD = "<record>invalid record</record>"; 
    
    private static final String RECORDS = ROOT_ELEMENT_OPEN + VALID_RECORD +
                 INVALID_RECORD + ROOT_ELEMENT_CLOSE;
    
    private static final String NO_RECORDS = ROOT_ELEMENT_OPEN + ROOT_ELEMENT_CLOSE;
 
    private static BaseMockBib2LodObjectFactory factory;
    private Parser parser;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();     
    }
    

    @Before
    public void setUp() {
        //factory.addInstance(Converter.class, new MockConverter());
        factory.addInstance(Parser.class, new MockXmlParser());
        parser = Parser.instance();
    }  
    
    @After
    public void tearDown() {
        factory.unsetInstances();
    }
    
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Ignore
    @Test
    public void invalidRecord_Ignored() throws Exception {
        InputDescriptor descriptor = new MockInputDescriptor(RECORDS);
        List<Record> records = parser.parse(descriptor);
        Assert.assertEquals(1, records.size());       
    }
    
    @Ignore
    @Test
    public void noRecords_Succeeds() throws Exception {
        InputDescriptor descriptor = new MockInputDescriptor(NO_RECORDS);
        List<Record> records = parser.parse(descriptor);
        Assert.assertTrue(records.isEmpty());     
    }
 
}

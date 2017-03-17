package org.ld4l.bib2lod.parsing.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.record.Record;

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

import org.ld4l.bib2lod.testing.AbstractTestClass;



/**
 * Tests class XmlParser.
 */
/*
 * test plan:
 * - test that an invalid record is ignored
 * - when reading in xml document, skip document or part of document that causes 
 * an error but do not terminate
 * 
 * todo:
 * - remove unused stuff from Configuration
 * - don't use factory? Should it be used, or removed?
 * - add package parsing.xml - move all xml parsers into it
 */
public class XmlParserTest extends AbstractTestClass {
    
    private Configuration config;
    private Parser parser;
    
    private static final String OPEN_ROOT_ELEMENT = "<collection>";
    private static final String CLOSE_ROOT_ELEMENT = "</collection>";

    private static final String VALID_RECORD = 
            "<record><child>valid</child></record>";   
     
    private static final String INVALID_RECORD = "<record>invalid</record>"; 
    
    private static final String RECORDS = OPEN_ROOT_ELEMENT + VALID_RECORD +
                 INVALID_RECORD + CLOSE_ROOT_ELEMENT;

    @Before
    public void setUp() {
        
        config = new MockConfiguration(null);
        parser = Parser.instance(config, MockXmlParser.class);

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
    
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
}

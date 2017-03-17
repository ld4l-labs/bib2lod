/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.record.Record;

import org.ld4l.bib2lod.testing.AbstractTestClass;


/**
 * Tests class XmlParser.
 */
public class XmlParserTest extends AbstractTestClass {
    
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
        parser = Parser.instance(null, MockXmlParser.class);
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

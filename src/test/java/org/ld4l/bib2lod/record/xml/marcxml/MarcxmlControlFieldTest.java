/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlControlField.
 */
public class MarcxmlControlFieldTest extends AbstractTestClass {
 
    private static final String NO_CONTROL_NUMBER = 
            "<controlfield>102063</controlfield>";
    
    private static final String EMPTY_CONTROL_NUMBER = 
            "<controlfield tag=''>102063</controlfield>";
    
    private static final String BLANK_CONTROL_NUMBER = 
            "<controlfield tag=' '>102063</controlfield>";
    
    private static final String INVALID_CONTROL_NUMBER= 
            "<controlfield tag='1234'>102063</controlfield>";   
    
    private static final String NO_VALUE = 
            "<controlfield tag='001'></controlfield>";

    private static final String NO_TEXT_VALUE = 
            "<controlfield tag='001'><child>test</child></controlfield>";

    private static final String VALID_CONTROL_FIELD = 
            "<controlfield tag='001'>102063</controlfield>";
    
 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void noControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is empty");
        buildControlFieldFromString(NO_CONTROL_NUMBER);
    }
    
    @Test
    public void emptyControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is empty");
        buildControlFieldFromString(EMPTY_CONTROL_NUMBER);
    }
    
    @Test
    public void blankControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not three characters");
        buildControlFieldFromString(BLANK_CONTROL_NUMBER);
    }
    
    @Test
    public void invalidControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "three characters");
        buildControlFieldFromString(INVALID_CONTROL_NUMBER);
    }
    
    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildControlFieldFromString(NO_VALUE);
    }

    @Test
    public void noTextValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildControlFieldFromString(NO_TEXT_VALUE);
    }

    @Test
    public void validControlField_Valid() throws Exception {
        // No exception
        buildControlFieldFromString(VALID_CONTROL_FIELD);
    }
        
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlControlField buildControlFieldFromString(String s) 
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlControlField(element); 
    }
}

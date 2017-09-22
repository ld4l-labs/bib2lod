/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
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
    
    private static final String INVALID_CONTROL_NUMBER = 
            "<controlfield tag='1234'>102063</controlfield>";   
    
    private static final String _008_INVALID_LENGTH = 
            "<controlfield tag='008'>102063</controlfield>";  
    
    private static final String NO_VALUE = 
            "<controlfield tag='001'></controlfield>";

    private static final String NO_TEXT_VALUE = 
            "<controlfield tag='001'><child>test</child></controlfield>";

    private static final String VALID_CONTROL_FIELD = 
            "<controlfield tag='001'>102063</controlfield>";
    
 
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test
    public void noControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(NO_CONTROL_NUMBER);
    }
    
    @Test
    public void emptyControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(EMPTY_CONTROL_NUMBER);
    }
    
    @Test
    public void blankControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(BLANK_CONTROL_NUMBER);
    }
    
    @Test
    public void invalidControlNumber_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, 
                "Control number is not between 1 and 9");
        buildFromString(INVALID_CONTROL_NUMBER);
    }
    
    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildFromString(NO_VALUE);
    }

    @Test
    public void noTextValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildFromString(NO_TEXT_VALUE);
    }
    
    @Test
    public void _008InvalidLength_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, 
                "does not contain exactly 40 characters.");
        buildFromString(_008_INVALID_LENGTH);
    }

    @Test
    public void validControlField_Valid() throws Exception {
        // No exception
        buildFromString(VALID_CONTROL_FIELD);
    }
        
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MarcxmlControlField buildFromString(String s) 
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlControlField(element); 
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlSubfield.
 */
public class MarcxmlSubfieldTest extends AbstractTestClass {
   
    private static final String NO_CODE = 
            "<subfield>(CStRLIN)NYCX86B63464</subfield>";
    
    private static final String EMPTY_CODE =
            "<subfield code=''>(CStRLIN)NYCX86B63464</subfield>";   
    
    private static final String BLANK_CODE =
            "<subfield code=' '>(CStRLIN)NYCX86B63464</subfield>";    
    
    private static final String NO_VALUE = 
            "<subfield code='a'></subfield>";
    
    private static final String NO_TEXT_VALUE = 
            "<subfield code='a'><child>test</child></subfield>";
    
    private static final String VALID_SUBFIELD = 
            "<subfield code='a'>(CStRLIN)NYCX86B63464</subfield>";  
    

    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void noCode_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "code cannot be empty");
        buildSubfieldFromString(NO_CODE);
    }
    
    @Test
    public void emptyCode_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "code cannot be empty");
        buildSubfieldFromString(EMPTY_CODE);
    }
    
    @Test
    public void blankCode_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "is blank");
        buildSubfieldFromString(BLANK_CODE);
    }

    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "value is null");
        buildSubfieldFromString(NO_VALUE);
    }

    @Test
    public void noTextValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "value is null");
        buildSubfieldFromString(NO_TEXT_VALUE);
    }

    @Test
    public void validSubfield_Succeeds() throws Exception {
        // No exception
        buildSubfieldFromString(VALID_SUBFIELD);
    }    

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MarcxmlSubfield buildSubfieldFromString(String s) 
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlSubfield(element);
    }
}

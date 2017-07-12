/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlDataField.
 */
public class MarcxmlDataFieldTest extends AbstractTestClass {
 
    private static final String NO_TAG = 
            "<datafield ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String EMPTY_TAG = 
            "<datafield tag='' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String BLANK_TAG = 
            "<datafield tag=' ' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String INVALID_TAG = 
            "<datafield tag='1234' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String NO_VALUE = 
            "<datafield tag='245' ind1='0' ind2='0'></datafield>";
    
    private static final String NON_SUBFIELD_CHILD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                 "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
                 "<child>test</child>" +
             "</datafield>";

    private static final String INVALID_SUBFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String INVALID_FIELD_245 = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='c'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String VALID_DATAFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void noTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildDataFieldFromString(NO_TAG);
    }

    @Test
    public void emptyTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildDataFieldFromString(EMPTY_TAG);
    }
    
    @Test
    public void blankTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "not an integer");
        buildDataFieldFromString(BLANK_TAG);
    }
    
    @Test
    public void invalidTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, 
                "value is not between 1 and 999");
        buildDataFieldFromString(INVALID_TAG);
    }
    
    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "no subfields");
        buildDataFieldFromString(NO_VALUE);
    }

    @Test
    public void nonSubfieldChild_Succeeds() throws Exception {
        // No exception
        buildDataFieldFromString(NON_SUBFIELD_CHILD);
    }

    @Test
    public void invalidSubfield_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "code cannot be empty");
        buildDataFieldFromString(INVALID_SUBFIELD);
    }
    
    @Test
    public void invalidField245_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "required for field 245");
        buildDataFieldFromString(INVALID_FIELD_245);
    }
    
    @Test
    public void validDataField_Valid() throws Exception {
        // No exception
        buildDataFieldFromString(VALID_DATAFIELD);
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlDataField buildDataFieldFromString(String s)
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlDataField(element);
    }
}

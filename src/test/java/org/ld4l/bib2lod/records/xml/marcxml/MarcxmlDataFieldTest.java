/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
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
    
    private static final String MULTIPLE_SUBFIELDS = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='a'>subfield a,</subfield>" +
                "<subfield code='b'>subfield b,</subfield>" +
                "<subfield code='b'>subfield b again,</subfield>" +
                "<subfield code='c'>subfield c,</subfield>" +
                "<subfield code='d'>subfield d,</subfield>" +
            "</datafield>";
    
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test
    public void noTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(NO_TAG);
    }

    @Test
    public void emptyTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(EMPTY_TAG);
    }
    
    @Test
    public void blankTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "must be non-empty");
        buildFromString(BLANK_TAG);
    }
    
    @Test
    public void invalidTag_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, 
                "must be exactly 3 characters long");
        buildFromString(INVALID_TAG);
    }
    
    @Test
    public void noValue_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "no subfields");
        buildFromString(NO_VALUE);
    }

    @Test
    public void nonSubfieldChild_Succeeds() throws Exception {
        // No exception
        buildFromString(NON_SUBFIELD_CHILD);
    }

    @Test
    public void invalidSubfield_ThrowsException() throws Exception {
        expectException(RecordFieldException.class, "code cannot be empty");
        buildFromString(INVALID_SUBFIELD);
    }
    
    @Test
    public void invalidField245_ThrowsException() throws Exception {
        expectException(
                RecordFieldException.class, "required for field 245");
        buildFromString(INVALID_FIELD_245);
    }
    
    @Test
    public void validDataField_Valid() throws Exception {
        // No exception
        buildFromString(VALID_DATAFIELD);
    } 
    
    @Test
    public void testListSubfieldValues() throws Exception {
        MarcxmlDataField field = buildFromString(MULTIPLE_SUBFIELDS);
        List<String> list = field.listSubfieldValues(
                Arrays.asList('a', 'b', 'c', 'e'));
        Assert.assertEquals(4, list.size());      
    }
    
    @Test
    public void testListSubfieldValuesNoMatch() throws Exception {
        MarcxmlDataField field = buildFromString(MULTIPLE_SUBFIELDS);
        List<String> list = field.listSubfieldValues(
                Arrays.asList('x', 'y', 'z'));
        Assert.assertEquals(0, list.size());      
    }

    @Test
    public void testConcatenateSubfieldValues() throws Exception {
        MarcxmlDataField field = buildFromString(MULTIPLE_SUBFIELDS);
        String values = field.concatenateSubfieldValues(
                Arrays.asList('a', 'b', 'c', 'e'));
        Assert.assertEquals("subfield a, subfield b, subfield b again, " + 
            "subfield c,", values);     
    }
    
    @Test
    public void testConcatenateSubfieldValuesNoMatch() throws Exception {
        MarcxmlDataField field = buildFromString(MULTIPLE_SUBFIELDS);
        String values = field.concatenateSubfieldValues(
                Arrays.asList('x', 'y', 'z'));
        Assert.assertNull(values);      
    }

    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MarcxmlDataField buildFromString(String s)
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlDataField(element);
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.RecordField.RecordFieldException;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlDataField.
 */
public class MarcxmlDataFieldTest extends AbstractTestClass {
    
    private static final String NO_VALUE = 
            "<datafield tag='245' ind1='0' ind2='0'></datafield>";
    
    private static final String NON_SUBFIELD_CHILD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                 "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
                 "<child>test</child>" +
             "</datafield>";
    
    private static final String NO_TAG = 
            "<datafield ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String EMPTY_TAG = 
            "<datafield tag=' ' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String INVALID_TAG_FORMAT = 
            "<datafield tag='1234' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";

    private static final String INVALID_INDICATOR_FORMAT = 
            "<datafield tag='245' ind1='10' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";   

    private static final String INVALID_SUBFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    
    private static final String VALID_DATAFIELD = 
            "<datafield tag='245' ind1='0' ind2='0'>" +
                "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" +
            "</datafield>";
    

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
        MarcxmlDataField dataField = buildDataFieldFromString(NO_VALUE);
        Assert.assertFalse(dataField.isValid());
    }
    
    @Test
    public void nonSubfieldChild_Ignored() throws Exception {
        MarcxmlDataField dataField = 
                buildDataFieldFromString(NON_SUBFIELD_CHILD);
        Assert.assertTrue(dataField.isValid());
    }
    
    @Test
    public void noTag_Invalid() throws Exception {
        MarcxmlDataField dataField = buildDataFieldFromString(NO_TAG);
        Assert.assertFalse(dataField.isValid());
    }
    
    @Test
    public void emptyTag_Invalid() throws Exception {
        MarcxmlDataField dataField = 
                buildDataFieldFromString(EMPTY_TAG);
        Assert.assertFalse(dataField.isValid());
    }
    
    @Test
    public void invalidTagFormat_Invalid() throws Exception {
        MarcxmlDataField dataField = 
                buildDataFieldFromString(INVALID_TAG_FORMAT);
        Assert.assertFalse(dataField.isValid());
    }
    
    @Test
    public void invalidIndicatorFormat_Invalid() throws Exception {
        MarcxmlDataField dataField = 
                buildDataFieldFromString(INVALID_INDICATOR_FORMAT);
        Assert.assertFalse(dataField.isValid());
    }            
    
    @Test
    public void invalidSubfield_Invalid() throws Exception {
        MarcxmlDataField dataField = buildDataFieldFromString(INVALID_SUBFIELD);
        Assert.assertFalse(dataField.isValid());
    }
    
    @Test
    public void validDataField_Valid() throws Exception {
        MarcxmlDataField dataField = buildDataFieldFromString(VALID_DATAFIELD);
        Assert.assertTrue(dataField.isValid());
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlDataField buildDataFieldFromString(String s) 
            throws RecordFieldException {
        return (MarcxmlDataField) XmlTestUtils.buildElementFromString(
                MarcxmlDataField.class, s);
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlControlField.
 */
public class MarcxmlControlFieldTest extends AbstractTestClass {
 
    private static final String NO_VALUE = 
            "<controlfield tag='001'></controlfield>";
    
    private static final String NO_CONTROL_NUMBER = 
            "<controlfield>102063</controlfield>";
    
    private static final String INVALID_CONTROL_NUMBER_FORMAT = 
            "<controlfield tag='1234'>102063</controlfield>";    
    
    private static final String NO_TEXT_VALUE = 
            "<controlfield tag='001'><child>test</child></controlfield>";

    private static final String VALID_CONTROL_FIELD = 
            "<controlfield tag='001'>102063</controlfield>";
    
 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
        MarcxmlControlField controlField = 
                buildControlFieldFromString(NO_VALUE);
        Assert.assertFalse(controlField.isValid());
    }
    
    @Test
    public void noControlNumber_Invalid() throws Exception {
        MarcxmlControlField controlField = 
                buildControlFieldFromString(NO_CONTROL_NUMBER);
        Assert.assertFalse(controlField.isValid());
    }

    @Test
    public void invalidControlNumberFormat_Invalid() throws Exception {
        MarcxmlControlField controlField = 
                buildControlFieldFromString(INVALID_CONTROL_NUMBER_FORMAT);
        Assert.assertFalse(controlField.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
        MarcxmlControlField controlField = buildControlFieldFromString(NO_TEXT_VALUE);
        Assert.assertFalse(controlField.isValid());
    }
    
    @Test
    public void validControlField_Valid() throws Exception {
        MarcxmlControlField controlField = 
                buildControlFieldFromString(VALID_CONTROL_FIELD);
        Assert.assertTrue(controlField.isValid());   
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

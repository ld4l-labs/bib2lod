/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlSubfield.
 */

/* test plan:
 * invalid code - more than 1 char
 * missing, empty, null code (see datafield tag)
 * no text value
 */
public class MarcxmlSubfieldTest extends AbstractTestClass {
   
    private static final String NO_CODE = 
            "<subfield>(CStRLIN)NYCX86B63464</subfield>";
    
    private static final String NO_VALUE = 
            "<subfield code='a'></subfield>";
    
    private static final String NO_TEXT_VALUE = 
            "<subfield code='a'><child>test</child></subfield>";
    
    private static final String VALID_SUBFIELD = 
            "<subfield code='a'>(CStRLIN)NYCX86B63464</subfield>";  
    

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noCode_Invalid() throws Exception {
        MarcxmlSubfield subfield = 
                buildSubfieldFromString(NO_CODE);
        Assert.assertFalse(subfield.isValid());
    }

    @Test
    public void noValue_Invalid() throws Exception {
        MarcxmlSubfield subfield = 
                buildSubfieldFromString(NO_VALUE);
        Assert.assertFalse(subfield.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
        MarcxmlSubfield subfield = 
                buildSubfieldFromString(NO_TEXT_VALUE);
        Assert.assertFalse(subfield.isValid());
    }
    
    @Test
    public void validSubfield_Valid() throws Exception {
        MarcxmlSubfield subfield = 
                buildSubfieldFromString(VALID_SUBFIELD);
        Assert.assertTrue(subfield.isValid());
    }
    

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlSubfield buildSubfieldFromString(String s) 
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlSubfield(element);
    }
}

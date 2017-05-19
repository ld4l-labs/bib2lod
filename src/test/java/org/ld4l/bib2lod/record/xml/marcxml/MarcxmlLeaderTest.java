/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Test;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlLeader;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.w3c.dom.Element;

/**
 * Tests class MarcxmlLeader.
 */
public class MarcxmlLeaderTest extends AbstractTestClass {

    private static final String NO_VALUE = "<leader/>";
    
    private static final String NO_TEXT_VALUE = 
            "<leader><subfield>Text</subfield></leader>";
      
    private static final String VALID_LEADER = 
            "<leader>01050cam a22003011  4500</leader>";

 
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void noValue_Invalid() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildLeaderFromString(NO_VALUE);
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
        expectException(RecordFieldException.class, "is null");
        buildLeaderFromString(NO_TEXT_VALUE);
    }
    
    @Test
    public void validLeader_Valid() throws Exception {
        // No exception
       buildLeaderFromString(VALID_LEADER);
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlLeader buildLeaderFromString(String s) 
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlLeader(element);
    }
}

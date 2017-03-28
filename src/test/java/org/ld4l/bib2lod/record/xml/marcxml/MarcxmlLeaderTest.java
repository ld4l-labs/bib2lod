/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.Field.RecordElementException;
import org.ld4l.bib2lod.record.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.AbstractTestClass;

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
        MarcxmlLeader leader = buildLeaderFromString(NO_VALUE);
        Assert.assertFalse(leader.isValid());
    }
    
    @Test
    public void noTextValue_Invalid() throws Exception {
        MarcxmlLeader leader = buildLeaderFromString(NO_TEXT_VALUE);
        Assert.assertFalse(leader.isValid());
    }
    
    @Test
    public void validLeader_Valid() throws Exception {
        MarcxmlLeader leader = buildLeaderFromString(VALID_LEADER);
        Assert.assertTrue(leader.isValid());
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlLeader buildLeaderFromString(String s) 
            throws RecordElementException {
        return (MarcxmlLeader) XmlTestUtils.buildElementFromString(
                MarcxmlLeader.class, s);
    }
}

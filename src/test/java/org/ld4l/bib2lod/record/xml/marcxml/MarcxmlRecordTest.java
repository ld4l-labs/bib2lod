/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.record.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.XmlTestUtils;

/**
 * Tests class MarcxmlRecord.
 */
public class MarcxmlRecordTest extends AbstractTestClass {
    
    private static final String TWO_LEADERS = 
        "<record>" + 
            "<leader>01050cam a22003011  4500</leader>" +
            "<leader>1234567</leader>" +
        "</record>";
    
    private static final String DUPLICATE_CONTROL_NUMBERS = 
            "<record>" + 
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='001'>duplicate field</controlfield>" +
            "</record>";
    
    private static final String MINIMAL_VALID_RECORD = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String RECORD_NO_LEADER = 
            "<record>" + 
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
 
    private static final String RECORD_NO_001 = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String RECORD_NO_008 = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String RECORD_NO_DATA_FIELDS = 
            "<record>" + 
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
            "</record>";
    
    private static final String RECORD_NO_TITLE_FIELD = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
            "</record>";

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void testMultipleLeaders_Ignored() throws Exception {
        MarcxmlRecord record = buildRecordFromString(TWO_LEADERS);
        List<MarcxmlField> fields = record.getFields();
        int leaderCount = 0;
        for (MarcxmlField field : fields) {
            if (field instanceof MarcxmlLeader) {
                leaderCount++;
            }
        }
        Assert.assertEquals(1, leaderCount);      
    }
    
    @Test
    public void testDuplicateControlNumbers_Ignored() throws Exception {
        MarcxmlRecord record = 
                buildRecordFromString(DUPLICATE_CONTROL_NUMBERS);
        List<MarcxmlControlField> fields = record.getControlFields();
        int fieldCount = 0;
        for (MarcxmlControlField field : fields) {
            if (field.getControlNumber().equals("001")) {
                fieldCount++;
            }
        }
        Assert.assertEquals(1, fieldCount);          
    }
    
    @Test
    public void testValidRecord_Valid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(MINIMAL_VALID_RECORD);
        Assert.assertTrue(record.isValid());
        
    }
    
    @Test
    public void testRecordNoLeader_Invalid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(RECORD_NO_LEADER);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void testRecordNo001_Invalid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(RECORD_NO_001);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void testRecordNo008_Invalid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(RECORD_NO_008);
        Assert.assertFalse(record.isValid());
    }
    
    @Test
    public void testRecordNoDataFields_Invalid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(RECORD_NO_DATA_FIELDS);
        Assert.assertFalse(record.isValid());
    }

    @Test
    public void testRecordNoTitle_Invalid() throws Exception {
        MarcxmlRecord record = buildRecordFromString(RECORD_NO_TITLE_FIELD);
        Assert.assertFalse(record.isValid());
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private MarcxmlRecord buildRecordFromString(String s) 
            throws RecordException {
        return (MarcxmlRecord) XmlTestUtils.buildRecordFromString(
                MarcxmlRecord.class, s);
    }
  
}

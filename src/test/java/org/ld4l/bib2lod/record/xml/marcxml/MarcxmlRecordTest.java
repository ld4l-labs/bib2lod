/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlLeader;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class MarcxmlRecord.
 */
public class MarcxmlRecordTest extends AbstractTestClass {

    
    private static final String NO_LEADER = 
            "<record>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String TWO_LEADERS = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<leader>1234567</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";

    
     private static final String NO_008 = 
             "<record>" + 
                 "<leader>01050cam a22003011  4500</leader>" +
                 "<controlfield tag='001'>102063</controlfield>" +
                 "<datafield tag='245' ind1='0' ind2='0'>" +
                     "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                 "</datafield>" +
             "</record>";
 
    private static final String DUPLICATE_008 = 
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String NO_245 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='040'>" +
                    "<subfield code='c'>NIC</subfield>" +
                    "<subfield code='d'>NIC</subfield>" + 
                "</datafield>" +
            "</record>";
    
    private static final String DUPLICATE_245 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>";

    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void noLeader_ThrowsException() throws Exception {
        expectException(RecordException.class, "no leader");
        MarcxmlTestUtils.buildRecordFromString(NO_LEADER);
    }
    
    @Test
    public void multipleLeaders_Ignored() throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(TWO_LEADERS);
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
    public void no008_ThrowsException() throws Exception {
        expectException(RecordException.class, "no 008");
        MarcxmlTestUtils.buildRecordFromString(NO_008);
    }
    
    @Test
    public void duplicateNonRepeatingControlFields_Ignored() throws Exception {
        MarcxmlRecord record = 
                MarcxmlTestUtils.buildRecordFromString(DUPLICATE_008);
        List<MarcxmlControlField> fields = record.getControlFields();
        int fieldCount = 0;
        for (MarcxmlControlField field : fields) {
            if (field.getControlNumber() == 8) {
                fieldCount++;
            }
        }
        Assert.assertEquals(1, fieldCount);          
    }

    @Test
    public void no245_ThrowsException() throws Exception {
        expectException(RecordException.class, "no 245");
        MarcxmlTestUtils.buildRecordFromString(NO_245);
    }
    
    @Test
    public void duplicateNonRepeatingDataFields_Ignored() throws Exception {
        // No exception
        MarcxmlTestUtils.buildRecordFromString(DUPLICATE_245);
    }
    
    @Test
    public void validRecord_Succeeds() throws Exception {
        // No exception
       MarcxmlTestUtils.buildRecordFromString(MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    
  
}

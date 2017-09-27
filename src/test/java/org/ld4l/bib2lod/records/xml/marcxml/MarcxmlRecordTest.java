/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class MarcxmlRecord.
 */
public class MarcxmlRecordTest extends AbstractTestClass {

    
    private static final MockMarcxml NO_LEADER = MockMarcxml.parse(
            "<record>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>"
            );
    
    private static final MockMarcxml TWO_LEADERS = MockMarcxml.parse(
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<leader>1234567</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>"
            );

    
     private static final MockMarcxml NO_008 = MockMarcxml.parse(
             "<record>" + 
                 "<leader>01050cam a22003011  4500</leader>" +
                 "<controlfield tag='001'>102063</controlfield>" +
                 "<datafield tag='245' ind1='0' ind2='0'>" +
                     "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                 "</datafield>" +
             "</record>"
             );
 
    private static final MockMarcxml DUPLICATE_008 = MockMarcxml.parse(
            "<record>" + 
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>"
            );
    
    private static final MockMarcxml NO_245 = MockMarcxml.parse(
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='040'>" +
                    "<subfield code='c'>NIC</subfield>" +
                    "<subfield code='d'>NIC</subfield>" + 
                "</datafield>" +
            "</record>"
            );
    
    private static final MockMarcxml DUPLICATE_245 = MockMarcxml.parse(
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>Clinical cardiopulmonary physiology.</subfield>" + 
                "</datafield>" +
            "</record>"
            );

    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------

    @Test
    public void noLeader_ThrowsException() throws Exception {
        expectException(RecordException.class, "no leader");
        buildRecord(NO_LEADER);
    }
    
    @Test
    public void multipleLeaders_Ignored() throws Exception {
        MarcxmlRecord record = buildRecord(TWO_LEADERS);
        List<BaseMarcxmlField> fields = record.getFields();
        int leaderCount = 0;
        for (BaseMarcxmlField field : fields) {
            if (field instanceof MarcxmlLeader) {
                leaderCount++;
            }
        }
        Assert.assertEquals(1, leaderCount);      
    }

    @Test
    public void no008_ThrowsException() throws Exception {
        expectException(RecordException.class, "no 008");
        buildRecord(NO_008);
    }
    
    @Test
    public void duplicateNonRepeatingControlFields_Ignored() throws Exception {
        MarcxmlRecord record = buildRecord(DUPLICATE_008);               
        List<MarcxmlControlField> fields = record.getControlFields();
        int fieldCount = 0;
        for (MarcxmlControlField field : fields) {
            if (field.getControlNumber().equals("008")) {
                fieldCount++;
            }
        }
        Assert.assertEquals(1, fieldCount);          
    }

    @Test
    public void no245_ThrowsException() throws Exception {
        expectException(RecordException.class, "no 245");
        buildRecord(NO_245);
    }
    
    @Test
    public void duplicateNonRepeatingDataFields_Ignored() throws Exception {
        // No exception
        buildRecord(DUPLICATE_245);
    }
    
    @Test
    public void validRecord_Succeeds() throws Exception {
        // No exception
        buildRecord(MINIMAL_RECORD);
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MarcxmlRecord buildRecord(MockMarcxml input) 
            throws RecordException {
        return input.toRecord();
    }
 
}

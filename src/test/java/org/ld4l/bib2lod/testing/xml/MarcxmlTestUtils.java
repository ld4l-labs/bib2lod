package org.ld4l.bib2lod.testing.xml;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.w3c.dom.Element;

/**
 * Helper methods for testing MARCXML conversion.
 */
public class MarcxmlTestUtils {
    
    public static final String MINIMAL_RECORD = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public final static MarcxmlRecord getMinimalRecord() throws RecordException {
        return new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(MINIMAL_RECORD));
    }

    public final static MarcxmlControlField buildControlFieldFromString(
            String element) throws RecordFieldException {                           
        return new MarcxmlControlField(
                XmlTestUtils.buildElementFromString(element));
    }
    
    public final static MarcxmlDataField buildDataFieldFromString(
            String element) throws RecordFieldException {                   
        return new MarcxmlDataField(
                XmlTestUtils.buildElementFromString(element));
    } 
    
    public final static MarcxmlSubfield buildSubfieldFromString(
            String element) throws RecordFieldException {                   
        return new MarcxmlSubfield(
                XmlTestUtils.buildElementFromString(element));
    } 
    
    public final static MarcxmlRecord buildRecordFromString(String s)
            throws RecordException {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MarcxmlRecord(element);
    }

}

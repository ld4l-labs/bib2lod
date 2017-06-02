package org.ld4l.bib2lod.testing.xml;

import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;

/**
 * Helper methods for testing MARCXML conversion.
 */
public class MarcxmlTestUtils {

    public final static MarcxmlControlField buildControlFieldFromString(String element) 
            throws RecordFieldException {        
        return new MarcxmlControlField(
                XmlTestUtils.buildElementFromString(element));
    }
    
    public final static MarcxmlDataField buildDataFieldFromString(String element) 
            throws RecordFieldException {        
        return new MarcxmlDataField(
                XmlTestUtils.buildElementFromString(element));
    } 

}

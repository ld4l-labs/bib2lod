package org.ld4l.bib2lod.records.xml.marcxml;

import org.ld4l.bib2lod.records.RecordField;

/**
 * Represents a field in a MarcxmlRecord with a tag attribute (i.e., a 
 * control field or a data field).
 */
public interface MarcxmlTaggedField extends RecordField {
    
    public int getTag();

}


/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.w3c.dom.Element;

/**
 * Represents a field in a MARCXML input record.
 */
public abstract class MarcxmlField extends BaseXmlElement {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Constructor
     */
    public MarcxmlField(Element element) {
        super(element);
    }
    
    /**
     * Returns the data field or control field tag number as an int.
     * @throws RecordFieldException 
     */
    public abstract int getTag() throws RecordFieldException;
    
    /**
     * Returns a substring of the field's text value. Returns null if field has 
     * no text value, or the end value is out of range.
     * or the substring is out of range, null 
     * @param start - start value
     * @param end - end value
     * @return String
     */
    public String getTextSubstring(int start, int end) {
        
        String textValue = getTextValue();
        if (! StringUtils.isEmpty(textValue) && textValue.length() >= end) {
            return textValue.substring(start, end);
        }
        return null;       
    }
    
    public char getCharAt(int pos) {
        return getTextSubstring(pos, pos+1).charAt(0);
    }

}

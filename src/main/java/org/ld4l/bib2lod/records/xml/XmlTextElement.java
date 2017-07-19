/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import org.ld4l.bib2lod.records.RecordField;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Represents an element with a text value in an XML record.
 */
public interface XmlTextElement extends RecordField {

    /**
     * Returns the text value of this element, or null if the element is not a
     * text node.
     */
    public String getTextValue();
    
    /**
     * Returns the text value of this XmlRecordElement. Returns null if the 
     * element is not an XmlTextElement, has no first child, or the first child 
     * is not a text node. 
     */
    default String setTextValue(Element element) {
        
        Node firstChild = element.getFirstChild();
        if (firstChild == null) {
            return null;
        }
        if (firstChild.getNodeType() == Node.TEXT_NODE) {
            return firstChild.getNodeValue();
        }
        if (firstChild.getNodeType() == Node.CDATA_SECTION_NODE) {
            return ((CDATASection)firstChild).getData();
        }
        return null;
    }
    
    /**
     * Returns a substring of the field's text value. Returns null if field has 
     * no text value, or the end value is out of range. 
     * @param start - start value
     * @param end - end value
     * @return String
     */
    default String getTextSubstring(int start, int end) 
                throws IndexOutOfBoundsException {

        if (! (this instanceof XmlTextElement) ) {
            return null;
        }      
        try {
            return getTextValue().substring(start, end);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    default char getCharAt(int pos) throws IndexOutOfBoundsException {
        return getTextSubstring(pos, pos+1).charAt(0);
    }

}
    


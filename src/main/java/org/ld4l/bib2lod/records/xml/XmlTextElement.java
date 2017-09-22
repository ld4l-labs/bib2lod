/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
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
     * Returns the clean text value of this element: trimmed, and with final
     * whitespace and punctuation removed. (See
     */
    public default String getTrimmedTextValue() {
        return trim(getTextValue());             
    }
    
    /**
     * Returns the text value of this XmlTextElement. Returns null if the 
     * DOM element has no first child, or the first child is not a text node. 
     */
    default String retrieveTextValue(Element domElement) {
        
        Node firstChild = domElement.getFirstChild();
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
    
    
    static final Pattern PATTERN_FINAL_PUNCT = 
            Pattern.compile(".+[.,;:]$");
    
    static final String PATTERN_FINAL_PUNCT_AND_WHITESPACE = 
            "\\s*[.,;:]?\\s*$";
    
    public static boolean endsWithPunct(String s) {
        Matcher m = PATTERN_FINAL_PUNCT.matcher(s);
        return m.matches();
    }
    
    public static String removeFinalPunct(String s) {
        return endsWithPunct(s) ? StringUtils.chop(s) : s;
    }
    
    public static String removeFinalPunctAndWhitespace(String s) {
        return s.replaceAll(PATTERN_FINAL_PUNCT_AND_WHITESPACE, "");
    }
    
    /**
     * Removes initial whitespace and final punctuation and  whitespace in any
     * order. Punctuation includes only ".,;:", since these may occur in the
     * record extraneously on data values such as dates, locations, names, 
     * titles, etc. and are not part of the actual text value.
     */
    public static String trim(String s) {
        return removeFinalPunctAndWhitespace(s.trim());
    }

}
    


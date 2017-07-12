/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.RecordField;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An abstract implementation.
 */
public abstract class BaseXmlElement implements RecordField {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String textValue;
    protected Element element;
    
    /**
     * Constructor
     */
    public BaseXmlElement(Element element) {
        this.element = element;
        this.textValue = setTextValue();
    }
    
    public Element getElement() {
        return element;
    }
    
    
    /**
     * Returns the text value of this XmlRecordElement. Returns null if the 
     * element is not an XmlTextElement, has no first child, or the first child 
     * is not a text node. 
     */
    private final String setTextValue() {
        if (! (this instanceof XmlTextElement) ) {
            return null;
        }
        Node firstChild = this.getElement().getFirstChild();
        if (firstChild == null) {
            return null;
        }
        if (firstChild.getNodeType() == Node.TEXT_NODE) {
            return firstChild.getNodeValue();
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
    public String getTextSubstring(int start, int end) 
                throws IndexOutOfBoundsException {

        if (! (this instanceof XmlTextElement) ) {
            return null;
        }      
        try {
            return textValue.substring(start, end);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    public char getCharAt(int pos) throws IndexOutOfBoundsException {
        return getTextSubstring(pos, pos+1).charAt(0);
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An abstract implementation.
 */
// TODO Not using at this point. Not clear whether it serves any purpose or just
// gets in the way. What are the methods common to different types of XML input?
public abstract class BaseXmlRecordElement implements XmlRecordElement {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String textValue;
    
    /**
     * Constructor
     */
    public BaseXmlRecordElement(Element element) {
        textValue = setTextValue(element);
    }
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.record.xml.XmlRecordElement#getTextContent()
     */
    @Override
    public String getTextValue() {
        return textValue;
    }
    
    /**
     * Determines the text value of this XmlRecordElement. Returns null if the 
     * element has no first child, or the first child is not a text node. 
     */
    private final String setTextValue(Element element) {
        Node firstChild = element.getFirstChild();
        if (firstChild == null) {
            return null;
        }
        if (firstChild.getNodeType() == Node.TEXT_NODE) {
            return firstChild.getNodeValue();
        }
        return null;
    }

}

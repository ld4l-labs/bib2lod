/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a control field in a MARCXML record.
 */
public class MarcxmlControlField extends MarcxmlField {
 
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private String name;
    private String value;
    
    /**
     * Constructor
     * @param controlField
     */
    MarcxmlControlField(Element controlField) {
        super(controlField);
        name = controlField.getAttribute("tag");
        // TODO Error-checking - what if there is no text?
        value = controlField.getTextContent();
    }
    
    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }
    

}

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
    private static final String CONTROL_NUMBER_ATTRIBUTE_NAME = "tag";
    
    private String controlNumber;
    private String value;
    
    /**
     * Constructor
     * @param controlField
     */
    MarcxmlControlField(Element controlField) {
        super(controlField);
        controlNumber = controlField.getAttribute(CONTROL_NUMBER_ATTRIBUTE_NAME);
        // TODO Error-checking - what if there is no text?
        value = controlField.getTextContent();
    }
    
    public String getControlNumber() {
        return controlNumber;
    }
    
    public String getValue() {
        return value;
    }
    
    protected static String getControlNumberAttributeName() {
        return CONTROL_NUMBER_ATTRIBUTE_NAME;
    }
    

}

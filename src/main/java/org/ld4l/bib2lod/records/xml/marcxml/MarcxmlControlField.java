/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

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
    
    /**
     * Constructor
     */
    public MarcxmlControlField(Element element) {
        super(element);
        controlNumber = element.getAttribute(CONTROL_NUMBER_ATTRIBUTE_NAME);
    }
    
    public String getControlNumber() {
        return controlNumber;
    }
    
    protected static String getControlNumberAttributeName() {
        return CONTROL_NUMBER_ATTRIBUTE_NAME;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.RecordElement#isValid()
     */
    @Override
    public boolean isValid() {
        
        if (controlNumber == null) {
            return false;
        }
        if (controlNumber.equals("")) {
            return false;
        }
        if (controlNumber.length() != 3) {
            return false;
        }
        if (textValue == null) {
            return false;
        }
        if (textValue.isEmpty()) {
            return false;
        }
        return true;
    }
}

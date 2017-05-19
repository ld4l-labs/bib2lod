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
    public MarcxmlControlField(Element element) throws RecordFieldException {
        super(element);
        controlNumber = element.getAttribute(CONTROL_NUMBER_ATTRIBUTE_NAME);
        isValid();
    }
    
    public String getControlNumber() {
        return controlNumber;
    }
    
    protected static String getControlNumberAttributeName() {
        return CONTROL_NUMBER_ATTRIBUTE_NAME;
    }

    private void isValid() throws RecordFieldException {
        
        if (controlNumber == null) {
            throw new RecordFieldException("control number is null");
        }
        if (controlNumber.equals("")) {
            throw new RecordFieldException("control number is empty");
        }
        if (controlNumber.length() != 3) {
            throw new RecordFieldException("control number is not three characters");
        }
        if (textValue == null) {
            throw new RecordFieldException("text value is null");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("text value is empty");
        }
    }
}

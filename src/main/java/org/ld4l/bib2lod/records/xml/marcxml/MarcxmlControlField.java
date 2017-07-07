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
    
    private Integer controlNumber;
    
    /**
     * Constructor
     */
    public MarcxmlControlField(Element element) throws RecordFieldException {
        super(element);
        try {
            controlNumber = Integer.parseInt(
                    element.getAttribute(CONTROL_NUMBER_ATTRIBUTE_NAME));
            isValid();
        } catch (NumberFormatException e) {
            throw new RecordFieldException("Invalid control number.");
        }
    }
    
    /**
     * Alias of getTag().
     */
    public int getControlNumber() {
        return controlNumber;
    }
    
    @Override
    public int getTag() {
        return controlNumber;
    }
    
    protected static String getControlNumberAttributeName() {
        return CONTROL_NUMBER_ATTRIBUTE_NAME;
    }

    private void isValid() throws RecordFieldException {
        
        if (controlNumber == null) {
            throw new RecordFieldException("Control number is null.");
        }
        if (! (controlNumber > 0 && controlNumber < 10)) {
            throw new RecordFieldException(
                    "Control number is not between 1 and 9.");
        }
        if (textValue == null) {
            throw new RecordFieldException("Text value is null.");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("Text value is empty.");
        }
    }
}

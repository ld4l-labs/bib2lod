/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
import org.w3c.dom.Element;

/**
 * Represents a control field in a MARCXML record.
 */
public class MarcxmlControlField extends BaseMarcxmlField 
        implements MarcxmlTaggedField, XmlTextElement {
 
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String CONTROL_NUMBER_ATTRIBUTE_NAME = "tag";
    
    private Integer tag;
    private String textValue;

    
    /**
     * Constructor
     */
    public MarcxmlControlField(Element element) throws RecordFieldException {
        super(element);
        try {
            tag = Integer.parseInt(
                    element.getAttribute(CONTROL_NUMBER_ATTRIBUTE_NAME));
            textValue = retrieveTextValue(this.element);
            isValid();
        } catch (NumberFormatException e) {
            throw new RecordFieldException("Control number is not an integer.");
        }
    }
    
    static String getControlNumberAttributeName() {
        return CONTROL_NUMBER_ATTRIBUTE_NAME;
    }
    
    /**
     * Alias of getTag().
     */
    public int getControlNumber() {
        return tag;
    }
    
    @Override
    public int getTag() {
        return tag;
    }
    
    @Override
    public String getTextValue() {        
        return textValue;
    }

    private void isValid() throws RecordFieldException {
        
        if (tag == null) {
            throw new RecordFieldException("Control number is null.");
        }
        if (! (tag > 0 && tag < 10)) {
            throw new RecordFieldException(
                    "Control number is not between 1 and 9.");
        }
        if (textValue == null) {
            throw new RecordFieldException("Text value is null.");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("Text value is empty.");
        }
        if (tag == 8 && textValue.length() != 40) {
            throw new RecordFieldException(
                    "Control field 008 does not contain exactly 40 characters.");
        }
    }

}

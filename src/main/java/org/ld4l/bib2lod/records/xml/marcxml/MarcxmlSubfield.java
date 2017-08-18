/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
import org.w3c.dom.Element;

/**
 * Represents a subfield in a MARCXML record.
 */
public class MarcxmlSubfield extends BaseMarcxmlField implements XmlTextElement {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private char code;
    private String textValue;

    /**
     * Constructor
     */
    public MarcxmlSubfield(Element element) throws RecordFieldException {
        super(element);   
        try {
            code = element.getAttribute("code").charAt(0);  
            textValue = retrieveTextValue(this.element);
            isValid();
        } catch (IndexOutOfBoundsException e) {
            throw new RecordFieldException("Subfield code cannot be empty.");
        }
    }

    public char getCode() {
        return code;
    }
    
    @Override
    public String getTextValue() {        
        return textValue;
    }
    
    private void isValid() throws RecordFieldException {

        // Here we test only the code format, not whether specific codes are
        // valid for specific data fields.
        if (code == ' ') {
            throw new RecordFieldException("Code is blank.");
        }
        if (textValue == null) {
            throw new RecordFieldException("Text value is null.");
        }
        if (textValue.isEmpty()) {
            throw new RecordFieldException("Text value is empty.");
        }
    }

}
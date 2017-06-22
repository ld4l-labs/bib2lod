/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a subfield in a MARCXML record.
 */
public class MarcxmlSubfield extends MarcxmlField {

    private static final Logger LOGGER = LogManager.getLogger(); 
    private static final String CODE_ATTRIBUTE_NAME = "code";
    
    private char code;

    /**
     * Constructor
     */
    public MarcxmlSubfield(Element element) throws RecordFieldException {
        super(element);   
        try {
            code = element.getAttribute(CODE_ATTRIBUTE_NAME).charAt(0);         
            isValid();
        } catch (IndexOutOfBoundsException e) {
            throw new RecordFieldException("Subfield code cannot be empty.");
        }
    }

    public char getCode() {
        return code;
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
/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a subfield in a MARCXML record.
 */
public class MarcxmlSubfield extends MarcxmlField {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private String code;

    /**
     * Constructor
     */
    public MarcxmlSubfield(Element element) {
        super(element);       
        code = element.getAttribute("code");
    }

    public String getCode() {
        return code;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.RecordElement#isValid()
     */
    @Override
    public boolean isValid() {

        // Here we test only the code format, not whether specific codes are
        // valid for specific data fields.
        if (code == null) {
            return false;
        }
        if (code.equals("")) {
            return false;
        }
        if (code.equals(" ")) {
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
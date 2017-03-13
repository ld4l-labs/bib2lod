/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a subfield in a MARCXML record.
 */
// TODO TBD is there value in the interface?
public class MarcxmlSubfield extends MarcxmlField {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private String name;
    private String value;

    /**
     * @param element
     */
    MarcxmlSubfield(Element subfield) {
        super(subfield);
        name = subfield.getAttribute("code");
        // TODO Error-checking - what if there is no text?
        value = subfield.getTextContent();
    }

    public String getName() {
        return name;
    }
    
    public String getValue() {
        return value;
    }

}
/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a MARCXML field in a MARCXML input record.
 */
public abstract class MarcxmlField extends BaseRecordElement {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // Reference to the underlying XML element
    protected Element fieldElement;
      
    protected String textValue;
    
    // TODO Add constructor

    /**
     * Returns text value of the field, or null if none exists
     */
    public String getTextValue() {
        return textValue;
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.xml.BaseXmlElement;
import org.w3c.dom.Element;

/**
 * Represents a field in a MARCXML input record.
 */
public abstract class BaseMarcxmlField extends BaseXmlElement { 

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Constructor
     */
    public BaseMarcxmlField(Element element) {
        super(element);
    }

}

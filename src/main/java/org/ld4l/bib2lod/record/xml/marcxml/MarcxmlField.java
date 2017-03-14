/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.record.xml.BaseXmlRecord;
import org.ld4l.bib2lod.record.xml.XmlRecord;
import org.w3c.dom.Element;

/**
 * Represents a field in a MARCXML input record.
 */
public abstract class MarcxmlField extends BaseXmlRecord {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // TODO Maybe we don't need to store this.
    Element element;
    
    /**
     * Constructor
     * @param element
     */
    MarcxmlField(Element element) {
       super(element);
    }

}

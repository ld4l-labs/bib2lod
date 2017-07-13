/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.RecordField;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * An abstract implementation.
 */
public abstract class BaseXmlElement implements RecordField {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected Element element;
    
    /**
     * Constructor
     */
    public BaseXmlElement(Element element) {
        this.element = element;
    }
    
    public Element getElement() {
        return element;
    }

}

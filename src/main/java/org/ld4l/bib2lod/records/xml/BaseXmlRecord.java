/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * An abstract implementation.
 */
public abstract class BaseXmlRecord implements XmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    protected ArrayList<Element> elements;
    
    /**
     * Constructor
     */
    public BaseXmlRecord(Element record) {
        elements = new ArrayList<Element>();
    }

}

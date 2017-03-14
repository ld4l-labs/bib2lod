/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents the leader in a MARCXML record.
 */
public class MarcxmlLeader extends MarcxmlField {
    
    private static final Logger LOGGER = LogManager.getLogger();    
    private String value;

    /**
     * Constructor
     * @param leader - the leader element
     */
    public MarcxmlLeader(Element leader) {
        super(leader);
        // TODO Error-checking - what if there is no text?
        value = leader.getTextContent();       
    }
    
    public String getValue() {
        return value;
    }
    

}

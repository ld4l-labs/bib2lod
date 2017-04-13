/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents the leader in a MARCXML record.
 */
public class MarcxmlLeader extends MarcxmlField {
    
    private static final Logger LOGGER = LogManager.getLogger();  
    
    Map<Integer, String> fields;

    /**
     * Constructor
     */
    public MarcxmlLeader(Element leader) {
        super(leader);
    }


    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.RecordElement#isValid()
     */
    @Override
    public boolean isValid() {
        if (textValue == null) {
            return false;
        }
        if (textValue.isEmpty()) {
            return false;
        }
        return true;
    }
    
}

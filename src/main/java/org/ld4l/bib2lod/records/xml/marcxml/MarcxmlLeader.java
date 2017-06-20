/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.Record.RecordException;
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
    public MarcxmlLeader(Element leader) throws RecordException{
        super(leader);
        isValid();
    }

    private void isValid() throws RecordFieldException {
        if (textValue == null) {
            throw new RecordFieldException("Leader text value is null.");
        }
        if (textValue.length() != 24) {
            throw new RecordFieldException(
                    "Leader does not have exactly 24 positions.");
        }
    }
    
}

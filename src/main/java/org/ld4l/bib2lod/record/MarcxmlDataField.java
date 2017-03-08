/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Represents a data field in a MARCXML record.
 */
public class MarcxmlDataField extends MarcxmlField {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private List<MarcxmlSubfield> subfields;
    
    // TODO Add constructor
  
    public List<MarcxmlSubfield> getSubfields() {
        return subfields;
    }


}

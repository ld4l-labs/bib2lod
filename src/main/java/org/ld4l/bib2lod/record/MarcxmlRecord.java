/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * Represents a MARCXML record.
 */
public class MarcxmlRecord extends BaseRecord {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
   
    // Reference to the underlying XML <record> element
    Element recordElement;
    
    List<MarcxmlField> fields;
    List<MarcxmlDataField> dataFields;
    MarcxmlLeader leader;
    List<MarcxmlControlField> controlFields;

    // TODO Add constructor
 
    /**
     * Gets this record's leader
     */
    public MarcxmlLeader getLeader() {
        return leader;        
    }
    
    /**
     * Gets this record's control fields.
     * @return
     */
    public List<MarcxmlControlField> getControlFields() {
        return controlFields;
    }
    
    /**
     * Get the control field for the specified value of the tag attribute
     * @param String tag - the value of the tag attribute
     * @return
     */
    public MarcxmlControlField getControlField(String tag) {
        // TODO - get the control field with the specified tag name
        return null;
    }
    
    /**
     * Gets this record's data fields
     * @return
     */
    public List<MarcxmlDataField> getDataFields() {
        return dataFields;
    }
    
    /**
     * Gets the data fields for the specified value of the tag attribute
     * @param String tag - the value of the tag attribute
     * @return
     */
    public List<MarcxmlDataField> getDataFields(String tag) {
        return null;
    }
    
    /**
     * Gets the first data field for the specified value of the tag attribute.
     * Use when only one element with the specified name is expected in
     * the record.
     * @param tag
     * @return
     */
    public MarcxmlDataField getDataField(String tag) {
        return null;
    }

}

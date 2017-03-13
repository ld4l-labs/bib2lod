/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.record.xml.XmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a MARCXML record.
 */
public class MarcxmlRecord implements XmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    private Element record;

    private MarcxmlLeader leader;
    private List<MarcxmlControlField> controlFields;
    private List<MarcxmlDataField> dataFields;
       
    public MarcxmlRecord(Element record) {
        this.record = record;
        
        NodeList leaderNodes = record.getElementsByTagName("leader");
        // There should be only one leader - ignore any others
        leader = new MarcxmlLeader((Element) leaderNodes.item(0));
        
        // TODO - see if elements (from BaseXmlRecord) adds anything. If not,
        // eliminate.
        // elements.add(leader);
        
        NodeList controlFieldNodes = 
                record.getElementsByTagName("controlfield");
        controlFields = new ArrayList<MarcxmlControlField>();
        for (int i = 0; i < controlFieldNodes.getLength(); i++) {
            controlFields.add(new MarcxmlControlField(
                    (Element) controlFieldNodes.item(i)));
        }
        
        // TODO - see if elements (from BaseXmlRecord) adds anything. If not,
        // eliminate.
        //elements.addAll(controlFields);
        
        NodeList dataFieldNodes = 
                record.getElementsByTagName("datafield");
        dataFields = new ArrayList<MarcxmlDataField>();
        for (int i = 0; i < dataFieldNodes.getLength(); i++) {
            dataFields.add(new MarcxmlDataField(
                    (Element) dataFieldNodes.item(i)));
        }
        
        // TODO - see if elements (from BaseXmlRecord) adds anything. If not,
        // eliminate.
        //elements.addAll(dataFields);

    }
 
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
     * Gets the control field for the specified value of the tag attribute.
     * Only one control field for a given tag value is expected.
     * @param String tag - the value of the tag attribute
     * @return
     */
    public MarcxmlControlField getControlField(String tag) {
        // TODO - get the control field with the specified tag name
        // TODO - what to do when if there is more than one?
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
        // TODO - get the data fields from the tag value
        return null;
    }
    
    /**
     * Gets the data field for the specified value of the tag attribute. Use
     * when only one element with the specified name is expected in the
     * record.
     * @param tag
     * @return
     */
    public MarcxmlDataField getDataField(String tag) {
        // TODO - get the data field from the tag value
        // TODO - what to do when if there is more than one?
        return null;
    }

}

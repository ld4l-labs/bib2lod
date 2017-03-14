/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.record.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a MARCXML record.
 */
public class MarcxmlRecord extends BaseXmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final String LEADER_TAG_NAME = "leader";
    private static final String CONTROL_FIELD_TAG_NAME = "controlfield";
    private static final String DATA_FIELD_TAG_NAME = "datafield";

    private MarcxmlLeader leader;
    private List<MarcxmlControlField> controlFields;
    private List<MarcxmlDataField> dataFields;
       
    public MarcxmlRecord(Element record) {
        super(record);
        
        leader = buildLeader();
        // TODO - see if elements (from BaseXmlRecord) adds anything. If not,
        // eliminate.
        // elements.add(leader);
        
        controlFields = buildControlFields();
        // elements.addAll(controlFields);
        
        dataFields = buildDataFields();
        // elements.addAll(dataFields);
    }
    
    /**
     * Builds this Record's leader from the MARCXML input. Only a single leader
     * is allowed; others are ignored. Returns null if no 
     * leader node is found.
     */
    private final MarcxmlLeader buildLeader() {
        NodeList leaderNodes = record.getElementsByTagName(LEADER_TAG_NAME);
        if (leaderNodes.getLength() == 0) {
            return null;
        }
        // There should be only one leader - ignore any others.
        return new MarcxmlLeader((Element) leaderNodes.item(0));        
    }
    
    /**
     * Builds this Record's control fields from the MARCXML input, allowing only
     * one control field per tag attribute value. Returns an empty List if there 
     * are no control fields.
     */
    private final List<MarcxmlControlField> buildControlFields() {
        
        List<MarcxmlControlField> controlFields = 
                new ArrayList<MarcxmlControlField>();

        NodeList controlFieldNodes = 
              record.getElementsByTagName(CONTROL_FIELD_TAG_NAME);
        
        List<String> controlNumbers = new ArrayList<String>();
        for (int i = 0; i < controlFieldNodes.getLength(); i++) {
            Element field = (Element) controlFieldNodes.item(i);
            // There should be only one control field per control number; ignore
            // others.
            String controlNumber = field.getAttribute(
                  MarcxmlControlField.getControlNumberAttributeName());
            if (! controlNumbers.contains(controlNumber)) {
                controlFields.add(new MarcxmlControlField(field));
                controlNumbers.add(controlNumber);
            }
        }  
        
        return controlFields;
    }
    
    /**
     * Builds this Record's data fields. Returns an empty List if there are 
     * none.
     */
    private List<MarcxmlDataField> buildDataFields() {
        
        List<MarcxmlDataField> dataFields = new ArrayList<MarcxmlDataField>();
        
        NodeList dataFieldNodes = 
                record.getElementsByTagName(DATA_FIELD_TAG_NAME);
        for (int i = 0; i < dataFieldNodes.getLength(); i++) {
            dataFields.add(
                    new MarcxmlDataField((Element) dataFieldNodes.item(i)));
        }   
        
        return dataFields;
     }
 
    /**
     * Returns this Record's leader
     */
    public MarcxmlLeader getLeader() {
        return leader;        
    }
    
    /**
     * Returns this Record's control fields.
     */
    public List<MarcxmlControlField> getControlFields() {
        return controlFields;
    }
    
    /**
     * Returns the control field for the specified value of the tag attribute.
     * Note that only one control field for a given tag value was included when 
     * the list was built. Returns null if no control field for the tag is
     * found.
     * @param String tag - the value of the tag attribute
     */
    public MarcxmlControlField getControlField(String tag) {
        
        for (MarcxmlControlField controlField : controlFields) {
            if (controlField.getControlNumber().equals(tag)) {
                return controlField;
            }
        }
        return null;
    }
    
    /**
     * Returns this Record's data fields
     */
    public List<MarcxmlDataField> getDataFields() {
        return dataFields;
    }
    
    /**
     * Returns the data fields for the specified value of the tag attribute. 
     * Returns an empty list if none are found.
     * @param String tag - the value of the tag attribute
     */
    public List<MarcxmlDataField> getDataFields(String tag) {
        
        List<MarcxmlDataField> fields = new ArrayList<MarcxmlDataField>();
        
        for (MarcxmlDataField field : dataFields) {
            if (field.getName().equals(tag)) {
                fields.add(field);
            }
        }
        
        return fields;
    }
    
    /**
     * Returns the data field for the specified value of the tag attribute. Use
     * when only one element with the specified name is expected in the
     * record. If multiple are found, returns the first. Returns null if no 
     * data field is found.
     * @param String tag - the value of the tag attribute
     */
    public MarcxmlDataField getDataField(String tag) {
   
        for (MarcxmlDataField field : dataFields) {
            if (field.getName().equals(tag)) {
                return field;
            }
        }
        
        return null;
    }

}

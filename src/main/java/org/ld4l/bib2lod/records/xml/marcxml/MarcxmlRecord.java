/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.BaseXmlRecord;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a MARCXML record.
 */
public class MarcxmlRecord extends BaseXmlRecord {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private enum Field {
        LEADER("leader"),
        CONTROL_FIELD("controlfield"),
        DATA_FIELD("datafield");
        
        private final String tagName;
        
        private Field(String tagName) {
            this.tagName = tagName;
        }       
    }

    private MarcxmlLeader leader;
    private List<MarcxmlControlField> controlFields;
    private List<MarcxmlDataField> dataFields;
       
    /**
     * Constructor
     */
    public MarcxmlRecord(Element record) throws RecordException {
        super(record);
        
        leader = buildLeader(record);
        
        controlFields = buildControlFields(record);
        
        dataFields = buildDataFields(record);
        
        isValid();
    }
    
    /**
     * Builds this Record's leader from the MARCXML input. Only a single leader
     * is allowed; others are ignored. Returns null if no leader node is found.
     */
    private final MarcxmlLeader buildLeader(Element record) 
            throws RecordException {
        NodeList leaderNodes = record.getElementsByTagName(Field.LEADER.tagName);
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
     * @param record 
     */
    private final List<MarcxmlControlField> buildControlFields(Element record) 
            throws RecordFieldException {
        
        List<MarcxmlControlField> controlFields = 
                new ArrayList<MarcxmlControlField>();

        NodeList controlFieldNodes = 
              record.getElementsByTagName(Field.CONTROL_FIELD.tagName);
        
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
     * @param record 
     */
    private final List<MarcxmlDataField> buildDataFields(Element record) 
            throws RecordFieldException {
        
        List<MarcxmlDataField> dataFields = new ArrayList<MarcxmlDataField>();
        
        NodeList dataFieldNodes = 
                record.getElementsByTagName(Field.DATA_FIELD.tagName);
        for (int i = 0; i < dataFieldNodes.getLength(); i++) {
            dataFields.add(
                    new MarcxmlDataField((Element) dataFieldNodes.item(i)));
        }   
        
        return dataFields;
     }
    
    // TODO This is the definition of a minimal record in Voyager. Not sure how
    // widely applicable it is. If it doesn't apply to other ILSs, create
    // subclass VoyagerMarcxmlRecord and put Voyager-specific tests 
    // there.
    private void isValid() throws RecordException {
        
        checkLeader();

        checkRequiredControlFields();

        checkRequiredDataFields();
    }
    
    private void checkLeader() throws RecordException {
        if (leader == null) {
            throw new RecordException("Record has no leader");
        }
    }
    
    private void checkRequiredControlFields() throws RecordException {
        boolean has001 = false;
        boolean has008 = false;
        for (MarcxmlControlField controlField : controlFields) {
            if (controlField.getControlNumber().equals("001")) {
                has001 = true;
            }
            if (controlField.getControlNumber().equals("008")) {
                has008 = true;
            }            
        }
        if (!has001) {
            throw new RecordException("Record has no 001 control field");
        }
        if (!has008) {
            throw new RecordException("Record has no 008 control field");
        }
    }
    
    private void checkRequiredDataFields() throws RecordException {
        for (MarcxmlDataField dataField : dataFields) {
            String dataFieldName = dataField.getName();
            if (dataFieldName.equals("245")) {
                return;
            }
        }
        throw new RecordException("Record does not contain a 245 field");
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
     * the list of fields was built. Returns null if no control field for the 
     * tag is found.
     * @param String tag - the value of the tag attribute
     */
    public MarcxmlControlField getControlField(String controlNumber) {
        
        for (MarcxmlControlField controlField : controlFields) {
            if (controlField.getControlNumber().equals(controlNumber)) {
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
     * Returns the datafields for the specified value of the tag attribute. 
     * Use with repeating datafields. Returns an empty List if none are found.
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
     * Returns the datafield for the specified value of the tag attribute. Use
     * for non-repeating datafields. If multiple are found, returns the first. 
     * Returns null if no datafield is found.
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
    
    public List<MarcxmlField> getFields() {
        List<MarcxmlField> fields = new ArrayList<MarcxmlField>();
        fields.add(leader);
        fields.addAll(controlFields);
        fields.addAll(dataFields);
        return fields;
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a data field in a MARCXML record.
 */
public class MarcxmlDataField extends BaseMarcxmlField 
        implements MarcxmlTaggedField {
  
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 

    private String tag;
    private Integer ind1;
    private Integer ind2;
    private List<MarcxmlSubfield> subfields;
    

    /**
     * Constructor
     */
    public MarcxmlDataField(Element element) throws RecordFieldException {

        super(element);

        this.tag = element.getAttribute("tag");
        this.ind1 = getIndicatorValue("ind1", element);
        this.ind2 = getIndicatorValue("ind2", element);

        NodeList subfieldNodes = 
                element.getElementsByTagName("subfield");
        subfields = new ArrayList<>();
        for (int i = 0; i < subfieldNodes.getLength(); i++) {
            subfields.add(new MarcxmlSubfield(
                    (Element) subfieldNodes.item(i)));
        }
        isValid();
        
    }
 
    @Override
    public String getTag() {
        return tag;
    }
    
    public Integer getFirstIndicator() {
        return ind1;
    }
    
    public Integer getSecondIndicator() {
        return ind2;
    }
    
    private Integer getIndicatorValue(String ind, Element element) {
        String value = element.getAttribute(ind);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }
  
    public List<MarcxmlSubfield> getSubfields() {
        return subfields;
    }
    
    /**
     * Returns a list of values of subfields of this data field with the
     * one of the specified codes, in order of occurrence. If no subfields, 
     * returns an empty list. Never returns null.
     */
    public List<String> listSubfieldValues(List<Character> codes) {
        List<String> values = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            if (! codes.contains(subfield.getCode())) {
                continue;
            }      
            values.add(subfield.getTextValue());
        }
        return values;        
    }
    
    /**
     * Concatenates the string values of the specified subfields of this
     * data field, in order of occurrence. If no subfields, 
     * returns null.
     */
    public String concatenateSubfieldValues(List<Character> codes) {
        String value = null;
        List<String> values = listSubfieldValues(codes);
        if (values.size() > 0) {
            value = StringUtils.join(values, " ");
        }
        return value; 
    }

    /**
     * Returns a list of subfields of this datafield with the specified code.
     * Use with repeating subfields. Returns an empty list if no subfields 
     * found.
     * @param String code - the value of the code attribute
     */
    public List<MarcxmlSubfield> getSubfields(char code) {
        List<MarcxmlSubfield> list = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            if (subfield.getCode() == code) {
                list.add(subfield);
            }
        }
        return list;
    }
    
    /**
     * Returns a list of subfields of this datafield with one of the 
     * specified codes, in the order in which they occur in the datafield. 
     * Returns an empty list if no subfields found.
     */
    public List<MarcxmlSubfield> getSubfields(List<Character> codes) {
        List<MarcxmlSubfield> list = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            if (codes.contains(subfield.getCode())) {
                list.add(subfield);
            }
        }
        return list;
    }
    
    public List<MarcxmlSubfield> getSubfields(Character...codes) {
        return getSubfields(Arrays.asList(codes));
    }
    
    /**
     * Returns the subfield of the datafield with the specified code. Use 
     * for  non-repeating  subfields. If sent a repeating subfield, returns  
     * the first encountered. Returns null if no subfield found. 
     * @param String code - the subfield code
     */
    public MarcxmlSubfield getSubfield(char code) {
        
        for (MarcxmlSubfield field : subfields) {
            if (field.getCode() == code) {
                return field;
            }
        }     
        return null;
    }
    
    /**
     * Returns true iff the datafield has the specified subfield.
     */
    public boolean hasSubfield(char code) {
        return getSubfield(code) != null;
    }
    
    private void isValid() throws RecordFieldException {

        if (StringUtils.isBlank(tag)) {
            throw new RecordFieldException("Tag must be non-empty.");
        }
        if (tag.length() != 3) {
            throw new RecordFieldException(
                    "Tag must be exactly 3 characters long.");
        }
        /*
         * Bad test: when pretty-printed there is whitespace inside the element.
        if (textValue != null) {
            throw new RecordFieldException("");
        }
        */
        if (subfields.isEmpty()) {
            throw new RecordFieldException("field has no subfields");
        }
        if (tag.equals("245")) {
           if (! ( hasSubfield('a') || hasSubfield('k') ) ) {
               throw new RecordFieldException(
                       "Subfield $a or $k required for field 245.");
           }
        }
    }

}

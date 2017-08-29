/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.util.Bib2LodStringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a data field in a MARCXML record.
 */
public class MarcxmlDataField extends BaseMarcxmlField 
        implements MarcxmlTaggedField {

    private static final Logger LOGGER = LogManager.getLogger(); 

    private Integer tag;
    private Integer ind1;
    private Integer ind2;
    private List<MarcxmlSubfield> subfields;
    

    /**
     * Constructor
     */
    public MarcxmlDataField(Element element) throws RecordFieldException {

        super(element);
        
        try {
            tag = Integer.parseInt(element.getAttribute("tag"));
        } catch (NumberFormatException e) {
            throw new RecordFieldException("Tag value is not an integer.");
        }

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

    
    /**
     * Returns a list of values of subfields of this data field with the
     * specified code. If no subfields, returns an empty list. Never returns
     * null.
     * @param code - the code of the subfields to get
     * @param trim - if true, trim and remove final punct and whitespace
     */
    public List<String> getSubfieldValues(char code, boolean trim) {
        List<String> values = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            if (!subfield.hasCode(code)) {
                continue;
            }
            String value = subfield.getTextValue();            
            values.add(trim ? Bib2LodStringUtils.trim(value) : value);
        }
        return values;        
    }
    
    public List<String> getTrimmedSubfieldValues(char code) {
        return getSubfieldValues(code, true);
    }
    
    /**
     * Returns a list of values of subfields of this data field with the
     * specified code. If no subfields, returns an empty list. Never returns
     * null.
     * @param code - the code of the subfields to get
     * @param clean - if true, trim and remove final punct and whitespace
     */
    public Set<String> getUniqueSubfieldValues(char code, boolean trim) {
        return new HashSet<String>(getSubfieldValues(code, trim)); 
    }
    
    public Set<String> getUniqueTrimmedSubfieldValues(char code) {
        return getUniqueSubfieldValues(code, true);
    }
    
    /**
     * Returns true iff this data field has a subfield with the specified code
     * with the specified value.
     * @param code - the code of the subfields to check
     * @param value - the value to look for 
     * @param clean - if true, trim and remove final punct and whitespace
     */
    public boolean hasSubfieldValue(char code, String value, boolean clean) {
            
        List<String> values = getSubfieldValues(code, clean);
        return values.contains(value);
    }
    
    public boolean hasCleanSubfieldValue(char code, String value) {
        return hasSubfieldValue(code, value, true);
    }
    
    private Integer getIndicatorValue(String ind, Element element) {
        String value = element.getAttribute(ind);
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }
      
    @Override
    public int getTag() {
        return tag;
    }
    
    public Integer getFirstIndicator() {
        return ind1;
    }
    
    public Integer getSecondIndicator() {
        return ind2;
    }
  
    public List<MarcxmlSubfield> getSubfields() {
        return subfields;
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
    
    /**
     * Returns the subfield of the datafield with the specified code. Use for 
     * non-repeating  subfields. If sent a repeating subfield, returns the first 
     * encountered. Returns null if no subfield found. 
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
    
    /**
     * Returns a list of subfield codes in the datafield. Note that a valid
     * datafield must have at least one subfield, so this method never
     * returns an empty list.
     */
    public List<Character> getSubfieldCodes() {        
    
        List<Character> codes = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            codes.add(subfield.getCode());
        }
        return codes;
    }
    
    public Set<Character> getUniqueSubfieldCodes() {
        
        Set<Character> codes = new HashSet<>();
        for (MarcxmlSubfield subfield : subfields) {
            codes.add(subfield.getCode());
        }
        return codes;        
    }
    
    /**
     * Returns true iff this datafield contains at least one subfield
     * in the specified list of character codes.
     */
    public boolean containsAnySubfield(List<Character> codes) {
        return CollectionUtils.containsAny(getSubfieldCodes(), codes);
    }
    
    /**
     * Returns true iff this datafield contains at least one subfield
     * in the specified array of character codes.
     */
    public boolean containsAnySubfield(Character[] codes) {
        return containsAnySubfield(Arrays.asList(codes));
    }
    
      
    /**
     * Returns the datafield in the specified list with the specified tag value.
     * Returns the first if multiple are found. Returns null if none are found. 
     */
    public static MarcxmlDataField get(
            List<MarcxmlDataField> fields, int tag) {
        
        for (MarcxmlDataField field: fields) {
            if (field.getTag() == tag) {
                return field;
            }
        }
        return null;       
    }

    private void isValid() throws RecordFieldException {

        if (tag == null) {
            throw new RecordFieldException("Tag is null.");
        }
        if (! (tag  > 0 && tag < 1000) ) {
            throw new RecordFieldException(
                    "Tag value is not between 1 and 999.");
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
        if (tag == 245) {
           if (! ( hasSubfield('a') || hasSubfield('k') ) ) {
               throw new RecordFieldException(
                       "Subfield $a or $k required for field 245.");
           }
        }
    }
    
}

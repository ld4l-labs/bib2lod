/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml.marcxml;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.util.Bib2LodStringUtils;
import org.ld4l.bib2lod.util.collections.MapOfLists;
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
    private MapOfLists<Character, String> subfieldMap;
    

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
        
        /* Create map of subfield codes to values - convenient for handling
        /* repeated subfields.
         * 
         * Consider eliminating List<Subfield> and storing only this map.
         */
        subfieldMap = new MapOfLists<>();
        for (MarcxmlSubfield subfield : subfields) {
            subfieldMap.addValue(subfield.getCode(), subfield.getTextValue());
        }
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
        for (String value : getSubfieldValues(code)) {
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
    
    public MapOfLists<Character, String> getSubfieldMap() {
        return subfieldMap;
    }

    /**
     * Returns the submap with keys in the specified character array.
     */
    @SuppressWarnings("unchecked")
    public MapOfLists<Character, String> getSubfieldSubmap(Character[] codes) {
        return (MapOfLists<Character, String>) subfieldMap.getSubmap(codes);
    }
    
    public List<String> getSubfieldValues(char code) {
        return subfieldMap.getValues(code);
    }

    /**
     * Returns a list of subfields of this datafield with the specified code.
     * Use with repeating subfields. Returns an empty List if no subfields 
     * found.
     * @param String code - the value of the code attribute
     */
    public List<MarcxmlSubfield> getSubfields(char code) {
        List<MarcxmlSubfield> sf = new ArrayList<>();
        for (MarcxmlSubfield subfield : subfields) {
            if (subfield.getCode() == code) {
                sf.add(subfield);
            }
        }
        return sf;
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

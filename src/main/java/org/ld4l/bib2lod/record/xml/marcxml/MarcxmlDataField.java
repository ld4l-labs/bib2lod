/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Represents a data field in a MARCXML record.
 */
public class MarcxmlDataField extends MarcxmlField {

    private static final Logger LOGGER = LogManager.getLogger(); 

    private String tag;
    private Integer ind1;
    private Integer ind2;
    private List<MarcxmlSubfield> subfields;
    

    /**
     * Constructor
     */
    public MarcxmlDataField(Element element) {
        super(element);
            
        tag = element.getAttribute("tag");
        
        ind1 = getIndicatorValue("ind1", element);
        ind2 = getIndicatorValue("ind2", element);

        NodeList subfieldNodes = 
                element.getElementsByTagName("subfield");
        subfields = new ArrayList<MarcxmlSubfield>();
        for (int i = 0; i < subfieldNodes.getLength(); i++) {
            subfields.add(new MarcxmlSubfield(
                    (Element) subfieldNodes.item(i)));
        }
    }
    
    private Integer getIndicatorValue(String ind, Element element) {
        String value = element.getAttribute(ind);
        if (value.isEmpty()) {
            return null;
        }
        return Integer.parseInt(value);
    }
                                                                                                                                                            
    public String getName() {
        return tag;
    }
    
    public int getFirstIndicator() {
        return ind1;
    }
    
    public int getSecondIndicator() {
        return ind2;
    }
  
    public List<MarcxmlSubfield> getSubfields() {
        return subfields;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.RecordElement#isValid()
     */
    @Override
    public boolean isValid() {
        
        if (tag == null) {
            return false;
        }
        if (tag.equals("")) {
            return false;
        }
        if (tag.equals(" ")) {
            return false;
        }
        if (Integer.parseInt(tag) > 999) {
            return false;
        }
        if (textValue != null) {
            return false;
        }
        if (! isValidIndicator(ind1)) {
            return false;
        }
        if (! isValidIndicator(ind2)) {
            return false;
        }
        if (subfields.isEmpty()) {
            return false;
        }
        for (MarcxmlSubfield subfield : subfields) {
            if (! subfield.isValid()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Return true iff the indicator value is valid. In this test we do not
     * consider the indicator values with respect to specific fields, but only
     * formal, non-field-specific validity. 
     */
    private boolean isValidIndicator(Integer indicator) {
        
        if (indicator == null) {
            return true;
        }
        if (indicator <= 9) {
            return true;
        }
        return false;
    }

}

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
    
    private String name;
    private String firstIndicator;
    private String secondIndicator;
    private List<MarcxmlSubfield> subfields;
    
    /**
     * Constructor
     * @param element
     */
    MarcxmlDataField(Element field) {
        super(field);
        name = field.getAttribute("tag");
        
        // TODO what happens if these are missing? Do we want them set to null
        // or empty?
        firstIndicator = field.getAttribute("ind1");
        secondIndicator = field.getAttribute("ind2");
        
        NodeList subfieldNodes = 
                field.getElementsByTagName("subfield");
        subfields = new ArrayList<MarcxmlSubfield>();
        for (int i = 0; i < subfieldNodes.getLength(); i++) {
            subfields.add(new MarcxmlSubfield(
                    (Element) subfieldNodes.item(i)));
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getFirstIndicator() {
        return firstIndicator;
    }
    
    public String getSecondIndicator() {
        return secondIndicator;
    }
  
    public List<MarcxmlSubfield> getSubfields() {
        return subfields;
    }


}

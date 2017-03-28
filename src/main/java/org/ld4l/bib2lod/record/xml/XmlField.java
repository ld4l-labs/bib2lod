/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.record.Field;
import org.w3c.dom.Element;

/**
 * Represents an element in an XML record.
 */
public interface XmlField extends Field {
    
    /**
     * Factory method
     * @param elementClass - the class of XmlRecordElement to instantiate
     * @param element - the XML element
     * @throws RecordElementException 
     */
    static XmlField instance(Class<?> elementClass, Element element) 
            throws RecordElementException {
        try {
            return (XmlField) elementClass
                    .getConstructor(Element.class)
                    .newInstance(element);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RecordElementException(e);
        }     
    }
    
    /**
     * Returns the text value of this element, or null if the element is not a
     * text node.
     */
    public String getTextValue();
    
}

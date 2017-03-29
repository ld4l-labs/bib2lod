/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.record.RecordField;
import org.w3c.dom.Element;

/**
 * Represents an element in an XML record.
 */
public interface XmlElement extends RecordField {
    
    /**
     * Factory method
     * @param elementClass - the class of XmlRecordElement to instantiate
     * @param element - the XML element
     * @throws RecordFieldException 
     */
    static XmlElement instance(Class<?> elementClass, Element element) 
            throws RecordFieldException {
        try {
            return (XmlElement) elementClass
                    .getConstructor(Element.class)
                    .newInstance(element);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RecordFieldException(e);
        }     
    }
    
    /**
     * Returns the text value of this element, or null if the element is not a
     * text node.
     */
    public String getTextValue();
    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.Record.RecordException;
import org.w3c.dom.Element;

/**
 * Represents an XML record. 
 */
public interface XmlRecord extends Record {

    /**
    /**
     * Factory method
     * @param recordClass - the class of XmlRecord to instantiate
     * @param recordElement - the XML record element
     * @throws RecordException 
     */
    static XmlRecord instance(Class<?> recordClass, Element recordElement) 
            throws RecordException {
        try {
            return (XmlRecord) recordClass
                    .getConstructor(Element.class)
                    .newInstance(recordElement);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new RecordException(e);
        }     
    }
    
}

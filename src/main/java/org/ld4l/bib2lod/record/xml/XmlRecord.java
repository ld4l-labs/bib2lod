/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.record.Record;
import org.w3c.dom.Element;

/**
 * Represents an XML input record. Record objects are immutable, providing no 
 * setter methods.
 */
// TODO Not clear whether it serves any purpose or just
// gets in the way. What are the methods common to different types of XML input?
public interface XmlRecord extends Record {

    /**
     * Factory method
     * @param recordClass - the class of record to instantiate
     * @param recordElement - the XML record element
     * @return the Record instance
     * @throws RecordException 
     */
    static Record instance(Class<?> recordClass, Element recordElement) 
            throws RecordException {
        try {
            return (Record) recordClass
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

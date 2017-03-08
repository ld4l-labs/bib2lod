/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

import java.util.List;

/**
 * Represents an input record. Record objects should be immutable, providing no
 * setter methods.
 */
public interface Record {

    /**
     * Returns all the elements in this record.
     * @return a list of RecordElement objects.
     */
    public List<RecordElement> getElements();
    
    /**
     * Returns a list of elements in this record identified by the specified 
     * name. The source of the name varies depending on the format. For example,
     * in MARCXML the element name is the value of the tag attribute of the 
     * datafield element. In FGDC it is the name of the element.
     * @param elementName - the elementName
     * @return a list of RecordElement objects
     */
    public List<RecordElement> getElements(String elementName);
    
    /**
     * Returns the first element in this record identified by the specified 
     * name. Use when only one element with the specified name is expected in
     * the record.
     * @param elementName
     * @return a RecordElement
     */
    public RecordElement getFirstElement(String elementName);
}

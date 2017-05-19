/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records.xml;

import org.ld4l.bib2lod.records.RecordField;

/**
 * Represents an element in an XML record.
 */
public interface XmlElement extends RecordField {
    
    /**
     * Returns the text value of this element, or null if the element is not a
     * text node.
     */
    public String getTextValue();
    
}

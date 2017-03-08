/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

/**
 * Represents a field in an input record.
 */
public interface RecordElement {
    
    /**
     * Gets the text value of the field. Interface defines a default for fields
     * that have complex values (e.g., a list of subfields) rather than a 
     * text value.
     * @return the text value of the field, or null if none exists
     */
    public default String getTextValue() {
        return null;
    }

}

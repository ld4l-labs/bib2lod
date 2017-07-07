/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.records;

import org.ld4l.bib2lod.records.Record.RecordException;

/**
 * Represents an element in a record. RecordElement objects should be immutable,  
 * providing no public setter methods, and only final private or protected 
 * setters.
 */
public interface RecordField {
 
    public static class RecordFieldException extends RecordException {
        private static final long serialVersionUID = 1L;

        public RecordFieldException(String message, Throwable cause) {
            super(message, cause);
        }

        public RecordFieldException(String message) {
            super(message);
        }

        public RecordFieldException(Throwable cause) {
            super(cause);
        }
    }

}

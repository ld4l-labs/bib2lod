/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

/**
 * Represents an element in a record. RecordElement objects should be immutable,  
 * providing no public setter methods, and only final private or protected 
 * setters.
 */
public interface RecordField {
 
    // TODO Should this be RecordFieldInstantiationException? See if it's used 
    // for anything other than instantiation.
    public static class RecordFieldException extends Exception {
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
    /**
     * Returns true iff the element is valid as defined by its instance methods.
     */
    // TODO Change return type to String: return an error message to be logged
    // for auditing, empty string if valid.
    public boolean isValid();

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

/**
 * Represents an element in a record. RecordElement objects should be immutable,  
 * providing no public setter methods, and only final private or protected 
 * setters.
 */
public interface Field {
 
    // TODO Should this be RecordElementInstantiationException? See if it's used for
    // anything other than instantiation.
    public static class RecordElementException extends Exception {
        private static final long serialVersionUID = 1L;

        public RecordElementException(String message, Throwable cause) {
            super(message, cause);
        }

        public RecordElementException(String message) {
            super(message);
        }

        public RecordElementException(Throwable cause) {
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

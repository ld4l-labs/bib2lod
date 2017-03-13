/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

/**
 * Represents an input record. Record objects should be immutable, providing no
 * setter methods.
 */
// Not clear whether it serves any purpose or just
// gets in the way. What are the methods common to XML and non-XML input?
// But without it, we can't do List<Record> records = parser.parse(reader);
public interface Record {
    
    // TODO Should this be RecordInstantiationException? See if it's used for
    // anything other than instantiation.
    public static class RecordException extends Exception {
        private static final long serialVersionUID = 1L;

        public RecordException(String message, Throwable cause) {
            super(message, cause);
        }

        public RecordException(String message) {
            super(message);
        }

        public RecordException(Throwable cause) {
            super(cause);
        }
    }



    
}

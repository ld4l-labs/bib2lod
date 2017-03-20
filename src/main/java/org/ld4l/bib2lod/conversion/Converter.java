/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;

/**
 * An object that orchestrates the conversion of an input reader containing one
 * or more records.
 */
public interface Converter {
    
    /**
     * Signals a problem during conversion of an input.
     */
    public static class ConverterException extends Exception {
        private static final long serialVersionUID = 1L;

        public ConverterException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConverterException(String message) {
            super(message);
        }

        public ConverterException(Throwable cause) {
            super(cause);
        }
    }
    
    /**
     * Signals a problem during conversion of a single record.
     */
    public static class RecordConversionException extends Exception {
        private static final long serialVersionUID = 1L;

        public RecordConversionException(String message, Throwable cause) {
            super(message, cause);
        }

        public RecordConversionException(String message) {
            super(message);
        }

        public RecordConversionException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Factory method
     */
    static Converter instance() {
        return Bib2LodObjectFactory.instance().createConverter();
    }

    public void convert(InputDescriptor input, OutputDescriptor output) 
            throws ConverterException;

}

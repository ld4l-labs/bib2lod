/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.util.List;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.records.Record;

/**
 * Parses input into Record and RecordElement objects.
 */
public interface Parser {
    public static class ParserException extends Exception {
        private static final long serialVersionUID = 1L;

        public ParserException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParserException(String message) {
            super(message);
        }

        public ParserException(Throwable cause) {
            super(cause);
        }
    }
    
    public static class ParserInstantiationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ParserInstantiationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ParserInstantiationException(String message) {
            super(message);
        }

        public ParserInstantiationException(Throwable cause) {
            super(cause);
        }        
    }
       

    /**
     * Factory method
     */
    static Parser instance() {
        return Bib2LodObjectFactory.getFactory()
                .instanceForInterface(Parser.class);
    }

    /**
     * Parses input into a list of Record objects.
     * @throws ParserException
     */
    public List<Record> parse(InputDescriptor input) throws ParserException;   
    
    public boolean isValidRecord(Record record);
    
}

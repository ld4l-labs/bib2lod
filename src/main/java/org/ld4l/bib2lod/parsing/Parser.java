/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.record.Record;

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
     * @param configuration - the program Configuration
     * @param parserClass - the class of parser to instantiate
     * @return the Parser instance
     * @throws ParserException 
     */
    static Parser instance(Configuration configuration, Class<?> parserClass) 
            throws ParserInstantiationException {
        try {
            return (Parser) parserClass
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new ParserInstantiationException(e);
        }     
    }

    /**
     * Parses input into a list of Record objects.
     * @param input - an InputDescriptor
     * @return a list of Record objects
     * @throws ParserException
     */
    public List<Record> parse(InputDescriptor input) throws ParserException;   
    
    public boolean isValidRecord(Record record);
    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;
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
       

    /**
     * Factory method
     * @param configuration - the program Configuration
     * @param parserClass - the class of parser to instantiate
     * @return the Parser instance
     * @throws ParserException 
     */
    static Parser instance(Configuration configuration, Class<?> parserClass) 
            throws ParserException {
        try {
            return (Parser) parserClass
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            throw new ParserException(e);
        }     
    }

    /**
     * Parses a reader into a list of Record objects.
     * @param reader - the input reader
     * @return a List of Records
     * @throws ParserException
     */
    public List<Record> parse(Reader reader) throws ParserException;
            
    
}

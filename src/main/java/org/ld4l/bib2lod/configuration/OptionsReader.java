/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Return the options as obtained from the command line and/or a config file.
 */
public interface OptionsReader {
    /**
     * A problem with creating or using the OptionsReader.
     */
    public static class OptionsReaderException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public OptionsReaderException(String message) {
            super(message);                 
        }

        public OptionsReaderException(String message, Throwable cause) {
            super(message, cause);
        }

        public OptionsReaderException(Throwable cause) {
            super(cause);
        }
        
    }
    
    /**
     * Factory method
     */
    static OptionsReader instance(String[] args) {
        return Bib2LodObjectFactory.instance().createOptionsReader(args);
    }

    JsonNode configure();
}

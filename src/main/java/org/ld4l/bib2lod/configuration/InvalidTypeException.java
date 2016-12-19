package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.Configuration.Key;

class InvalidTypeException extends RuntimeException { 
    
    private static final long serialVersionUID = 1L;

    protected InvalidTypeException(Key key) {
        super("Value of configuration key '" + key.string + 
                " is of invalid type.'");
    }
}
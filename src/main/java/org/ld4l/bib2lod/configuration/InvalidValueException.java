package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.Configuration.Key;

class InvalidValueException extends RuntimeException {  
    
    private static final long serialVersionUID = 1L;

    protected InvalidValueException(Key key) {
        super("Value of configuration key '" + key.string + 
                "' is invalid.");
    }
    
    protected InvalidValueException(Key key, String msg) {
        super("Value of configuration key '" + key.string + 
                "' is invalid: " + msg + ".");
    }
}
package org.ld4l.bib2lod.options;

import org.ld4l.bib2lod.configuration.Configuration.Key;

public class InvalidValueException extends RuntimeException {  
    
    private static final long serialVersionUID = 1L;

    protected InvalidValueException(Key key) {
        super("Value of configuration key '" + key.string() + 
                "' is invalid.");
    }
    
    public InvalidValueException(Key key, String msg) {
        super("Value of configuration key '" + key.string() + 
                "' is invalid: " + msg + ".");
    }
}
package org.ld4l.bib2lod.options;


public class InvalidValueException extends RuntimeException {  
    
    private static final long serialVersionUID = 1L;

    protected InvalidValueException(String key) {
        super("Value of configuration key '" + key + 
                "' is invalid.");
    }
    
    public InvalidValueException(String key, String msg) {
        super("Value of configuration key '" + key + 
                "' is invalid: " + msg + ".");
    }
}
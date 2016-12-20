package org.ld4l.bib2lod.options;


public class InvalidTypeException extends RuntimeException { 
    
    private static final long serialVersionUID = 1L;

    protected InvalidTypeException(String key) {
        super("Value of configuration key '" + key + " is of invalid type.'");                
    }
}
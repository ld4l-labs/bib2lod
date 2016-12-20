package org.ld4l.bib2lod.configuration.options;


public class RequiredKeyMissingException extends RuntimeException {   
    
    private static final long serialVersionUID = 1L;

    protected RequiredKeyMissingException(String key) {
        super("Configuration is missing required key '" + key  + ".'");              
    }
}
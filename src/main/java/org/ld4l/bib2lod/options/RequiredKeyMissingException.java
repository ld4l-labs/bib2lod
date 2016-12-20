package org.ld4l.bib2lod.options;

import org.ld4l.bib2lod.configuration.Configuration.Key;

public class RequiredKeyMissingException extends RuntimeException {   
    
    private static final long serialVersionUID = 1L;

    protected RequiredKeyMissingException(Key key) {
        super("Configuration is missing required key '" + key.string() 
                + ".'");
    }
}
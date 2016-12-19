package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.Configuration.Key;

class RequiredKeyMissingException extends RuntimeException {   
    
    private static final long serialVersionUID = 1L;

    protected RequiredKeyMissingException(Key key) {
        super("Configuration is missing required key '" + key.string 
                + ".'");
    }
}
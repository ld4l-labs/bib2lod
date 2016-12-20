package org.ld4l.bib2lod.options;

import org.ld4l.bib2lod.configuration.Configuration.Key;

public class RequiredValueNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected RequiredValueNullException(Key key) {
        super("Value of required configuration key '" + key.string() 
                + " is null.'");
    }
}
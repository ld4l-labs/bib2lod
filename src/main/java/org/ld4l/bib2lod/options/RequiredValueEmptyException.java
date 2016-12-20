package org.ld4l.bib2lod.options;

import org.ld4l.bib2lod.configuration.Configuration.Key;

public class RequiredValueEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected RequiredValueEmptyException(Key key) {
        super("Value of required configuration key '" + key.string() + 
                " is empty.'");
    }
}
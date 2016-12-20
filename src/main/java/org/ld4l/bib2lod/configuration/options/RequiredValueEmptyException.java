package org.ld4l.bib2lod.configuration.options;

public class RequiredValueEmptyException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected RequiredValueEmptyException(String key) {
        super("Value of required configuration key '" + key + " is empty.'");
                
    }
}
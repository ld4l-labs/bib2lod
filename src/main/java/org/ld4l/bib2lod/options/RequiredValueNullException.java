package org.ld4l.bib2lod.options;

public class RequiredValueNullException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    protected RequiredValueNullException(String key) {
        super("Value of required configuration key '" + key + " is null.'");
                
    }
}
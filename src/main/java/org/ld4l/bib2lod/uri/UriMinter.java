package org.ld4l.bib2lod.uri;

public interface UriMinter {

    public String getLocalNamespace();
    
    public String mintUri();
    
}

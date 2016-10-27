package org.ld4l.bib2lod.uri;

public abstract class BaseUriMinter implements UriMinter {
    
    private String localNamespace;

    public BaseUriMinter(String localNamespace) {
        this.localNamespace = localNamespace;
    }
    
    public String getLocalNamespace() {
        return localNamespace;
    }

}

package org.ld4l.bib2lod.uri;

import java.io.IOException;

public abstract class BaseUriMinter implements UriMinter {
    
    private String localNamespace;

    public BaseUriMinter(String localNamespace) {
        this.localNamespace = localNamespace;
    }
    
    public String getLocalNamespace() {
        return localNamespace;
    }
    
    public String mintUri() {
        return localNamespace + "/" + mintLocalName();
    }
    
    protected abstract String mintLocalName();


}

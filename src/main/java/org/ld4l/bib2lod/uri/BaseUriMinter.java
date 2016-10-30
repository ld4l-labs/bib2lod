package org.ld4l.bib2lod.uri;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class BaseUriMinter implements UriMinter {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseUriMinter.class);
    
    private String localNamespace;

    public BaseUriMinter(String localNamespace) {
        this.localNamespace = localNamespace;
    }
    
    public String getLocalNamespace() {
        return localNamespace;
    }
    
    public String mintUri() {
        String uri = localNamespace + "/" + mintLocalName();
        LOGGER.debug("URI = " + uri);
        return uri;
    }
    
    protected abstract String mintLocalName();


}

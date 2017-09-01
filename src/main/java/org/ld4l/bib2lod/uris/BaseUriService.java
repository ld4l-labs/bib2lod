package org.ld4l.bib2lod.uris;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entity.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseUriService implements UriService {

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String localNamespace;

    @Override
    public void configure(Configuration config) {
        localNamespace = config.getAttribute("localNamespace");
    }

    protected abstract String getLocalName(Entity entity);
    
    protected String buildUri(String localName) {
        return localNamespace + localName;
    }
    

}

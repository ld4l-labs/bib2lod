package org.ld4l.bib2lod.uris;

import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseUriService implements UriService {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String localNamespace;

    @Override
    public void configure(Configuration config) {
        localNamespace = config.getAttribute("localNamespace");
    }

    @Override
    public String getUri(Entity entity, Iterator<UriService> it) {
        // TODO Auto-generated method stub
        throw new RuntimeException("BaseUriService.getUri() not implemented.");
        
    }

    protected abstract String getLocalName(Entity entity);
    
    protected String buildUri(String localName) {
        return localNamespace + localName;
    }
    

}

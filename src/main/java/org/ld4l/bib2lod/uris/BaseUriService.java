package org.ld4l.bib2lod.uris;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.EntityInterface;

/**
 * An abstract implementation.
 */
public abstract class BaseUriService implements UriService {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected String localNamespace;

    /**
     * Constructor.
     * @param configuration
     */
    public BaseUriService(Configuration configuration) {
        this.localNamespace = configuration.getLocalNamespace();
    }

    protected abstract String getLocalName(EntityInterface entity);
    
    protected String buildUri(String localName) {
        return localNamespace + localName;
    }
    

}

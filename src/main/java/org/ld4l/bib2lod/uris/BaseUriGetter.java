package org.ld4l.bib2lod.uris;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseUriGetter implements UriGetter {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // Remains to be seen which of these should be member variables. Everything
    // can be retrieved from the configuration, but it may be more convenient
    // to store them directly.
    protected Configuration configuration;
    protected String localNamespace;

    /**
     * Constructor.
     * @param configuration
     */
    public BaseUriGetter(Configuration configuration) {
        this.configuration = configuration;
        this.localNamespace = configuration.getLocalNamespace();
    }

    

    
    protected abstract String getLocalName(Entity entity);
    
    protected String buildUri(String localName) {
        return localNamespace + localName;
    }
    

}

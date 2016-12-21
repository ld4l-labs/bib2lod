package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.uri.UriMinter;

public abstract class BaseConverter implements Converter {
    
    protected UriMinter uriMinter;

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    // NB Is it possible to store the configuration as an instance variable, 
    // which creates cirular references (config points to converter, which 
    // points to config, ...). Is this a problem??
    public BaseConverter(Configuration configuration) {
        this.uriMinter = configuration.getUriMinter();
    }

  
}

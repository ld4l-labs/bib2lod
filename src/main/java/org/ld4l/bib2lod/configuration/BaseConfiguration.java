package org.ld4l.bib2lod.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

public abstract class BaseConfiguration implements Configuration {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseConfiguration.class);
    
    protected UriMinter uriMinter;
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    public BaseConfiguration() {
        // TODO Auto-generated constructor stub
    }
    
    public UriMinter getUriMinter() {
        return uriMinter;
    }
    

}

package org.ld4l.bib2lod.context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

public abstract class BaseContext implements Context {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseContext.class);
    
    private UriMinter uriMinter;
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    public BaseContext() {
        // TODO Auto-generated constructor stub
    }
    
    public UriMinter getUriMinter() {
        return uriMinter;
    }

}

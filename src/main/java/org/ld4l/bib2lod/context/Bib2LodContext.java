package org.ld4l.bib2lod.context;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonObject;

public class Bib2LodContext {

    private static final Logger LOGGER = 
            LogManager.getLogger(Bib2LodContext.class);
    
    // private UriMinter uriMinter;
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    /**
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public Bib2LodContext(String[] args) throws IOException, ParseException {    
        
        // Get the configuration used to configure the application and create 
        // the services.        
        JsonObject config = new Configurer(args).getConfig();
        
        // TODO Create Context object: configuration plus services (reader, 
        // writer, uri minter, logger, error handler)
        LOGGER.debug(config.toString()); 

    }
    
//    private void UriMinter setUriMinter() {
//        
//    }
//    
//    public UriMinter getUriMinter() {
//        return uriMinter;
//    }


}

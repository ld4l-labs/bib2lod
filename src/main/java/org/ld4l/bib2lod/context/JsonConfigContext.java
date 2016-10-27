package org.ld4l.bib2lod.context;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.context.configuration.JsonFileConfigurer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonConfigContext extends BaseContext {

    private static final Logger LOGGER = 
            LogManager.getLogger(JsonConfigContext.class);

    
    /**
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public JsonConfigContext(String[] args) throws IOException, ParseException {    
        
        // Get the configuration used to configure the application and create 
        // the services.        
        JsonObject config = new JsonFileConfigurer(args).getConfig();
        
        JsonElement services = config.get("services");
        LOGGER.debug(services.toString());
        

        
        // TODO Create Context object: configuration plus services (reader, 
        // writer, uri minter, logger, error handler)
//        LOGGER.debug(config.toString()); 
//        
//        JsonObject services = config.getAsJsonObject("services");
//        JsonObject uriMinter = services.getAsJsonObject("uri-minter");
        //String minterClass = uriMinter.getAsString();
        
        //LOGGER.debug(minterClass);


        

        
        //uriMinter = setUriMinter();
        

    }
//    
//    private void UriMinter setUriMinter() {
//        
//    }
    



}

package org.ld4l.bib2lod.context;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.context.configuration.Configurer;
import org.ld4l.bib2lod.context.configuration.JsonFileConfigurer;
import org.ld4l.bib2lod.uri.UriMinter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class JsonConfigConfiguration extends BaseConfiguration {

    private static final Logger LOGGER = 
            LogManager.getLogger(JsonConfigConfiguration.class);

    
    /**
     * @param args
     * @throws Exception 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ReflectiveOperationException
     */
    public JsonConfigConfiguration(String[] args) throws ClassNotFoundException, IOException, ParseException, ReflectiveOperationException {    
        
        // Get the configuration used to configure the application and create 
        // the services.      
  
        Configurer configurer = new JsonFileConfigurer(args);
        JsonNode config = ((JsonFileConfigurer) configurer).getConfig();
        
        LOGGER.debug(config.toString());
        
        JsonNode services = config.get("services");
        
        LOGGER.debug(services.toString());
        
        String localNamespace = getJsonStringValue(config, "localNamespace");
        this.uriMinter = createUriMinter(services, localNamespace);
        
        // TODO Add same for other services
        
        // TODO Add same for other config elements

    }
    
    private String getJsonStringValue(JsonNode node, String key) {
        
        JsonNode value = node.get(key);
        if (value == null) {
            throw new RuntimeJsonMappingException("Required value " + key + " not defined in configuration.");
        }
        String stringValue = value.textValue();
        if (stringValue == null) {
            throw new RuntimeJsonMappingException("Required value " + key + " must be a string.");
        }
        return stringValue;
        
    }
        
        
    private UriMinter createUriMinter(JsonNode services,
            String localNamespace) throws ClassNotFoundException, ReflectiveOperationException {

        String uriMinterName = getJsonStringValue(services, "uriMinter");      

        Class<?> c = Class.forName(uriMinterName);
        UriMinter minter = (UriMinter) c.getConstructor(String.class)
                                        .newInstance(localNamespace); 
        return minter;
               
    } 
    
        


}

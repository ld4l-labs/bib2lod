package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.jena.iri.IRIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.options.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.options.JsonUtils;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.uri.UriMinter;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * An object providing program configuration values
 *
 */
public class Configuration {
    
    public static class InvalidValueException extends RuntimeException {         
        private static final long serialVersionUID = 1L;
        
        protected InvalidValueException(String key) {
            super("Value of configuration key '" + key + "' is invalid.");                 
        }
        
        public InvalidValueException(String key, String msg) {
            super("Value of configuration key '" + key + 
                    "' is invalid: " + msg + ".");
        }
    }

    private static final Logger LOGGER = LogManager.getLogger(); 

    private String localNamespace;
    private UriMinter uriMinter;
    private List<File> input;
    
    // TODO Convert to list later
    // private List<Converter> converters;
    private Converter converter;
    
    // cleaner, parser, converters
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    protected enum Key {
        
        CONVERTER("converter"),
        INPUT("input"),
        INPUT_LOCATION("location"),
        LOCAL_NAMESPACE("local_namespace"),
        URI_MINTER("uri_minter");
        
        final String string;
        
        Key(String string) {
            this.string = string;
        }
        
        public String string() {
            return this.string;
        }
    }
    
    /**
     * @param args - the commandline arguments
     * @throws Exception 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ReflectiveOperationException
     */
    public Configuration(String[] args) throws ClassNotFoundException, 
            FileNotFoundException, IOException, ParseException, 
                ReflectiveOperationException {    
        
        // Get the configuration values from the commandline values and 
        // specified config file as a JSON object.
        JsonOptionsReader optionsReader = new JsonOptionsReader(args);
        JsonNode config = optionsReader.configure();
        
        LOGGER.debug(config.toString());
         
        setLocalNamespace(config);
        
        // buildServices(config);

        // buildInputFileList(config);
        
        // buildConverters(config);
        // buildConverter(config);
    
        // TODO Add same for other config elements...

    }

    
    /**
     * Gets the configured local namespace.
     * @return localNamespace - the local namespace
     */
    public String getLocalNamespace() {
        return localNamespace;
    }
    
    /**
     * Gets the configured URI minter
     * @return uriMinter - the configured UriMinter
     */
    public UriMinter getUriMinter() {
        return uriMinter;
    }
    
    /**
     * Gets the configured list of input files.
     * @return input - the list of input files
     */
    // TODO Or just return the input string from config file?
    public List<File> getInput() {
        return input;
    }
    
//    public List<Converter> getConverters() {
//        return converters;
//    }
    
    /**
     * Gets the configured converter
     * @return converter - the converter
     */
    public Converter getConverter() {
        return converter;
    }
    
    /**
     * Sets the local namespace
     * @param config
     */
    protected void setLocalNamespace(JsonNode config) 
            throws IRIException, InvalidValueException {
        
        String localNamespace = 
                JsonUtils.getRequiredJsonStringValue(
                        config, Key.LOCAL_NAMESPACE.string);
        
        // Throws an error if the localNamespace is malformed.
        org.apache.jena.riot.system.IRIResolver.validateIRI(localNamespace);

        // Require the final slash, otherwise it could be a web page address
        if (!localNamespace.endsWith("/")) {
            throw new InvalidValueException(Key.LOCAL_NAMESPACE.string, 
                    "Local namespace must end in a forward slash.");
        }
               
        this.localNamespace = localNamespace;
    }
    
    /**
     * Builds the services specified in the config file
     * @param config
     * @throws ClassNotFoundException
     * @throws ReflectiveOperationException
     */
    protected void buildServices(JsonNode config) 
            throws ClassNotFoundException, ReflectiveOperationException {   
        
        JsonNode services = config.get("services");       
        LOGGER.debug(services.toString());   
    
        makeUriMinter(JsonUtils.getRequiredJsonStringValue(
                services, Key.URI_MINTER.string));
        
        // TODO Add same for other services...
       
    }
    
    /**
     * Builds the UriMinter service
     * @param minterClassName
     * @throws ClassNotFoundException
     * @throws ReflectiveOperationException
     */
    protected void makeUriMinter(String minterClassName) 
            throws ClassNotFoundException, ReflectiveOperationException {
        
        Class<?> c = Class.forName(minterClassName);
        this.uriMinter = (UriMinter) c.getConstructor(String.class)
                                        .newInstance(localNamespace);         
    }
    
    /**
     * Builds list of input files from the input path
     * @param input
     * @return 
     * @return
     * @throws FileNotFoundException 
     */
    // TODO Also pass in file type and make sure we get only the files of this
    // type
    protected void buildInputFileList(JsonNode config) 
            throws FileNotFoundException {

        // TODO Throw error if not defined
        JsonNode inputNode = config.get(Key.INPUT.string);
        String inputPath = JsonUtils.getRequiredJsonStringValue(
                inputNode, Key.INPUT_LOCATION.string);
//        String inputFormat = getJsonStringValue(inputNode, "format");
//        String fileExtension = getJsonStringValue(inputNode, "extension");
        
        this.input = new ArrayList<File>(); 
        
        File path = new File(inputPath);
        
        if (! path.exists()) {
            throw new FileNotFoundException("Input location not found.");            
        }

        // TODO - Add filter to path.listFiles() so that we get only the
        // files with the right extension. See FilenameFilter or FileFilter.
        if (path.isDirectory()) {
            this.input = Arrays.asList(path.listFiles());
        } else {
            this.input.add(path);
        }        
    }
    
    /**
     * Builds the converter specified in the config file
     * @param config
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    protected void buildConverter(JsonNode config) 
            throws ClassNotFoundException, InstantiationException, 
                IllegalAccessException, IllegalArgumentException, 
                InvocationTargetException, NoSuchMethodException, 
                SecurityException {

        // ObjectMapper mapper = new ObjectMapper();
        
        // JsonNode converterList = config.get("converters"); 
        // TODO Should be an array of strings or a string. For now just handle
        // an array.
        // if string...convert to list
        // else
        
        // TODO Get this to work. Hard-coding as a single converter for now
        // TypeReference ref = new TypeReference<List<Converter>>() {};
        // converters = mapper.readValue(converterList, ref);
       String converter = JsonUtils.getRequiredJsonStringValue(
               config, Key.CONVERTER.string);
       Class<?> converterClass = Class.forName(converter); 
       Constructor<?> constructor = 
               converterClass.getConstructor(this.getClass());
       this.converter = (Converter) constructor.newInstance(this); 
    }
    
 
}

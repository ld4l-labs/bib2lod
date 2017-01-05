/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.jena.iri.IRIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * A simple implementation.
 */
public class DefaultConfiguration implements Configuration {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    private String localNamespace;
    private UriMinter uriMinter;
    private List<File> input;  // or List<String>?
    private File output; // Or String
    
    // TODO Convert to list later
    private List<String> converters;
    private List<String> reconcilers;

    
    // cleaner, parser, converters
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    /**
     * Represents keys in the incoming JSON configuration.
     */
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
    }

    /**
     * Signals that the content of a configuration value is invalid.  Differs
     * from empty, null, or invalid types, which are handled by JsonUtils
     * exceptions, which are content-neutral. The DefaultConfiguration object 
     * evaluates the contents of the value.
     */
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

    
    /**
     * Constructor
     * @param args - the program arguments
     * @throws ClassNotFoundException 
     * @throws FileNotFoundException
     * @throws IOException 
     * @throws ParseException 
     */
    public DefaultConfiguration(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
            ParseException {
        
        // Get the configuration values from the commandline values and 
        // specified config file as a JSON object.
        // JsonNode config = OptionsReader.instance(args).configure();
        
        // LOGGER.debug(config.toString());
        
        // setLocalNamespace(config);
        
        // buildServices(config);

        // buildInputFileList(config);
        
        // buildConverters(config);
        // buildConverter(config);
    
        // TODO Add same for other config elements...

    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getLocalNamespace()
     */
    @Override
    public String getLocalNamespace() {
        return localNamespace;
    }

    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getInput()
     */
    // TODO Or just return the input string from config file?
    @Override
    public List<File> getInput() {
        return input;
    }

    
    /**
     * Sets the local namespace
     * @param config
     */
    protected void setLocalNamespace(JsonNode config) 
            throws IRIException, InvalidValueException {
        
        String localNamespace = JsonUtils.getRequiredTextNode(
                config, Key.LOCAL_NAMESPACE.string).textValue();
        
        // Throws an error if the localNamespace is malformed.
        org.apache.jena.riot.system.IRIResolver.validateIRI(localNamespace);

        // Require the final slash, otherwise it could be a web page address
        if (!localNamespace.endsWith("/")) {
            throw new InvalidValueException(Key.LOCAL_NAMESPACE.string, 
                    "Local namespace must end in a forward slash.");
        }
               
        this.localNamespace = localNamespace;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getUriMinter()
     */
    @Override
    public String getUriMinter() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getConverters()
     */
    @Override
    public List<String> getConverters() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getReconcilers()
     */
    @Override
    public List<String> getReconcilers() {
        // TODO Auto-generated method stub
        return null;
    }
 
// TODO - these will all go in the factory methods and factory
//    /**
//     * Builds the services specified in the config file
//     * @param config
//     * @throws ClassNotFoundException
//     * @throws ReflectiveOperationException
//     */
//    protected void buildServices(JsonNode config) 
//            throws ClassNotFoundException, ReflectiveOperationException {   
//        
//        JsonNode services = config.get("services");       
//        LOGGER.debug(services.toString());   
//    
//        makeUriMinter(JsonUtils.getRequiredJsonStringValue(
//                services, Key.URI_MINTER.string));
//        
//        // TODO Add same for other services...
//       
//    }
//    
//    /**
//     * Builds the UriMinter service
//     * @param minterClassName
//     * @throws ClassNotFoundException
//     * @throws ReflectiveOperationException
//     */
//    protected void makeUriMinter(String minterClassName) 
//            throws ClassNotFoundException, ReflectiveOperationException {
//        
//        Class<?> c = Class.forName(minterClassName);
//        this.uriMinter = (UriMinter) c.getConstructor(String.class)
//                                        .newInstance(localNamespace);         
//    }
//    
//    /**
//     * Builds list of input files from the input path
//     * @param input
//     * @return 
//     * @return
//     * @throws FileNotFoundException 
//     */
//    // TODO Also pass in file type and make sure we get only the files of this
//    // type
//    protected void buildInputFileList(JsonNode config) 
//            throws FileNotFoundException {
//
//        // TODO Throw error if not defined
//        JsonNode inputNode = config.get(Key.INPUT.string);
//        String inputPath = JsonUtils.getRequiredJsonStringValue(
//                inputNode, Key.INPUT_LOCATION.string);
////        String inputFormat = getJsonStringValue(inputNode, "format");
////        String fileExtension = getJsonStringValue(inputNode, "extension");
//        
//        this.input = new ArrayList<File>(); 
//        
//        File path = new File(inputPath);
//        
//        if (! path.exists()) {
//            throw new FileNotFoundException("Input location not found.");            
//        }
//
//        // TODO - Add filter to path.listFiles() so that we get only the
//        // files with the right extension. See FilenameFilter or FileFilter.
//        if (path.isDirectory()) {
//            this.input = Arrays.asList(path.listFiles());
//        } else {
//            this.input.add(path);
//        }        
//    }
//    
//    /**
//     * Builds the converter specified in the config file
//     * @param config
//     * @throws ClassNotFoundException
//     * @throws InstantiationException
//     * @throws IllegalAccessException
//     * @throws IllegalArgumentException
//     * @throws InvocationTargetException
//     * @throws NoSuchMethodException
//     * @throws SecurityException
//     */
//    protected void buildConverter(JsonNode config) 
//            throws ClassNotFoundException, InstantiationException, 
//                IllegalAccessException, IllegalArgumentException, 
//                InvocationTargetException, NoSuchMethodException, 
//                SecurityException {
//
//        // ObjectMapper mapper = new ObjectMapper();
//        
//        // JsonNode converterList = config.get("converters"); 
//        // TODO Should be an array of strings or a string. For now just handle
//        // an array.
//        // if string...convert to list
//        // else
//        
//        // TODO Get this to work. Hard-coding as a single converter for now
//        // TypeReference ref = new TypeReference<List<Converter>>() {};
//        // converters = mapper.readValue(converterList, ref);
//       String converter = JsonUtils.getRequiredJsonStringValue(
//               config, Key.CONVERTER.string);
//       Class<?> converterClass = Class.forName(converter); 
//       Constructor<?> constructor = 
//               converterClass.getConstructor(this.getClass());
//       this.converter = (Converter) constructor.newInstance(this); 
//    }
    
 
}

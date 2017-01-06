/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.apache.jena.iri.IRIException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;


/**
 * Implementation built from a JSON configuration file.
 */
public class ConfigurationFromJson extends BaseConfiguration {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Represents keys in the incoming JSON configuration.
     */
    protected enum Key {
        
        CONVERTER("converter"),
        INPUT("input"),
        INPUT_SOURCE("source"),
        LOCAL_NAMESPACE("local_namespace"),
        URI_MINTER("uri_minter");
        
        final String string;
        
        Key(String string) {
            this.string = string;
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
    public ConfigurationFromJson(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
            ParseException {
        
        // Get the configuration values from the commandline values and 
        // specified config file as a JSON object.
        JsonNode config = OptionsReader.instance(args).configure();
        
        LOGGER.debug(config.toString());
        
        setLocalNamespace(config);
    
        // TODO Add same for other config elements...

    }


    /**                                                                                                                          
     * Sets the local namespace, if valid. An exception is thrown down the
     * chain if the local namespace is invalid.                   
     * @param config - the JsonNode containing the configuration values
     * @return void
     */
    protected void setLocalNamespace(JsonNode config) {
        
        String localNamespace = JsonUtils.getRequiredStringValue(
                config, Key.LOCAL_NAMESPACE.string);
        
        super.setLocalNamespace(localNamespace);                       
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
//                inputNode, Key.INPUT_SOURCE.string);
////        String inputFormat = getJsonStringValue(inputNode, "format");
////        String fileExtension = getJsonStringValue(inputNode, "extension");
//        
//        this.input = new ArrayList<File>(); 
//        
//        File path = new File(inputPath);
//        
//        if (! path.exists()) {
//            throw new FileNotFoundException("Input source not found.");            
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

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.uri.UriMinter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

//TODO Is Context a better name?
public class Configuration {

    private static final Logger LOGGER = 
            LogManager.getLogger(Configuration.class);

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
    
    /**
     * @param args
     * @throws Exception 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws ParseException 
     * @throws ReflectiveOperationException
     */
    public Configuration(String[] args) throws ClassNotFoundException, 
            FileNotFoundException, IOException, ParseException, 
                ReflectiveOperationException {    
        
        // Get the configuration used to configure the application and create 
        // the services.      
  
        OptionsReader optionsReader = new OptionsReader(args);
        JsonNode config = optionsReader.configure();
        
        LOGGER.debug(config.toString());
         

        setLocalNamespace(config);
        
        buildServices(config);

        buildInputFileList(config);
        
        // buildConverters(config);
        buildConverter(config);
    
        // TODO Add same for other config elements...

    }


    public String getLocalNamespace() {
        return localNamespace;
    }
    
    public UriMinter getUriMinter() {
        return uriMinter;
    }
    
    // TODO Or just return the input string from config file?
    public List<File> getInput() {
        return input;
    }
    
//    public List<Converter> getConverters() {
//        return converters;
//    }
    public Converter getConverter() {
        return converter;
    }
    
    protected void setLocalNamespace(JsonNode config) {
        String localNamespace = getJsonStringValue(config, "localNamespace");
        if (!localNamespace.endsWith("/")) {
            localNamespace += "/";
        }
        this.localNamespace = localNamespace;
    }
    
    protected void buildServices(JsonNode config) 
            throws ClassNotFoundException, ReflectiveOperationException {   
        
        JsonNode services = config.get("services");       
        LOGGER.debug(services.toString());   
    
       makeUriMinter(getJsonStringValue(services, "uriMinter"));
        
        // TODO Add same for other services...
       
    }
    
    protected void makeUriMinter(String minterClassName) 
            throws ClassNotFoundException, ReflectiveOperationException {
        
        Class<?> c = Class.forName(minterClassName);
        this.uriMinter = (UriMinter) c.getConstructor(String.class)
                                        .newInstance(localNamespace);         
    }
    
    /**
     * Get list of input files from the input path
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
        JsonNode inputNode = config.get("input");
        String inputPath = getJsonStringValue(inputNode, "location");
        String inputFormat = getJsonStringValue(inputNode, "format");
        String fileExtension = getJsonStringValue(inputNode, "extension");
        
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
    
    protected void buildConverter(JsonNode config) 
            throws ClassNotFoundException, InstantiationException, 
                IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

        // ObjectMapper mapper = new ObjectMapper();
        
        // JsonNode converterList = config.get("converters"); 
        // TODO Should be an array of strings or a string. For now just handle
        // an array.
        // if string...convert to list
        // else
        
        // TODO Get this to work. Hard-coding as a single converter for now
        // TypeReference ref = new TypeReference<List<Converter>>() {};
        // converters = mapper.readValue(converterList, ref);
       String converter = getJsonStringValue(config, "converter");
       Class<?> converterClass = Class.forName(converter); 
       Constructor<?> constructor = 
               converterClass.getConstructor(this.getClass());
       this.converter = (Converter) constructor.newInstance(this); 
    }
    
    private String getJsonStringValue(JsonNode node, String key) {
        
        JsonNode value = node.get(key);
        if (value == null) {
            throw new RuntimeJsonMappingException("Required value '" + key + 
                    "' not defined in configuration.");
        }
        String stringValue = value.textValue();
        if (stringValue == null) {
            throw new RuntimeJsonMappingException("Required value '" + key + 
                    "' must be a string.");
        }
        return stringValue;
        
    }

}

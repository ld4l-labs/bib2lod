package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

public class Configuration {

    private static final Logger LOGGER = 
            LogManager.getLogger(Configuration.class);

    private String localNamespace;
    private UriMinter uriMinter;
    private List<File> input;
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
  
        OptionsReader configurer = new OptionsReader(args);
        JsonNode config = ((OptionsReader) configurer).getConfig();
        
        LOGGER.debug(config.toString());
        
        JsonNode services = config.get("services");
        
        LOGGER.debug(services.toString());
        
        String localNamespace = getJsonStringValue(config, "localNamespace");
        setLocalNamespace(localNamespace);
        
        makeUriMinter(getJsonStringValue(services, "uriMinter"));
        
        // TODO Add same for other services...

        // TODO Throw error if not defined
        JsonNode inputNode = config.get("input");
        String inputPath = getJsonStringValue(inputNode, "location");
        String inputFormat = getJsonStringValue(inputNode, "format");
        String fileExtension = getJsonStringValue(inputNode, "extension");
        buildInputFileList(inputPath, inputFormat, fileExtension);
        
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
    
    protected void setLocalNamespace(String localNamespace) {
        this.localNamespace = localNamespace;
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
    protected void buildInputFileList(String inputPath, String fileFormat, 
            String fileExtension) throws FileNotFoundException {
        
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

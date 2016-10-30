package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

public abstract class BaseConfiguration implements Configuration {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseConfiguration.class);
    
    protected String localNamespace;
    protected UriMinter uriMinter;
    protected List<File> input;
    // private Reader reader;
    // private Writer writer;
    // private ErrorHandler errorHandler;
    // private Logger logger;
    
    public BaseConfiguration() {
        // TODO Auto-generated constructor stub
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
    
    protected void createUriMinter(String minterClassName) 
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
    

}

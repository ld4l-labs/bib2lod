/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.ConfigurationFromJson.Key;

/**
 * An abstract implementation providing shared methods.
 */
public abstract class BaseConfiguration implements Configuration {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Signals that the content of a configuration value is invalid.  Differs
     * from empty, null, or invalid types, which are handled by JsonUtils
     * exceptions, which are content-neutral. The ConfigurationFromJson object 
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
     * Signals that the specified input source is invalid or non-existent.
     */
    public static class InvalidInputSourceException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected InvalidInputSourceException(String msg) {
            super(msg);
        }
    }

    protected String localNamespace;

    // inputSource and inputFileExtension are only stored for the toString()
    // method.
    protected String inputSource;
    protected String inputFileExtension;
    
    protected List<Reader> input;  
    protected String inputFormat;
    
    protected File outputDirectory; 
    protected String outputFormat;
    
    protected String uriMinter;
    protected String writer;

    protected String converter;
    protected String cleaner;
    protected List<String> reconcilers;

    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getLocalNamespace()
     */
    @Override
    public String getLocalNamespace() {
        return localNamespace;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getInputSource()
     */
    @Override
    public String getInputSource() {
        return inputSource;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getInputFiles()
     */
    @Override
    public List<Reader> getInput() {
        return input;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getInputFormat()
     */
    @Override
    public String getInputFormat() {
        return inputFormat;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getOutputDirectory()
     */
    @Override
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getOutputFormat()
     */
    @Override
    public String getOutputFormat() {
        return outputFormat;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getUriMinter()
     */
    @Override
    public String getUriMinter() {
        return uriMinter;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getWriter()
     */
    @Override
    public String getWriter() {
        return writer;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getCleaner()
     */
    @Override
    public String getCleaner() {
        return writer;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getConverter()
     */
    @Override
    public String getConverter() {
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
    
    /**
     * Sets local namespace. Validates namespace and, if valid, sets it. Else
     * an exception is thrown.
     * @param localNamespace - the local namespace to set
     * 
     */
    protected void setLocalNamespace(String localNamespace) {
        
        // TODO Test for empty/null/missing/invalid value. See issue #14.
        if (isValidLocalNamespace(localNamespace)) {
            this.localNamespace = localNamespace; 
        }
    }
    
    /**
     * Tests for valid local namespace: must be a valid IRI and end in a final
     * slash. Throws exceptions if invalid.
     * @param localNamespace - the string to validate
     * @return true if valid, else throws an exception
     */
    protected static boolean isValidLocalNamespace(String localNamespace) {
    
        // Throws an error if the localNamespace is malformed.
        org.apache.jena.riot.system.IRIResolver.validateIRI(localNamespace);
    
        // Require the final slash, otherwise it could be a web page address
        if (!localNamespace.endsWith("/")) {
            throw new InvalidValueException(Key.LOCAL_NAMESPACE.string, 
                    "Local namespace must end in a forward slash.");
        }
        
        return true;
    }
    
    /**
     * Sets input source from configuration. Throws an
     * exception if the specified source does not exist or is not readable.
     * Builds list of input files from a valid source. 
     * @param source - input source (string)
     * @return void
     */
    protected void buildInputList(String source) {

        buildInputList(source, null);
    }
    
    /**
     * Sets input source from configuration (file or directory). Throws an
     * exception if the specified source does not exist or is not readable.
     * @param source - input source (string)
     * @param extension - input file extension
     * @return void
     */
    protected void buildInputList(String source, String extension) {
        
        this.inputSource = source;
        this.inputFileExtension = extension;        
        this.input = buildInputReaderList();
        
        if (this.input == null) {
            throw new InvalidInputSourceException(source);
        }
    }
    
    // ****** TODO Move all reader methods to a Reader class ******* //
    // Also move the InvalidInputSourceException there
    
    /**
     * Builds the list of input readers. 
     * @return void
     */
    private List<Reader> buildInputReaderList() {
        
        List<Reader> readers = null;

        readers = buildInputReadersFromFiles();

        if (readers == null) {
            return readers;
        }
        
        // Else handle other types of input
  
        return readers;
      
    }
    
    /**
     * Builds the list of input readers from a source on the file system. 
     * Returns null if the source doesn't exist.
     * @return List of readers, or null if the source doesn't exist
     */
    private List<Reader> buildInputReadersFromFiles() {
        
        File source = new File(inputSource);
        
        // Source doesn't exist on the file system
        // TODO How can we differentiate a source that is intended to be a file
        // but doesn't exist, from one that is non-file-based? Perhaps we can't,
        // and the caller will just have to throw the same exception in both
        // cases.
        if (! source.exists()) {
            return null;
        }
        
        if (!source.canRead()) {
            throw new InvalidInputSourceException(
                    "Can't read input source " + inputSource);
        }     
        
        // Create a list of input files
        // TODO Create the list of readers directly, without the intermediate
        // list of files?
        List<File> inputFiles = new ArrayList<File>();

        if (source.isDirectory()) {     
            // TODO Use filters to make sure files are readable, and if an 
            // extension is defined, only with that extension. See issue #16.
            inputFiles = Arrays.asList(source.listFiles());
        } else if (source.isFile()) {
            // Wrap the input file in a List
            inputFiles.add(source);
        }

        // Create a list of readers from the list of input files
        List<Reader> readers = new ArrayList<Reader>();
        for (File file : inputFiles) {
            Path path = null;
            try {
                path = file.toPath();
                readers.add(Files.newBufferedReader(path));
                LOGGER.debug("Adding input file " + path.toString());
            } catch (IOException e) {
                LOGGER.warn(
                        "IOException: can't add input file " + path.toString());
            }
        }
  
        if (readers.isEmpty()) {
            LOGGER.warn("No readable input files found");
            return null;
        }
        return readers;
    }
    
    // ***** End of reader methods to be moved to a Reader class **** //
    
    /**
     * Sets the input format.
     * @param format - the input format
     * @return void
     */
    protected void setInputFormat(String format) {
        // TODO check for valid formats (from a list)?
        // But individual converters will still have to check that the input 
        // format is one of the expected formats, so maybe don't do here.
        this.inputFormat = format;
    }
       
    /**
     * Sets output directory. Throws an exception if: the destination is a file;
     * the destination is unreadable; the destination doesn't exist and cannot
     * be created. Succeeds if the destination is a directory which either 
     * exists or doesn't exist but can be created. Creates the directory if it
     * doesn't exist.
     * @param destination - the output directory 
     * @return void
     */
    protected void setOutputDestination(String destination) {
        
        // TODO:
        // Handle non-file-based output destination (e.g., stdout)
        
        // If destination is a file: throw exception
        // If destination is a directory but unreadable: throw exception
        // If destination doesn't exist and can't be created: throw exception
        // If destination doesn't exist but can be created, create it.
        
        this.outputDirectory = new File(destination);
        // TODO Convert string to directory    
        // Test for nonexistent but can create, nonexistent but can't create,
        // exists but unreadable
    }
    
    /**
     * Sets output format.
     */
    protected void setOutputFormat(String format) {
        // TODO check for valid formats (from a list)?
        // But individual converters will still have to check that the output 
        // format is one of the expected formats, so maybe don't do here.
        this.outputFormat = format;
    }
    
    /**
     * Sets UriMinter.
     * @param uriMinter - name of UriMinter class
     * @return void
     */
    protected void setUriMinter(String uriMinter) {
        this.uriMinter = uriMinter;
    }
    
    /**
     * Sets Writer.
     * @param writer - name of Writer class
     * @return void
     */
    protected void setWriter(String writer) {
        this.writer = writer;
    }
    
    /**
     * Sets Cleaner.
     * @param writer - name of Cleaner class
     * @return void
     */
    protected void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }
    
    /**
     * Sets Converter.
     * @param converter - name of Converter class
     */
    protected void setConverter(String converter) {
        this.converter = converter;
    }
    
    /**
     * Sets Reconcilers.
     * @param reconcilers - array of Reconciler class names
     */
    protected void setReconcilers(String[] reconcilers) {
        this.reconcilers = Arrays.asList(reconcilers);
    }
    
    /**
     * Provide a string representation for logging and debugging. 
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n\nConfiguration values:\n\n");
        sb.append("Local namespace: " + localNamespace + "\n\n");
           
        sb.append("Input source: " + inputSource + "\n\n");

        if (inputFileExtension != null) {
            sb.append("Input file extension: " + inputFileExtension + "\n\n");
        }
        
        sb.append("Input format: " + inputFormat + "\n\n");
        
        try {
            sb.append("Output destination: " + 
                    outputDirectory.getCanonicalPath() + "\n\n");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        sb.append("Output format: " + outputFormat + "\n\n");

        sb.append("Uri minter: " + uriMinter + "\n\n");
        
        sb.append("Writer: " + writer + "\n\n");
        
        sb.append("Converter: " + converter + "\n\n");
        
        sb.append("Cleaner: " + cleaner + "\n\n");

        sb.append("Reconcilers:");
        if (reconcilers.isEmpty()) {
            sb.append(" none\n");
        } else {
            for (String reconciler : reconcilers) {
                sb.append(reconciler + "\n");        
            }
        }
        sb.append("\n");
        
        return sb.toString();       
    }

    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.ConfigurationFromJson.Key;
import org.ld4l.bib2lod.io.InputBuilder;

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
    protected String inputBuilder;
    // TODO Just have the converter create the list of readers, not the configuration
    protected List<BufferedReader> input;  
    protected String inputFormat;  
    protected String inputSource;
    protected String inputFileExtension;
    protected String outputDestination; 
    protected String outputFormat;    
    protected List<String> uriMinters;
    protected String outputWriter;
    protected String outputStream;
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
    
    @Override
    public String getInputSource() {
        return inputSource;
    }
    
    @Override
    public String getInputFileExtension() {
        return inputFileExtension;
    }
    
    @Override
    public String getInputFormat() {
        return inputFormat;
    }
    
    @Override
    public String getInputBuilder() {
        return this.inputBuilder;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getInputFiles()
     */
    @Override
    public List<BufferedReader> getInput() {
        return input;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getOutputDirectory()
     */
    @Override
    public String getOutputDestination() {
        return outputDestination;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getOutputFormat()
     */
    @Override
    public String getOutputFormat() {
        return outputFormat;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getUriMinters()
     */
    @Override
    public List<String> getUriMinters() {
        return uriMinters;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getOutputWriter()
     */
    @Override
    public String getOutputWriter() {
        return outputWriter;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.configuration.Configuration#getCleaner()
     */
    @Override
    public String getCleaner() {
        return cleaner;
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
     * Builds input list. Instantiates an InputBuilder from the configuration,
     * calls its buildInputList() method, and assigns the result to the
     * member variable input.
     * @param builder - the class name of the input builder to invoke
     * @param source - input source (string)
     * @param 
     * @return void
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ParseException 
     * @throws FileNotFoundException 
     * @throws IOException 
     */
    protected void buildInput(String builder, String source) throws
            InstantiationException, IllegalAccessException, 
            ClassNotFoundException, ParseException, FileNotFoundException, 
            IOException {
                  
        // Instantiate builder
        InputBuilder inputBuilder = InputBuilder.instance(this);
        
        // Pass source and extension to builder, get back list of readers
        this.input = inputBuilder.buildInputList(); 
    }
    
    /**
     * Builds input list.
     * @param builder - the class name of the input builder to invoke
     * @param source - input source (string)
     * @param extension - input file extension (for input source on file system)
     * @param 
     * @return void
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws IOException 
     * @throws ParseException 
     */
    protected void buildInput() 
            throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, IOException, ParseException {
        
        // Instantiate builder
        InputBuilder builder = InputBuilder.instance(this);
                
        
        // Pass source and extension to builder, get back list of readers
        this.input = builder.buildInputList(); 
        
    }
    
    protected void setInputBuilder(String inputBuilder) {
        this.inputBuilder = inputBuilder;
    }
    
    protected void setInputSource(String inputSource) {
        this.inputSource = inputSource;
    }
    
    protected void setInputFileExtension(String inputFileExtension) {
        this.inputFileExtension = inputFileExtension;
    }
    
    protected void setInputFormat(String inputFormat) {
        this.inputFormat = inputFormat;
    }
     
    /**
     * Sets output destination. 
     * @param destination - the output directory 
     * @return void
     */
    protected void setOutputDestination(String destination) {
        
        // TODO:
        // Handle non-file-based output destination (e.g., stdout)
        
        this.outputDestination = destination;
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
     * Sets list of class names of UriMinters.
     * @param uriMinter - list of names of UriMinter classes
     * @return void
     */
    protected void setUriMinters(String[] uriMinters) {
        this.uriMinters = Arrays.asList(uriMinters);
    }
    
    /**
     * Sets class name of OutputWriter.
     * @param writer - name of OutputWriter class
     * @return void
     */
    protected void setOutputWriter(String writer) {
        this.outputWriter = writer;
    }
    
    protected void setOutputStream(String outputStream) {
        this.outputStream = outputStream;
    }
    
    /**
     * Sets class name of Cleaner.
     * @param writer - name of Cleaner class
     * @return void
     */
    protected void setCleaner(String cleaner) {
        this.cleaner = cleaner;
    }
    
    /**
     * Sets class name of Converter.
     * @param converter - name of Converter class
     */
    protected void setConverter(String converter) {
        this.converter = converter;
    }
    
    /**
     * Sets list of class names of Reconcilers.
     * @param reconcilers - array of Reconciler class names
     */
    protected void setReconcilers(String[] reconcilers) {
        this.reconcilers = Arrays.asList(reconcilers);
    }
    
}

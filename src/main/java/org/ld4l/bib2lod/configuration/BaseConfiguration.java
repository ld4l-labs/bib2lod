/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uris.UriGetter;

/**
 * An abstract implementation providing shared methods.
 */
public abstract class BaseConfiguration implements Configuration {
    
    /**
     * A problem occurring in setting up the Configuration.
     */
    public static class ConfigurationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConfigurationException(String message) {
            super(message);
        }

        public ConfigurationException(Exception cause) {
            super(cause);
        }
    }
    
    private static final Logger LOGGER = LogManager.getLogger(); 
   

    // TODO Make some of these private if possible
    protected String localNamespace;  
    protected String inputServiceClass;
    protected String inputFormat;  
    protected String inputSource;
    protected String inputFileExtension;
    protected String outputServiceClass;
    protected String outputDestination; 
    protected String outputFormat;    
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
    public String getInputServiceClass() {
        return inputServiceClass;
    }

    @Override
    public String getInputFormat() {
        return inputFormat;
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
    public String getOutputServiceClass() {
        return outputServiceClass;
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
     * Sets local namespace and builds UriGetters to be stored as static
     * variable of UriGetter. These functions are combined into one method
     * because the latter depends on having a local namespace in the 
     * configuration.
     * @param localNamespace
     * @param uriGetters - array of UriGetter classes to instantiate
     * @throws ConfigurationException 
     */
    protected void setUpUriGetters(String localNamespace, String[] uriGetters) 
            throws ConfigurationException {
        
        setLocalNamespace(localNamespace);
        
        try {
            createUriGetters(uriGetters);
        } catch (IllegalArgumentException
                | SecurityException e) {
            throw new ConfigurationException(e);
        }
    }
    
    /**
     * Sets local namespace. Validates namespace and, if valid, sets it. Else
     * an exception is thrown.
     * @param localNamespace - the local namespace to set
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
     * @throws ConfigurationException
     */
    protected static boolean isValidLocalNamespace(String localNamespace) {
    
        // Throws an error if the localNamespace is malformed.
        org.apache.jena.riot.system.IRIResolver.validateIRI(localNamespace);
    
        // Require the final slash, otherwise it could be a web page address
        if (!localNamespace.endsWith("/")) {
            throw new ConfigurationException(
                    "Local namespace must end in a forward slash.");
        }
        
        return true;
    }

    /**
     * Sets list of UriGetter instances.
     * @param uriGetter - array of names of UriGetter classes
     * @return void
     */
    private void createUriGetters(String[] uriGetters)  {
        
       UriGetter.createUriGetters(uriGetters, this);
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
     * Sets class name of Cleaner.
     * @param cleaner - name of Cleaner class
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

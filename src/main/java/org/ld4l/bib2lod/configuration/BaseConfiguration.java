/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.ConfigurationFromJson.Key;
import org.ld4l.bib2lod.uri.UriMinter;

/**
 *
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

    protected String localNamespace;
    protected UriMinter uriMinter;
    protected List<File> input;  // or List<String>?
    protected File output; // Or String
    
    // TODO Convert to list later
    protected List<String> converters;
    protected List<String> reconcilers;
 
    // cleaner, parser, converters
    // protected Reader reader;
    // protected Writer writer;
    // protected ErrorHandler errorHandler;
    // protected Logger logger;
    
    /**
     * 
     */
    public BaseConfiguration() {
        // TODO Auto-generated constructor stub
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
    
    /**
     * Sets local namespace.
     * 
     */
    protected void setLocalNamespace(String localNamespace) {
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
}

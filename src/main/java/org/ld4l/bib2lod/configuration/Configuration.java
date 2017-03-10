/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.BufferedReader;
import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * Provides program configuration values.
 */
public interface Configuration {
    /**
     * Signals a problem with creating or using the Configuration.
     */
    public static class ConfigurationException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public ConfigurationException(String message) {
            super(message);                 
        }

        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConfigurationException(Throwable cause) {
            super(cause);
        }
        
    }
    
    /**
     * Signals that the content of a configuration value is invalid.  Differs
     * from empty, null, or invalid types, which are handled by JsonUtils
     * exceptions, which are content-neutral. The ConfigurationFromJson object 
     * evaluates the contents of the value.
     */
    public static class InvalidValueException extends ConfigurationException {         
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
    public static class InvalidInputSourceException extends ConfigurationException {
        private static final long serialVersionUID = 1L;

        protected InvalidInputSourceException(String msg) {
            super(msg);
        }
    }
    
    /**
     * Factory method
     * @param args
     * @return
     */
    static Configuration instance(String[] args) {
            return Bib2LodObjectFactory.instance().createConfiguration(args);
    }

    /**
     * Gets the configured local namespace.
     * @return the local namespace
     */
    String getLocalNamespace();

    /**
     * Gets the configured list of input readers.
     * @return the list of input readers
     */
    List<BufferedReader> getInput();
    
    /**
     * Gets the configured output destination.
     * @return the output destination
     */
    String getOutputDestination();
      
    /**
     * Gets the configured output format.
     * @return the output format
     */
    String getOutputFormat();
    
    /**
     * Returns the class name of the OutputWriter specified in the 
     * configuration.
     * @return the class name of the OutputWriter
     */
    String getOutputWriter();
    
    /**
     * Gets the class name of the Cleaner specified in the configuration.
     * @return the class name of the cleaner
     */
    String getCleaner();
    
    /**
     * Gets the class name of the Converter specified in the configuration.
     * @return the class name of the converter
     */
    String getConverter();
    
    /**
     * Gets the list of class names of the reconcilers specified in the 
     * configuration.
     * @return the ordered class names of the reconcilers
     */
    List<String> getReconcilers();

}

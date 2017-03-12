/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.ConfigurationFromJson.Key;
import org.ld4l.bib2lod.uris.UriGetter;

/**
 * An abstract implementation providing shared methods.
 */
public abstract class BaseConfiguration implements Configuration {
    
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
     * Sets local namespace and builds UriMinters to be stored as static
     * variable of UriGetter. These functions are comibined into one method
     * because the latter depends on having a local namespace in the 
     * configuration.
     * @param localNamespace
     * @param uriMinters
     * @throws ParseException 
     * @throws IOException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws FileNotFoundException 
     * @throws ClassNotFoundException 
     */
    protected void setUpUriMinters(String localNamespace, String[] uriMinters) 
            throws ClassNotFoundException, FileNotFoundException, 
                InstantiationException, IllegalAccessException, 
                    IllegalArgumentException, InvocationTargetException, 
                        NoSuchMethodException, SecurityException, IOException, 
                            ParseException {
        
        setLocalNamespace(localNamespace);
        createUriMinters(uriMinters);
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
     * Sets list of UriGetter instances.
     * @param uriMinter - list of names of UriGetter classes
     * @return void
     * @throws ParseException 
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws ClassNotFoundException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     */
    private void createUriMinters(String[] uriMinters) throws 
            ClassNotFoundException, FileNotFoundException, IOException, 
                ParseException, InstantiationException, IllegalAccessException, 
                    IllegalArgumentException, InvocationTargetException, 
                        NoSuchMethodException, SecurityException {
        
        UriGetter.createMinters(uriMinters, this);
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

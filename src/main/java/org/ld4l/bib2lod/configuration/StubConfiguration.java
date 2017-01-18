/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;
import java.io.OutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Stub Configuration implementation that hard-codes values without reading from 
 * commandline args or a configuration file.
 */
public class StubConfiguration extends BaseConfiguration {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private static final String LOCAL_NAMESPACE = 
            "http://data.ld4l.org/cornell/";
    private static final String INPUT_BUILDER = 
            "org.ld4l.bib2lod.io.FileInputBuilder";
    private static final String INPUT_SOURCE = 
            "/Users/rjy7/Workspace/bib2lod/src/test/resources/input/102063.min.xml";
    private static final String INPUT_FORMAT = "MARCXML";
    private static final String OUTPUT_DESTINATION = 
            "/Users/rjy7/Workspace/bib2lod/src/test/resources/output/";
    private static final String OUTPUT_FORMAT = "ntriples";
    private static final String[] URI_MINTERS = {
        "org.ld4l.bib2lod.uri.RandomUriMinter"};
    private static final String OUTPUT_WRITER = "org.ld4l.bib2lod.io.FileOutputWriter";
    private static final String CLEANER = 
            "org.ld4l.bib2lod.clean.MarcxmlCleaner";
    private static final String CONVERTER = 
            "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlToLd4lRdf";
    private static final String[] RECONCILERS = {};

    /**
     * Constructor.
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     */              
    public StubConfiguration() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, IOException  {
        
        setLocalNamespace(LOCAL_NAMESPACE);     
        buildInput(INPUT_BUILDER, INPUT_SOURCE, INPUT_FORMAT);     
        setOutputDestination(OUTPUT_DESTINATION);
        setOutputFormat(OUTPUT_FORMAT);      
        setUriMinters(URI_MINTERS);
        setOutputWriter(OUTPUT_WRITER);   
        setCleaner(CLEANER);
        setConverter(CONVERTER);        
        setReconcilers(RECONCILERS);

    }

}
                     
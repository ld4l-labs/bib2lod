/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
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
    private static final String INPUT_SERVICE_CLASS = "org.ld4l.bib2lod.io.FileInputService";
    private static final String INPUT_SOURCE = 
            "/Users/rjy7/projects/bib2lod/doc/sample-conversions/marcxml-to-ld4l/102063.min.xml";
    private static final String INPUT_FILE_EXTENSION = "xml";
    private static final String INPUT_FORMAT = "MARCXML";
    private static final String OUTPUT_DESTINATION = 
            "/Users/rjy7/projects/bib2lod/output/";
    private static final String OUTPUT_FORMAT = "ntriples";
    private static final String[] URI_MINTERS = {
        "org.ld4l.bib2lod.uris.RandomUriGetter"};
    private static final String OUTPUT_SERVICE_CLASS = "org.ld4l.bib2lod.io.FileOutputService";
    private static final String CLEANER = 
            "org.ld4l.bib2lod.cleaning.MarcxmlCleaner";
    private static final String CONVERTER = 
            "org.ld4l.bib2lod.conversion.to_rdf.ld4l.MarcxmlToRdf";
    private static final String[] RECONCILERS = {};

    /**
     * Constructor.
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     * @throws IOException 
     * @throws ParseException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */              
    public StubConfiguration() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, IOException, 
                ParseException, IllegalArgumentException, 
                    InvocationTargetException, NoSuchMethodException, 
                        SecurityException  {
   

        // NB Some orderings are crucial. e.g., need localNamespace before 
        // createUriMinters. Then do in one method - set up UriGetter
        setUpUriMinters(LOCAL_NAMESPACE, URI_MINTERS);

        this.inputServiceClass = INPUT_SERVICE_CLASS;
        this.inputSource = INPUT_SOURCE;
        this.inputFileExtension = INPUT_FILE_EXTENSION;
        this.inputFormat = INPUT_FORMAT;
        this.outputServiceClass = OUTPUT_SERVICE_CLASS;
        this.outputDestination = OUTPUT_DESTINATION;
        this.outputFormat = OUTPUT_FORMAT;      

        setCleaner(CLEANER);
        setConverter(CONVERTER);        
        setReconcilers(RECONCILERS);   

    }


}
                     
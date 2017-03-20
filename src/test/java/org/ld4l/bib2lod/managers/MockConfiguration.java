/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import org.ld4l.bib2lod.configuration.BaseConfiguration;

/**
 * Mocking infrastructure for SimpleManager tests
 */
public class MockConfiguration extends BaseConfiguration {
    
    private static final String INPUT_SERVICE_CLASS = 
            "org.ld4l.bib2lod.managers.MockInputService";
    private static final String OUTPUT_SERVICE_CLASS = 
            "org.ld4l.bib2lod.managers.MockOutputService";
    private static final String CONVERTER_CLASS = 
            "org.ld4l.bib2lod.managers.MockConverter";
//    private static final String OUTPUT_DESTINATION = 
//            "/Users/rjy7/projects/bib2lod/output/";
//    private static final String OUTPUT_FORMAT = "N-TRIPLE";
    
    /**
     * Constructor
     */
    public MockConfiguration(String[] args) {
        this.inputServiceClass = INPUT_SERVICE_CLASS;
        this.outputServiceClass = OUTPUT_SERVICE_CLASS;
        this.converter = CONVERTER_CLASS;
//        this.outputDestination = OUTPUT_DESTINATION;
//        this.outputFormat = OUTPUT_FORMAT;  
    }

}

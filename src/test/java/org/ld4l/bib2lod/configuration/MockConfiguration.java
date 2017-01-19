/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.ParseException;

import com.fasterxml.jackson.databind.JsonNode;

/**
 *
 */
public class MockConfiguration extends BaseConfiguration {

    /**
     * 
     */
    public MockConfiguration(String[] args) 
        throws ClassNotFoundException, FileNotFoundException, IOException,
        ParseException {
            
        JsonNode config = OptionsReader.instance(args).configure();
        
        setLocalNamespace(config);
        
        //buildInputList(config);
    }

    protected void setLocalNamespace(JsonNode config) {
        
        super.setLocalNamespace(config.get("local_namespace").textValue());                       
    }
    
}

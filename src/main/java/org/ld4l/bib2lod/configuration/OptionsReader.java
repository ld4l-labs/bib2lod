/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Return the options as obtained from the command line and/or a config file.
 */
public interface OptionsReader {
    
    /**
     * Factory method
     */
    static OptionsReader instance(String[] args) {
        return Bib2LodObjectFactory.instance().createOptionsReader(args);
    }

    JsonNode configure() throws IOException, ParseException;
}

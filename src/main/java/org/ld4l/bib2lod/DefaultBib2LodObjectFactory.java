/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.DefaultConfiguration;
import org.ld4l.bib2lod.configuration.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.OptionsReader;

/**
 * A simple implementation.
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {

    @Override
    public Configuration createConfiguration(String[] args)
            throws ClassNotFoundException, FileNotFoundException, IOException,
            ParseException {
        return new DefaultConfiguration(args);
    }
    
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return new JsonOptionsReader(args);
    }

}

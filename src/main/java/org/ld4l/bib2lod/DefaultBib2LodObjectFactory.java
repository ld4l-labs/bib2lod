/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.cleaning.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.configuration.StubConfiguration;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.MarcxmlToRdf;

/**
 * A simple implementation.
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(java.lang.String[])
     */
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return new JsonOptionsReader(args);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(java.lang.String[])
     */
    @Override
    public Configuration createConfiguration(String[] args) {
        // return new ConfigurationFromJson(args);
        try {
            return new StubConfiguration();
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException | IOException | ParseException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter(org.ld4l.bib2lod.configuration.Converter)
     */
    @Override
    public Converter createConverter(Configuration configuration) {
        return new MarcxmlToRdf(configuration);
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner(org.ld4l.bib2lod.cleaning.Cleaner)
     */
    @Override
    public Cleaner createCleaner(Configuration configuration) {
        return new MarcxmlCleaner(configuration);
    }
 
 // Removing this because the converter knows what type of parser it needs and
 // must ask for it specifically.
//    @Override
//    public Parser createParser(Configuration configuration) {
//        return new MarcxmlParser(configuration);
//    }
//    
//    @Override
//    public MarcxmlParser createMarcxmlParser(Configuration configuration) {
//        return new MarcxmlParser(configuration);
//    }
    


    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createInputBuilder(org.ld4l.bib2lod.configuration.Configuration)
     */
//    @Override
//    public InputBuilder createInputBuilder(Configuration configuration) {
//        return new FileInputBuilder(configuration);
//    }

}

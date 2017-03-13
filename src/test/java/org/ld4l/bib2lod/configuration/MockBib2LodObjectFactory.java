/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.IOException;
import java.lang.reflect.Field;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.uris.UriGetter;

/**
 * When created, sets itself as a wrapper for the default factory instance.
 * 
 * When client code requests an object, checks to see whether a mock has been
 * supplied. If not, delegates to the default factory instance.
 */
public class MockBib2LodObjectFactory extends Bib2LodObjectFactory {

    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------

    private final Bib2LodObjectFactory defaultFactory;
    private OptionsReader optionsReader;
    private Configuration configuration;

    public MockBib2LodObjectFactory() {
        try {
            Field field = Bib2LodObjectFactory.class
                    .getDeclaredField("instance");
            field.setAccessible(true);
            this.defaultFactory = (Bib2LodObjectFactory) field.get(null);
            field.set(null, this);
        } catch (NoSuchFieldException | SecurityException
                | IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(
                    "Failed to initialize the MockBib2LodObjectFactory", e);
        }
    }

    public void setOptionsReader(OptionsReader optionsReader) {
        this.optionsReader = optionsReader;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }
    
    // ----------------------------------------------------------------------
    // Mocked methods
    // ----------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return (optionsReader != null) ? optionsReader
                : defaultFactory.createOptionsReader(args);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Configuration createConfiguration(String[] args) {
        // Currently the default configuration is StubConfiguration, which 
        // doesn't work to test the abstract BaseConfiguration because it  
        // doesn't use a JsonNode, and isn't useful to build tests on because it
        // is only temporary. 
        try {
            return new MockConfiguration(args);
        } catch (IOException | ParseException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Cleaner createCleaner(Configuration configuration) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Converter createConverter(Configuration configuration) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createUriGetter(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public UriGetter createUriGetter(String className, Configuration configuration) {
        // TODO Auto-generated method stub
        return null;
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.lang.reflect.Field;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
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

    // ----------------------------------------------------------------------
    // Mocked methods
    // ----------------------------------------------------------------------

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Configuration createConfiguration(String[] args) {
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner()
     */
    @Override
    public Cleaner createCleaner() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter()
     */
    @Override
    public Converter createConverter() {
        try {
            return new MockConverter();
        } catch (IllegalArgumentException | SecurityException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
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

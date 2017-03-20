/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;
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
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(java.lang.String[])
     */
    @Override
    public Configuration createConfiguration(String[] args) throws Bib2LodObjectFactoryException {
        try {
            return new MockConfiguration(args);
        } catch (IllegalArgumentException | SecurityException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Cleaner createCleaner() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public Converter createConverter() {
        try {
            return new MockConverter(configuration);
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
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createInputService(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public InputService createInputService(Configuration configuration) {
        try {
            return (InputService) Class
                    .forName(configuration.getInputServiceClass())
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOutputService(org.ld4l.bib2lod.configuration.Configuration)
     */
    @Override
    public OutputService createOutputService(Configuration configuration) {
        try {
            return (OutputService) Class
                    .forName(configuration.getOutputServiceClass())
                    .getConstructor(Configuration.class)
                    .newInstance(configuration);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException
                | ClassNotFoundException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

}

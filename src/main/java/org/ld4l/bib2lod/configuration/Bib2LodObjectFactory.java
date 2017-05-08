/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * This base class holds the factory instance which others will use.
 */
public abstract class Bib2LodObjectFactory {
    private static Bib2LodObjectFactory instance;

    /**
     * Set the factory instance. May be called only once.
     */
    public static void setFactoryInstance(Bib2LodObjectFactory factory) {
        if (instance == null) {
            instance = factory;
        } else {
            throw new IllegalStateException(
                    "Bib2LodObjectFactory instance has already been set");
        }
    }

    /**
     * Get the factory instance. Never returns null.
     */
    public static Bib2LodObjectFactory getFactory() {
        if (instance == null) {
            throw new IllegalStateException(
                    "Bib2LodObjectFactory instance has not been set");
        } else {
            return instance;
        }
    }
    

    /**
     * Return the first instance that was created for this interface. Never
     * returns null.
     * 
     * @throws ConfigurationException
     *             if no instances are found.
     */
    public abstract <T> T instanceForInterface(Class<T> interfaze);

    /**
     * Return the instances that were created for this interface. Never returns
     * empty or null.
     * 
     * @throws ConfigurationException
     *             if no instances are found.
     */
    public abstract <T> List<T> instancesForInterface(Class<T> interfaze);

}

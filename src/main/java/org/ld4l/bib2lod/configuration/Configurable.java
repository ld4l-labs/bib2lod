/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * Identifies a class that can be instantiated by the object factory, and will
 * be provided with the appropriate portion of the configuration tree.
 */
public interface Configurable {
    /**
     * Called by the object factory, immediately after creating the instance.
     * 
     * @throws ConfigurationException
     */
    void configure(Configuration config);
}

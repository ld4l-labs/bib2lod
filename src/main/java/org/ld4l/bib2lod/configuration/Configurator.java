/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

/**
 * TODO
 */
public interface Configurator {
    /**
     * Returns an immutable configuration tree for the entire application.
     */
    Configuration getTopLevelConfiguration();
}

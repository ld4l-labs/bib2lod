/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * Abstract converter that takes MARCXML input.
 */
public abstract class FromMarcxml extends FromXml {
    
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor
     * @param configuration
     */
    public FromMarcxml(Configuration configuration) {
        super(configuration);
    }
    

}

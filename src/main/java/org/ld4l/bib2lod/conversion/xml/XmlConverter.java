/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.BaseConverter;

/**
 * Abstract converter that takes XML input.
 */
// TODO Figure out if this class is needed.
public abstract class XmlConverter extends BaseConverter {
    protected Configuration configuration;
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Override
    public void configure(Configuration c) {
        configuration = c;
    }
    
}

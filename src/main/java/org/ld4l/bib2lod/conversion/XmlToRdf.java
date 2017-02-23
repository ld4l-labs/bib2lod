/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * Abstract converter that takes XML input.
 */
public abstract class XmlToRdf extends BaseConverter {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Constructor
     * @param configuration
     */
    public XmlToRdf(Configuration configuration) {
        super(configuration);
    }

    // See if this is needed
    // public abstract String getRecordTagName();
    


}

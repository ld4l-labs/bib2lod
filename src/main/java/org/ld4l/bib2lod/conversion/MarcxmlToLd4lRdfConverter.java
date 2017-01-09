/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An implementation that converts MARCXML to LD4L RDF.
 */
public class MarcxmlToLd4lRdfConverter extends BaseConverter {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public MarcxmlToLd4lRdfConverter(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */
    @Override
    public void convert() {
        // TODO Auto-generated method stub
        
    }

}

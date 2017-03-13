/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.parsing.MarcxmlParser;

/**
 * An implementation that converts MARCXML to LD4L RDF
 */
public class MarcxmlToRdf extends XmlToRdf {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Class<?> PARSER_CLASS = MarcxmlParser.class;
    
    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public MarcxmlToRdf(Configuration configuration) {
        super(configuration);
    }
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.BaseConverter#getParserClass()
     */
    @Override
    public Class<?> getParserClass() {
        return PARSER_CLASS;
    }
  
}

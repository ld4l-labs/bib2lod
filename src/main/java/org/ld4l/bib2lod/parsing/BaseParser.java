/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.record.Record;

/**
 *
 */
public abstract class BaseParser implements Parser {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected Configuration configuration;
    
    /**
     * Constructor
     * @param configuration - the program Configuration
     */
    public BaseParser(Configuration configuration) {
        this.configuration = configuration;
    }
    
    /**
     * Returns true iff the specified record is valid.
     */
    public boolean isValidRecord(Record record) {
        return record.isValid();
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.Reader;
import java.io.Writer;

import org.apache.commons.io.LineIterator;
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
    public StringBuffer convert(Reader reader) {
        
        // Stub - just read input into buffer and return
        StringBuffer buffer = new StringBuffer();
        LineIterator lines = new LineIterator(reader);
        String lineSeparator = System.getProperty("line.separator");
        while (lines.hasNext()) {
            String line = lines.nextLine();
            LOGGER.debug(line);
            buffer.append(lines + lineSeparator);
        }
        return buffer;
    }

}

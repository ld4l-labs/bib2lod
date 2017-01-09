/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An object that orchestrates the conversion of an input stream containing one
 * or more records.
 */
public interface Converter {

    /**
     * Factory method
     */
    static Converter instance(Configuration configuration) {
        return Bib2LodObjectFactory.instance().createConverter(configuration);
    }
    
    /**
     * Converts input and writes to output destination specified in
     * configuration.
     */
    // TODO This will take a file as input - but should be a stream/reader
    // rather than a file.
    public void convert();
}

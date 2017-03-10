/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 *
 */
public interface OutputWriter {
    
    /**
     * Factory method
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     */
    static OutputWriter instance(Configuration configuration) {
        return Bib2LodObjectFactory.instance().createOutputWriter(
                configuration);
    }

    /**
     * Writes the specified output.
     * @param output - the StringBuffer to write
     * @return void
     * @throws FileNotFoundException 
     */
    public void write(StringBuffer output) throws FileNotFoundException;
    
    
}

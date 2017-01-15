/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
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
    static OutputWriter instance(Configuration configuration) 
            throws ClassNotFoundException,
            FileNotFoundException, IOException, ParseException, 
            InstantiationException, IllegalAccessException, 
            NoSuchMethodException, SecurityException, 
            IllegalArgumentException, InvocationTargetException {
        
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

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An object that orchestrates the conversion of an input reader containing one
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
     * Converts input 
     * @param reader - the reader containing the input
     * @throws FileNotFoundException 
     * @throws ParseException 
     * @throws IOException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws ClassNotFoundException 
     */
    public void convert(Reader reader) throws FileNotFoundException, 
            ClassNotFoundException, InstantiationException, 
            IllegalAccessException, NoSuchMethodException, SecurityException, 
            IllegalArgumentException, InvocationTargetException, IOException, 
            ParseException;
    
}

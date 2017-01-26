/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity.EntityInstantiationException;

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
     * Converts input to RDF.
     * @param reader
     * @param outputStream 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws EntityInstantiationException 
     */
    public void convert(Reader reader, OutputStream outputStream) throws 
            InstantiationException, 
                IllegalAccessException, ClassNotFoundException, 
                    EntityInstantiationException, IllegalArgumentException, 
                        InvocationTargetException, NoSuchMethodException, 
                            SecurityException;

}

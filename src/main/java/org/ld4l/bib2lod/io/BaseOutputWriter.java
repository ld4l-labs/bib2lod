/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.ld4l.bib2lod.configuration.Configuration;

/**
 * An abstract implementation.
 */
public abstract class BaseOutputWriter implements OutputWriter {
    
    Configuration configuration;

    /**
     * Constructor.
     */
    public BaseOutputWriter(Configuration configuration) {
        this.configuration = configuration;
    }

}

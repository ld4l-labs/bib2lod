/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.ld4l.bib2lod.configuration.Configuration;

/**
 * Writes output to a file.
 */
public class FileOutputWriter extends BaseOutputWriter {
    
    private static int count;

    /**
     * Constructor
     * @param configuration
     */
    public FileOutputWriter(Configuration configuration) {
        super(configuration);
        count = 0;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.io.OutputWriter#write()
     */
    @Override
    public void write(StringBuffer buffer) throws FileNotFoundException {
        
        // If destination is a file: throw exception
        // If destination is a directory but unreadable: throw exception
        // If destination doesn't exist and can't be created: throw exception
        // If destination doesn't exist but can be created, create it.

        // TODO Convert string to directory    
        // Test for nonexistent but can create, nonexistent but can't create,
        // exists but unreadable
        // For now we assume everything is correct
       
        // Temporary: increment counter to create a unique filename.
        // TODO Ideally create output filename from input filename, but how???
        count++;
      File file = new File(
              configuration.getOutputDestination(), "output-" + count);
      OutputStream outputStream = new FileOutputStream(file);
      PrintStream printStream = new PrintStream(outputStream);
      printStream.print(buffer.toString());
      printStream.close();        
    }

}

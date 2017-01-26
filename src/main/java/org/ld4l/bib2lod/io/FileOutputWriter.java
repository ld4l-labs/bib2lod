/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
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
        print(outputStream, buffer);
    }

    /**
     * Writes a Jena model to a file.
     * @param model - the model
     * @param file - the file to write to
     */
    // TODO Consider: this pertains to writing but also to RDF conversion. Do
    // we want a class FileToRdfOutputWriter? We don't want to spawn a new
    // class for every combination. This would be the place for a decorator.
    public void writeModel(Model model, File file) {
        FileOutputStream outStream;
        try {
            outStream = new FileOutputStream(file);
            // TODO Make RDF format configurable
            RDFDataMgr.write(outStream, model, RDFFormat.NTRIPLES);           
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }
    
}

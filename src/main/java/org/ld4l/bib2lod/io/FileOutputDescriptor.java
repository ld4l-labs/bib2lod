/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.jena.rdf.model.Model;
import org.ld4l.bib2lod.io.FileOutputService.Format;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;

/**
 * A wrapper for a file-based output item.
 */
public class FileOutputDescriptor implements OutputDescriptor {
    private final File file;
    private final Format format;
    private FileOutputStream output;

    public FileOutputDescriptor(Format format, File destination,
            String inputName) throws IOException {
        this.format = format;
        String filename = replaceExtension(inputName, format.getExtension());
        this.file = Paths.get(destination.getAbsolutePath(), filename).toFile();
        this.output = new FileOutputStream(file);
    }

    private String replaceExtension(String name, String newExtension) {
        int periodHere = name.lastIndexOf('.');
        if (periodHere == -1) {
            return name + "." + newExtension;
        } else {
            return name.substring(0, periodHere + 1) + newExtension;
        }
    }

    @Override
    synchronized public void writeModel(Model model)
            throws IOException, OutputServiceException {
        if (output == null) {
            throw new OutputServiceException(
                    "Attempting to write after close.");
        }
        model.write(output, format.getLanguage());
    }

    @Override
    synchronized public void close()
            throws OutputServiceException, IOException {
        if (output != null) {
            output.close();
            output = null;
        }
    }
}

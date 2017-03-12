/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;

/**
 * InputService that takes its data from a file or a directory of files.
 * 
 * configuration.getInputSource() will provide the path to either a file or a
 * directory of files.
 * 
 * If a file, then that file is used as input.
 * 
 * If a directory, then the files in that directory (not recursive) are used as
 * input. Any file in the directory is not readable, or does not match a
 * required extension, is ignored.
 */
public class FileInputService implements InputService {
    /**
     * Restrict the list of files from the input directory.
     */
    private static class InputFileFilter implements FileFilter {
        private final String extension;

        public InputFileFilter(String ext) {
            this.extension = ext == null ? "" : "." + ext;
        }

        @Override
        public boolean accept(File file) {
            return file.isFile() && file.canRead()
                    && file.getName().endsWith(extension);
        }

    }

    private final List<InputDescriptor> descriptor;

    protected FileInputService(Configuration configuration) throws IOException {
        File source = new File(configuration.getInputSource());

        if (!source.exists()) {
            throw new IOException("Input source " + source + " doesn't exist.");
        }

        if (!source.canRead()) {
            throw new IOException("Can't read input source " + source);
        }

        if (source.isDirectory()) {
            FileFilter filter = new InputFileFilter(
                    configuration.getInputFileExtension());
            this.descriptor = wrapFilesInDescriptors(source.listFiles(filter));
        } else {
            this.descriptor = wrapFilesInDescriptors(new File[] { source });
        }
    }

    private List<InputDescriptor> wrapFilesInDescriptors(File[] files) {
        List<FileInputDescriptor> list = new ArrayList<>();
        for (File file : files) {
            list.add(new FileInputDescriptor(file));
        }
        return Collections.unmodifiableList(list);
    }

    /**
     * Supply wrapped input files.
     */
    @Override
    public Iterable<InputDescriptor> getDescriptors() {
        return descriptor;
    }

}

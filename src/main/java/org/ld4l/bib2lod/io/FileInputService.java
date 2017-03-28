/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;

/**
 * InputService that takes its data from a file or a directory of files.
 * 
 * The "source" configuration attribute provides the path to either a file or a
 * directory of files.
 * 
 * If a file, then that file is used as input.
 * 
 * If a directory, then the files in that directory (not recursive) are used as
 * input. Any file in the directory that is not readable, or does not match a
 * required extension, is ignored.
 */
public class FileInputService implements InputService {
    private List<InputDescriptor> descriptor;

    @Override
    public void configure(Configuration config) {
        File source = new File(config.getAttribute("source"));

        if (!source.exists()) {
            throw new ConfigurationException(
                    "Input source " + source + " doesn't exist.");
        }

        if (!source.canRead()) {
            throw new ConfigurationException(
                    "Can't read input source " + source);
        }

        if (source.isDirectory()) {
            FileFilter filter = new InputFileFilter(
                    config.getAttribute("extension"));
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
}

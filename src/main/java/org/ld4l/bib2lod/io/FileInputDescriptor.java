/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;

class FileInputDescriptor implements InputDescriptor {
    /**
     * Information about this particular input.
     */
    private static class FileInputMetadata implements InputMetadata {
        private final String name;

        public FileInputMetadata(File file) {
            this.name = file.getName();
        }

        @Override
        public String getName() {
            return name;
        }

    }

    private final File file;
    private InputStream inputStream;

    public FileInputDescriptor(File file) {
        this.file = file;
    }

    @Override
    public InputMetadata getMetadata() {
        return new FileInputMetadata(file);
    }

    @Override
    public synchronized InputStream getInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = new FileInputStream(file);
        }
        return inputStream;
    }

    @Override
    public synchronized void close() throws IOException {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }
    }

}
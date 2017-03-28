/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;
import org.ld4l.bib2lod.io.InputService.InputMetadata;

/**
 * Produces data streams for output, as requested.
 */
public interface OutputService extends Configurable {
    /**
     * Indicates a problem in the operation of the OutputService.
     */
    public static class OutputServiceException extends Exception {
        private static final long serialVersionUID = 1L;

        public OutputServiceException(String message, Throwable cause) {
            super(message, cause);
        }

        public OutputServiceException(String message) {
            super(message);
        }

        public OutputServiceException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * An individual destination for output data.
     */
    public static interface OutputDescriptor extends AutoCloseable {
        public void writeModel(Model model)
                throws IOException, OutputServiceException;

        @Override
        public void close() throws IOException, OutputServiceException;
    }

    /**
     * Factory method
     */
    static OutputService instance() {
        return Bib2LodObjectFactory.getFactory()
                .instanceForClass(OutputService.class);
    }

    /**
     * Creates a wrapper for an output file.
     */
    OutputDescriptor openSink(InputMetadata metadata)
            throws OutputServiceException, IOException;

}

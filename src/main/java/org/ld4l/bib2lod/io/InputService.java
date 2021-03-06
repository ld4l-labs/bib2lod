/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.io;

import java.io.IOException;
import java.io.InputStream;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;

/**
 * A collection of data streams for input.
 */
public interface InputService extends Configurable {
    /**
     * Indicates a problem in the operation of the InputService.
     */
    public static class InputServiceException extends Exception {
        private static final long serialVersionUID = 1L;

        public InputServiceException(String message, Throwable cause) {
            super(message, cause);
        }

        public InputServiceException(String message) {
            super(message);
        }

        public InputServiceException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * An individual stream of input data.
     */
    public static interface InputDescriptor extends AutoCloseable {
        InputMetadata getMetadata();

        InputStream getInputStream() throws IOException;

        @Override
        public void close() throws InputServiceException, IOException;
    }

    public static interface InputMetadata {
        String getName();
    }

    /**
     * Factory method
     */
    static InputService instance() {
        return Bib2LodObjectFactory.getFactory()
                .instanceForInterface(InputService.class);
    }

    /**
     * @return
     */
    Iterable<InputDescriptor> getDescriptors();

}

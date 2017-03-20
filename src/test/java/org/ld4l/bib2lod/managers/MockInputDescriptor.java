/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;

/**
 * Test infrastructure for BaseConverter tests
 */
public class MockInputDescriptor implements InputDescriptor {
    
    private final String inputString;
    private InputStream inputStream;
    
    /**
     * Constructor
     */
    public MockInputDescriptor(String input) {
        super();
        this.inputString = input;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.io.InputService.InputDescriptor#getMetadata()
     */
    @Override
    public InputMetadata getMetadata() {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.io.InputService.InputDescriptor#getInputStream()
     */
    @Override
    public synchronized InputStream getInputStream() throws IOException {
        if (inputStream == null) {
            inputStream = IOUtils.toInputStream(inputString, "UTF-8");
        }
        return inputStream;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.io.InputService.InputDescriptor#close()
     */
    @Override
    public synchronized void close() throws InputServiceException, IOException {
        if (inputStream != null) {
            inputStream.close();
            inputStream = null;
        }      
    }

}

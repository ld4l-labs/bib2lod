/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.OutputService;

/**
 *
 */
public class MockOutputService implements OutputService {
    
    /**
     * Constructor
     */
    public MockOutputService(Configuration config) {
        
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.io.OutputService#openSink(org.ld4l.bib2lod.io.InputService.InputMetadata)
     */
    @Override
    public OutputDescriptor openSink(InputMetadata metadata)
            throws OutputServiceException, IOException {
        // TODO Auto-generated method stub
        return null;
    }

}

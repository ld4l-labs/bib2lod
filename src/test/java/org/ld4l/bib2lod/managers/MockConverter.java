/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;

/**
 * Testing infrastructure
 */
public class MockConverter extends BaseConverter {
    
    private static int inputCount;
    private static int outputCount;
    private List<InputDescriptor> outputs;

    /**
     * Constructor
     */
    public MockConverter(Configuration configuration) {
        super(configuration);
        inputCount = 0;
        outputCount = 0;
        outputs = new ArrayList<InputDescriptor>();
    }
    
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) 
            throws ConverterException {
        inputCount++;
        if (inputCount == 2) {
            throw new ConverterException("skip this input");
        }
        outputs.add(input);
        outputCount++;
    }
    
    public int getOutputCount() {
        // This doesn't work - it's empty when SimpleManagerTest calls it
        int o = outputs.size();
        return outputCount;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.BaseConverter#getParserClass()
     */
    @Override
    protected Class<?> getParserClass() {
        return null;
    }

}

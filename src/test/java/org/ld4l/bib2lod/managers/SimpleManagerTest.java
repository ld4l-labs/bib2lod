/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class SimpleManager.
 */
public class SimpleManagerTest extends AbstractTestClass {
    
    private Bib2LodObjectFactory factory;
    private Configuration configuration;
    private Converter converter;
    private InputService inputService;
    private OutputService outputService;

    @Before
    public void setup() throws IOException {
        factory = new MockBib2LodObjectFactory();
        configuration = Configuration.instance(new String[0]);
//        inputService = InputService.instance(configuration);
//        outputService = OutputService.instance(configuration);
//        converter = Converter.instance(configuration); 
    }

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Ignore
    @Test
    public void converterError_IgnoresInput() {
        SimpleManager.convert(configuration);
        int inputsProcessed = ((MockConverter) converter).getOutputCount();
        Assert.assertEquals(2, inputsProcessed);
        
    }
}

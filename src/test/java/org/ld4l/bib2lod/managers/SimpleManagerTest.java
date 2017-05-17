/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.managers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.MockBib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class SimpleManager.
 */

/*
 * Test plan:
 * error in converting input is ignored - go to next input
 * error in writing output - go to next input
 */
public class SimpleManagerTest extends AbstractTestClass {
    
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ---------------------------------------------------------------------- 
    
    public static class MockConverter implements Converter {
        
        private int inputCount;
        private int outputCount;
        // private List<InputDescriptor> outputs;

        public MockConverter() {
            inputCount = 0;
            outputCount = 0;
            // outputs = new ArrayList<InputDescriptor>();
        }
       
        
       @Override
        public void convert(InputDescriptor input, OutputDescriptor output) 
                throws ConverterException {
            inputCount++;
            if (inputCount == 2) {
                throw new ConverterException("Skip this input");
            }
            // outputs.add(input);
            outputCount++;
        }
        
        public int getOutputCount() {
            // return outputs.size();
            return outputCount;
        }
    }

    public static class MockInputDescriptor implements InputDescriptor {
        
        private final String inputString;

        public MockInputDescriptor(String input) {
            this.inputString = input;
        }

        @Override
        public InputMetadata getMetadata() {
            return null;
        }

        @Override
        public synchronized InputStream getInputStream() throws IOException {
            throw new RuntimeException("Method not implemented.");  
        }

        @Override
        public synchronized void close() throws IOException { }

    }
    
    public static class MockInputService implements InputService {
        
        private static final String INPUT_1 = "input 1";
        private static final String INPUT_2 = "input 2";
        private static final String INPUT_3 = "input 3";
        
        private final List<InputDescriptor> descriptor;

        public MockInputService() {
            String[] inputs = {INPUT_1, INPUT_2, INPUT_3};
            this.descriptor = wrapStringsInDescriptors(inputs);
        }

        @Override
        public void configure(Configuration config) {
            // Nothing to do
        }

        private List<InputDescriptor> wrapStringsInDescriptors(String[] strings) {
            List<InputDescriptor> list = new ArrayList<InputDescriptor>();
            for (String string : strings) {
                list.add(new MockInputDescriptor(string));
            }
            return Collections.unmodifiableList(list);
        }

        @Override
        public Iterable<InputDescriptor> getDescriptors() {
            return descriptor;
        }
    }
    
    public static class MockOutputDescriptor implements OutputDescriptor {

        @Override
        public void writeModel(Model model)
                throws IOException, OutputServiceException {
            throw new RuntimeException("Method not implemented.");       
        }

        @Override
        public void close() throws IOException, OutputServiceException { }           
    }

    public static class MockOutputService implements OutputService {

        @Override
        public void configure(Configuration config) {
            // Nothing to do
        }

        @Override
        public OutputDescriptor openSink(InputMetadata metadata)
                throws OutputServiceException, IOException {
            return new MockOutputDescriptor();
        }
    }
    
    private MockBib2LodObjectFactory factory;

    @Before
    public void setUp() {
        factory = new MockBib2LodObjectFactory();
        factory.addInstance(Converter.class, new MockConverter());
        factory.addInstance(InputService.class, new MockInputService());
        factory.addInstance(OutputService.class, new MockOutputService());
        Bib2LodObjectFactory.setFactoryInstance(factory);
  
        // Suppress output when SimpleManager throws an exception.
        suppressSysout();
        suppressSyserr();
    }

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Ignore
    @Test
    public void converterError_IgnoresInput() {
//        SimpleManager.convert();
        MockConverter converter = (MockConverter) factory.instanceForInterface(Converter.class);
        Assert.assertEquals(2, converter.getOutputCount());     
    }
}

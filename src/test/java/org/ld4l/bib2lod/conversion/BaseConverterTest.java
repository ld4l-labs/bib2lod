/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.io.InputService;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.io.OutputService;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.parsing.BaseParser;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.records.BaseRecord;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;

/**
 * Tests abstract class BaseConverter.
 */
public class BaseConverterTest extends AbstractTestClass {
 
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------

    /**
     * A concrete implementation to test abstract class BaseConverter.
     */
    public static class MockConverter_IgnoresSingleInputConversionException extends BaseConverter {

        private int inputCount;
        private int outputCount;

        public MockConverter_IgnoresSingleInputConversionException() {
            inputCount = 0;
            outputCount = 0;
        }
       
        @Override
        public void convert(InputDescriptor input, OutputDescriptor output) 
                throws ConverterException {
            inputCount++;
            if (inputCount == 2) {
                throw new ConverterException("Skip this input");
            }
            outputCount++;
        }
        
        public int getOutputCount() {
            return outputCount;
        }
      
        @Override
        protected Entity buildEntity(Record record)
                throws EntityBuilderException {
            throw new RuntimeException("Method not yet implemented.");
        }        
    }

    /**
     * A concrete implementation to test abstract class BaseConverter.
     */
    public static class MockConverter_ThrowsRecordConversionException extends BaseConverter {
        
        @Override 
        public Model convertRecord(Record record) throws RecordConversionException {
            throw new RecordConversionException("Error");
        }

        @Override
        protected Entity buildEntity(Record record)
                throws EntityBuilderException {
            throw new RuntimeException("Method not yet implemented.");
        }
    }

    /**
     * A concrete implementation needed to test abstract class BaseConverter.
     */
    public static class MockConverter_ReturnsEmptyModel extends BaseConverter {
        
        @Override 
        public Model convertRecord(Record record) throws RecordConversionException {
            return ModelFactory.createDefaultModel();
        }

        @Override
        protected Entity buildEntity(Record record)
                throws EntityBuilderException {
            throw new RuntimeException("Method not yet implemented.");
        }
    }
    
    public static class MockParser_ReturnsNullRecordList extends BaseParser {

        @Override
        public List<Record> parse(InputDescriptor input)
                throws ParserException {
            return null;
        }
    }
    
    public static class MockParser_ReturnsEmptyRecordList extends BaseParser {

        @Override
        public List<Record> parse(InputDescriptor input)
                throws ParserException {
            return new ArrayList<Record>();
        }
    }  
    
    public static class MockParser_ReturnsEmptyRecord extends BaseParser {

        @Override
        public List<Record> parse(InputDescriptor input)
                throws ParserException {
            List<Record> records = new ArrayList<>();
            records.add(new MockRecord());
            return records;
        }
    }   
    
    public static class MockRecord extends BaseRecord {

        @Override
        public boolean isValid() {
            return true;
        }   
    }
    
    public static class MockInputService implements InputService {
        
        @Override
        public void configure(Configuration config) {
            throw new RuntimeException("Method not implemented.");        
        }

        @Override
        public Iterable<InputDescriptor> getDescriptors() {
            List<InputDescriptor> descriptors = new ArrayList<>();
            descriptors.add(new MockInputDescriptor());
            descriptors.add(new MockInputDescriptor());
            descriptors.add(new MockInputDescriptor());
            return Collections.unmodifiableList(descriptors);
        }     
    }

    public static class MockInputDescriptor implements InputDescriptor {

        @Override
        public InputMetadata getMetadata() {
            return null;
        }

        @Override
        public synchronized InputStream getInputStream() throws IOException {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized void close() throws InputServiceException, IOException {
              
        }
    }
    
    public static class MockOutputService implements OutputService {

        @Override
        public void configure(Configuration config) {
            throw new RuntimeException("Method not implemented.");           
        }

        @Override
        public OutputDescriptor openSink(InputMetadata metadata)
                throws OutputServiceException, IOException {
            return null;
        }       
    }
    
    public class MockOutputDescriptor implements OutputDescriptor {
        
        private OutputStream output;
        
        public MockOutputDescriptor() {
            this.output = new ByteArrayOutputStream();
        }

        @Override
        public void writeModel(Model model)
                throws IOException, OutputServiceException {
            model.write(output);
        }

        @Override
        public void close() throws IOException, OutputServiceException {
            throw new RuntimeException("Method not implemented");         
        }
        
    }

    private static BaseMockBib2LodObjectFactory factory;
    private InputDescriptor input; 
    private OutputDescriptor output;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
    }
    
    @Before
    public void setUp() {
        input = new MockInputDescriptor();
        output = new MockOutputDescriptor();
        
        // Suppress output when BaseConverter throws an exception.
        suppressSysout();
        suppressSyserr();
    }  
    
    @After
    public void tearDown() {
        factory.unsetInstances();
    }      
   
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void singleInputConversionException_Succeeds() throws Exception {
        InputService inputService = new MockInputService();
        OutputService outputService = new MockOutputService();
        Converter converter = new MockConverter_IgnoresSingleInputConversionException();
        converter.convertAll(inputService, outputService);
    }
    
    @Test
    public void nullRecordList_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsNullRecordList());
        Converter converter = new MockConverter_ThrowsRecordConversionException();
        converter.convert(input, output);
    }

    @Test
    public void emptyRecordList_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecordList());
        Converter converter = new MockConverter_ThrowsRecordConversionException();
        converter.convert(input, output);
    }
    
    @Test
    public void convertRecordException_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecord());
        Converter converter = new MockConverter_ThrowsRecordConversionException();
        converter.convert(input, output);        
    }
    
    @Test
    public void emptyModel_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecord());
        Converter converter = new MockConverter_ReturnsEmptyModel();
        converter.convert(input, output);  
    }
}

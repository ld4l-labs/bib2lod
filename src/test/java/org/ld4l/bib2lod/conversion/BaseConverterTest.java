/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.InputService.InputMetadata;
import org.ld4l.bib2lod.io.InputService.InputServiceException;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.parsing.BaseParser;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.records.BaseRecord;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * Tests abstract class BaseConverter.
 */
public class BaseConverterTest extends AbstractTestClass {
 
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------

    public static class MockBib2LodObjectFactory extends Bib2LodObjectFactory {

        MapOfLists<Class<?>, Object> instances = new MapOfLists<>();
        
        MockBib2LodObjectFactory() throws NoSuchFieldException, SecurityException {
            Field field = Bib2LodObjectFactory.class.getDeclaredField("instance");
            field.setAccessible(true);
            field = null;
            Bib2LodObjectFactory.setFactoryInstance(this); 
        }
        
        public <T> void addInstance(Class<T> interfaze, T instance) {
            addInstance(interfaze, instance, Configuration.EMPTY_CONFIGURATION);
        }
        
        public <T> void addInstance(Class<T> interfaze, T instance, Configuration config) {
            if (instance instanceof Configurable) {
                ((Configurable) instance).configure(config);
            }
            instances.addValue(interfaze, instance);
        }
        
        @SuppressWarnings("unchecked")
        @Override
        public <T> T instanceForInterface(Class<T> class1) {
            return (T) instances.getValue(class1);
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T> List<T> instancesForInterface(Class<T> class1) {
            return (List<T>) instances.getValues(class1);
        }
        
        public void unsetInstances() {
            instances = new MapOfLists<>();
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

    public static class MockInputDescriptor implements InputDescriptor {

        @Override
        public InputMetadata getMetadata() {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized InputStream getInputStream() throws IOException {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public synchronized void close() throws InputServiceException, IOException {
            throw new RuntimeException("Method not implemented.");          
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

    private static MockBib2LodObjectFactory factory;
    private Converter converter;
    private InputDescriptor input;
    private OutputDescriptor output;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new MockBib2LodObjectFactory();  
    }
    
    @Before
    public void setUp() {
        factory.addInstance(OutputDescriptor.class, new MockOutputDescriptor());
        input = new MockInputDescriptor();
        output = new MockOutputDescriptor();
    }  
    
    @After
    public void tearDown() {
        factory.unsetInstances();
    }      
   
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void nullRecordList_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsNullRecordList());
        factory.addInstance(Converter.class, new MockConverter_ThrowsRecordConversionException());
        converter = Converter.instance();
        converter.convert(input, output);
    }

    @Test
    public void emptyRecordList_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecordList());
        factory.addInstance(Converter.class, new MockConverter_ThrowsRecordConversionException());
        converter = Converter.instance();
        converter.convert(input, output);
    }
    
    @Test
    public void convertRecordException_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecord());
        factory.addInstance(Converter.class, new MockConverter_ThrowsRecordConversionException());
        converter = Converter.instance();
        converter.convert(input, output);        
    }
    
    @Test
    public void emptyModel_Succeeds() throws Exception {
        factory.addInstance(Parser.class, new MockParser_ReturnsEmptyRecord());
        factory.addInstance(Converter.class, new MockConverter_ReturnsEmptyModel());
        converter = Converter.instance();
        converter.convert(input, output);  
    }
}

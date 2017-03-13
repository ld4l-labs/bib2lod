/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.Parser.ParserException;
import org.ld4l.bib2lod.parsing.Parser.ParserInstantiationException;
import org.ld4l.bib2lod.record.Record;

/**
 * An abstract implementation.
 */
public abstract class BaseConverter implements Converter {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected Configuration configuration;

    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public BaseConverter(Configuration configuration) {
        this.configuration = configuration;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) throws ConverterException {

        Parser parser;
        List<Record> records;
        Model model = ModelFactory.createDefaultModel();
        
        try {
            parser = getParser();
            records = parser.parse(input);
            for (Record record : records) {
                model.add(convertRecord(record));
            }
            output.writeModel(model);
            LOGGER.debug(model.toString());
        } catch (ParserException | IOException | OutputServiceException e) {
            throw new ConverterException(e);
        }      
  
        // Then what do we do with it? Do we write it out here, or send it back
        // up to the Manager to write?
        

//        try {
//            for (Element record : records) {
//                // Parse the record into Entity objects.
//                List<Entity> entities = parser.parseRecord(record);
//                for (Entity entity : entities) {
//                    ModelBuilder modelBuilder = 
//                            ModelBuilder.instance(entity, configuration);
//                    Model resourceModel = modelBuilder.build();
//                    // Are there going to be any retractions? Then need to change
//                    // this to getAdditions(), getRetractions() from the builder.
//                    model.add(resourceModel);
//                }  
//            }
//            
//            model.write(outputStream, "N-TRIPLE");
//        } catch (ParserException e) {
//            throw new ConverterException(e);
//        }

    }

    /**
     * Returns the class of the Parser to instantiate as defined by the concrete
     * Converter class.
     */
    protected abstract Class<?> getParserClass();
    
    /**
     * Instantiates a Parser for this Converter. The Parser class is defined by
     * the concrete implementation.
     * @return the Parser
     */
    private Parser getParser() {
        Class<?> parserClass = getParserClass();
        return Parser.instance(configuration, parserClass);        
    }

    /**
     * Converts a record to an RDF Model.
     */
    // protected abstract Model convertRecord(Record record);
    // TODO Not sure whether this goes here or whether it is 
    // implementation-specific.
    protected Model convertRecord(Record record) {
        
        Model model = ModelFactory.createDefaultModel();
        
        // Create Instance and other Entities
        // Maybe use EntityBuilders to create these entities - not sure yet
        // Then for each Entity, create a model/set of statements/resource and 
        // add to model
        
        return model;
    }
    
}

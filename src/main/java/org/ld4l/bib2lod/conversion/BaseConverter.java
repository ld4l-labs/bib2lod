/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.Parser.ParserException;
import org.ld4l.bib2lod.record.Record;

/**
 * An abstract implementation.
 */
public abstract class BaseConverter implements Converter {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) 
            throws ConverterException {

        Parser parser;
        List<Record> records;
        
        parser = getParser();
        try {
            records = parser.parse(input);
        } catch (ParserException e) {
            // Caller should catch the exception and continue to next input.
            throw new ConverterException(e);
        }
        
        Model model = ModelFactory.createDefaultModel();
        
        for (Record record : records) {
            // TODO convertRecord() might return an empty model. Make sure this
            // doesn't throw an error, else test for model not empty.
            model.add(convertRecord(record));
        }

        try {
            output.writeModel(model);
            LOGGER.debug(model.toString());
        } catch (IOException | OutputServiceException e) {
            // Caller (e.g., SimpleManager) should skip this input and go to 
            // next input.
            throw new ConverterException(e);
        }
    }

    /**
     * Returns the class of the Parser to instantiate as defined by the concrete
     * Converter class.
     */
    protected abstract Class<?> getParserClass();
    
    /**
     * Instantiates a Parser for this Converter. The Parser class is defined by
     * the concrete implementation.
     */
    private Parser getParser() {
        Class<?> parserClass = getParserClass();
        return Parser.instance(parserClass);        
    }

    /**
     * Converts a Record to an RDF Model. Starting with the primary 
     * bibliographic resource (commonly an Instance)
     * @throws RecordConversionException 
     */
    protected Model convertRecord(Record record) 
            throws RecordConversionException  {

        // Build the primary Entity (e.g., an Instance) from the Record, 
        // its dependent Entities, and links to the dependents.
        Entity entity = buildEntity(record);
 
        // Build a Resource from the Entity, including its dependent Entities.
        // Each Resource is attached to its Entity.
        entity.buildResource();

        // Build the Model for this Record from the Entity's Resource and the
        // Resources of its dependent Entities.
        Model model = entity.buildModel();
        
        return model;

    }
    
    /**
     * Builds the set of Entity objects from which the Model will be built.
     * The implementing class is input-specific.
     * @throws EntityBuilderException 
     */
    protected abstract Entity buildEntity(Record record) 
            throws EntityBuilderException;

  
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
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
import org.ld4l.bib2lod.resourcebuilders.ResourceBuilder;
import org.ld4l.bib2lod.resourcebuilders.ResourceBuilder.ResourceBuilderException;

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
            try {
                model.add(convertRecord(record));
            } catch (RecordConversionException e) {
                // Log the error and continue to next record.
                // TODO We may want a more sophisticated logging mechanism for
                // this type of error.
                e.printStackTrace();
                continue;
            }
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
     * Converts a Record to an RDF Model.
     * @throws RecordConversionException 
     */
    protected Model convertRecord(Record record) 
            throws RecordConversionException  {

        List<Entity> entities;
        try {
            entities = buildEntities(record);
        } catch (EntityBuilderException e) {
            throw new RecordConversionException(e);
        }
     
        Model model = ModelFactory.createDefaultModel();
        
        for (Entity entity : entities) {
            // get a Jena Model/Resource/StmtIterator/List<Statement> for 
            // the entity         
            buildResource(entity, model);

        }           
        return model;
    }
    
    /**
     * Builds the set of Entity objects from which the Resources will be built.
     * @throws EntityBuilderException 
     */
    protected abstract List<Entity> buildEntities(Record record) 
            throws EntityBuilderException;

    
    /**
     * Builds a Resource from an Entity and adds it to the Model. The Model
     * must be passed to the ResourceBuilder so that the new Resource gets
     * added to this Model, rather than the ResourceBuilder creating a new
     * Model for the new Resource.
     * @throws ResourceBuilderException 
     */
    private void buildResource(Entity entity, Model model) 
            throws ResourceBuilderException {
        
        ResourceBuilder builder = ResourceBuilder.instance(entity, model);                
        builder.build();
    }
    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.InstanceEntityBuilder;
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
    
    protected Configuration configuration;

    /**
     * Constructor
     * @param configuration - the program Configuration
     */
    // TODO Do we need to pass in the Configuration?
    public BaseConverter(Configuration configuration) {
        this.configuration = configuration;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) 
            throws ConverterException {

        Parser parser;
        List<Record> records;
        Model model = ModelFactory.createDefaultModel();
        
        parser = getParser();
        try {
            records = parser.parse(input);
        } catch (ParserException e) {
            // Caller (e.g., SimpleManager) should skip this input and go to 
            // next input.
            throw new ConverterException(e);
        }
        
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
     * @return the Parser
     */
    // TODO Seems wrong to have this in the interface. Only reason is to 
    // allow call from BaseConverterTest.
    public Parser getParser() {
        Class<?> parserClass = getParserClass();
        return Parser.instance(configuration, parserClass);        
    }

    /**
     * Converts a Record to an RDF Model.
     * @throws RecordConversionException 
     * @throws RecordConverterException 
     */
    protected Model convertRecord(Record record) 
            throws RecordConversionException  {

        List<Entity> entities;
        try {
            entities = buildEntities(record);
        } catch (EntityBuilderException e) {
            // Caller convert() skips this record and continues to next record.
            throw new RecordConversionException(e);
        }
        
        Model model = ModelFactory.createDefaultModel();
        
        for (Entity entity : entities) {
            // get a Jena Model/Resource/StmtIterator/List<Statement> for 
            // the entity         
            model.add(buildModel(entity));

        }           
        return model;
    }
    
    /**
     * Builds the set of Entity objects from which the Models will be built.
     * @throws EntityBuilderException 
     */
    private List<Entity> buildEntities(Record record) 
            throws EntityBuilderException {
        
        List<Entity> entities = new ArrayList<Entity>();

        EntityBuilder instanceBuilder = 
                EntityBuilder.instance(InstanceEntityBuilder.class);
        Entity instance = instanceBuilder.build(record);
        // more stuff in here...or should instanceBuilder return a list
        // of entities itself? That is, will the other entities be spun
        // off the instance, or will we come back here to instantiate the
        // other builders and create the entities?
        entities.add(instance);

        return entities;
    }
    
    /**
     * Builds a Model from an Entity.
     */
    // TODO May want to return a Resource/StmtIterator/List<Statement> instead
    private Model buildModel(Entity entity) {
        Model model = ModelFactory.createDefaultModel();
        // TODO *** throw a ModelBuilder exception which convertRecord will  
        // catch, then throw as a ConverterException
        return model;
    }
    
}

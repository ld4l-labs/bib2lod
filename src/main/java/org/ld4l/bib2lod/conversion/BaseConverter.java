/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilders;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.Parser.ParserException;
import org.ld4l.bib2lod.records.Record;

/**
 * An abstract implementation.
 */
public abstract class BaseConverter implements Converter {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private EntityBuilders entityBuilders;

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) 
            throws ConverterException {

        Parser parser;
        List<Record> records;
        
        parser = Parser.instance();
        try {
            records = parser.parse(input);
        } catch (ParserException e) {
            // Caller should catch the exception and continue to next input.
            throw new ConverterException(e);
        }
        
        Model model = ModelFactory.createDefaultModel();
        entityBuilders = EntityBuilders.instance();
        
        for (Record record : records) {
            try {
                model.add(convertRecord(record));
            } catch (EntityBuilderException e) {
                // Continue to next record
                continue;
            }
        }

        try {
            output.writeModel(model);
            LOGGER.debug(model.toString());
        } catch (IOException | OutputServiceException e) {
            // Caller should catch the exception and continue to next input.
            throw new ConverterException(e);
        }
    }


    /**
     * Converts a Record to an RDF Model. Starting with the primary 
     * bibliographic resource (commonly an Instance)
     * @throws RecordConversionException 
     * @throws EntityBuilderException 
     */
    protected Model convertRecord(Record record) 
            throws RecordConversionException, EntityBuilderException  {

        // Build the primary Entity (e.g., an Instance) from the Record, 
        // its dependent Entities, and links to the dependents.
        Entity entity = buildEntity(record);
 
        // Build a Resource from the Entity, including its dependent Entities.
        // Each Resource is attached to its Entity.
        entity.buildResource();

        // Build the Model for this Record from the Entity's Resource and the
        // Resources of its dependent Entities.
        return entity.getModel();

    }
    
    protected EntityBuilder getBuilder(Class<? extends Type> type) {
        return entityBuilders.getBuilder(type);
    }
    
    /**
     * Builds the set of Entity objects from which the Model will be built.
     * The implementing class is input-specific.
     * @throws EntityBuilderException 
     */
    protected abstract Entity buildEntity(Record record) 
            throws EntityBuilderException;

  
}

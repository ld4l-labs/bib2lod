/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.OutputStream;
import java.io.Reader;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
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
     * @param configuration - the Configuration object
     */
    public BaseConverter(Configuration configuration) {
        this.configuration = configuration;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(Reader reader, OutputStream outputStream) 
            throws ConverterException, ParserException {

        Parser parser = getParser();        
        List<Record> records = parser.parse(reader);
        
        Model model = ModelFactory.createDefaultModel();
        
        for (Record record : records) {
            model.add(convertRecord(record));
        }
        
        LOGGER.debug(model.toString());
        
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
     * @return the Parser class
     */
    protected abstract Class<?> getParserClass();
    
    /**
     * Instantiates a Parser for this Converter. The Parser class is defined by
     * the concrete implementation.
     * @return the Parser
     * @throws ParserException
     */
    private Parser getParser() throws ParserException {
        // This converter needs a MarcxmlParser, so can it ask for it directly
        // rather than going through Parser.instance()?
        // Parser parser = Parser.instance(configuration);
        // Or call the MarcxmlParser directly?
        // Parser parser = new MarcxmlParser(configuration);
        // Parser parser = MarcxmlParser.instance(configuration);
        Class<?> parserClass = getParserClass();
        return Parser.instance(configuration, parserClass);        
    }

    /**
     * Converts a record to an RDF Model.
     * @param record
     * @return a Model
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

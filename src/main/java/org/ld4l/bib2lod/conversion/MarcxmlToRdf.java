/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.IOException;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputDescriptor;
import org.ld4l.bib2lod.io.OutputService.OutputServiceException;
import org.ld4l.bib2lod.modelbuilders.ModelBuilder;
import org.ld4l.bib2lod.parsing.MarcxmlParser;
import org.ld4l.bib2lod.parsing.Parser;
import org.ld4l.bib2lod.parsing.Parser.ParserException;
import org.w3c.dom.Element;

/**
 * An implementation that converts MARCXML to LD4L RDF
 */
public class MarcxmlToRdf extends XmlToRdf {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Constructor
     * @param configuration - the Configuration object
     */
    public MarcxmlToRdf(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.Converter#convert()
     */  
    @Override
    public void convert(InputDescriptor input, OutputDescriptor output) throws ConverterException {

        // This converter needs a MarcxmlParser, so can it ask for it directly
        // rather than going through Parser.instance()?
        // Parser parser = Parser.instance(configuration);
        // Or call the MarcxmlParser directly?
        // Parser parser = new MarcxmlParser(configuration);
        // Here we use a MarcxmlParser.instance() method, but does this have 
        // any advantages over calling the constructor directly?
        Parser parser = MarcxmlParser.instance(configuration);
        
        List<Element> records =  parser.getRecords(input);
  
        Model model = ModelFactory.createDefaultModel();
        
        try {
            for (Element record : records) {
                // Parse the record into Entity objects.
                List<Entity> entities = parser.parseRecord(record);
                for (Entity entity : entities) {
                    ModelBuilder modelBuilder = 
                            ModelBuilder.instance(entity, configuration);
                    Model resourceModel = modelBuilder.build();
                    // Are there going to be any retractions? Then need to change
                    // this to getAdditions(), getRetractions() from the builder.
                    model.add(resourceModel);
                }  
            }
            
            output.writeModel(model);
        } catch (ParserException | IOException | OutputServiceException e) {
            throw new ConverterException(e);
        }

    }
   
}

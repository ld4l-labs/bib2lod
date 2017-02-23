/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.cli.ParseException;
import org.apache.commons.io.LineIterator;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Entity.EntityInstantiationException;
import org.ld4l.bib2lod.modelbuilders.ModelBuilder;
import org.ld4l.bib2lod.parsing.MarcxmlParser;
import org.ld4l.bib2lod.parsing.Parser;
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
    public void convert(Reader reader, OutputStream outputStream) throws  
            InstantiationException,IllegalAccessException, 
                ClassNotFoundException, EntityInstantiationException, 
                    IllegalArgumentException, InvocationTargetException, 
                        NoSuchMethodException, SecurityException {

        // This converter needs a MarcxmlParser, so can it ask for it directly
        // rather than going through Parser.instance()?
        // Parser parser = Parser.instance(configuration);
        // Or call the MarcxmlParser directly?
        // Parser parser = new MarcxmlParser(configuration);
        // Here we use a MarcxmlParser.instance() method, but does this have 
        // any advantages over calling the constructor directly?
        Parser parser = MarcxmlParser.instance(configuration);
        
        List<Element> records =  parser.getRecords(reader);
  
        Model model = ModelFactory.createDefaultModel();
        
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
        
        model.write(outputStream, "N-TRIPLE");

    }
   
}

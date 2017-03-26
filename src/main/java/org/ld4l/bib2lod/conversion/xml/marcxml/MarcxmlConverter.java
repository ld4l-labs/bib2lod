/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.xml.XmlConverter;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.MarcxmlInstanceBuilder;
import org.ld4l.bib2lod.parsing.xml.marcxml.MarcxmlParser;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Converts MARCXML records
 */
public class MarcxmlConverter extends XmlConverter {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    private static final Class<?> PARSER_CLASS = MarcxmlParser.class;

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.BaseConverter#getParserClass()
     */
    @Override
    public Class<?> getParserClass() {
        return PARSER_CLASS;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.conversion.BaseConverter#buildEntities(org.ld4l.bib2lod.record.Record)
     */
    @Override
    protected List<Entity> buildEntities(Record record) 
            throws EntityBuilderException {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        // The Instance is the fundamental Entity created from the Record.
        EntityBuilder instanceBuilder = 
                new MarcxmlInstanceBuilder((MarcxmlRecord) record);
        entities.addAll(instanceBuilder.build());
        
        // From here, build bib resources: Instance, Work, Item
        // Other entities are built off of each of those (identifiers, 
        // activities, etc. Both work and item builders need to get passed 
        // the record and the instance.

        return entities;
    }
  
}

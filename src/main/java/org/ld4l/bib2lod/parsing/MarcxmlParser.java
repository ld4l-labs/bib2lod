/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Entity.EntityInstantiationException;
import org.ld4l.bib2lod.entitybuilders.MarcxmlInstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.w3c.dom.Element;

/**
 *
 */
public class MarcxmlParser extends XmlParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    // TODO instead use enums - Tag, Attribute, Class, Property 
    private static final String RECORD_TAG_NAME = "record";


    /**
     * Constructor
     * @param configuration - the program configuration
     */
    public MarcxmlParser(Configuration configuration) {
       super(configuration);
    }
    
    /**
     * Factory method
     * @param configuration
     * @return
     */
    public static MarcxmlParser instance(Configuration configuration) {
        return new MarcxmlParser(configuration);
    }
    
    protected String getRecordTagName() {
        return RECORD_TAG_NAME;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.Parser#parseRecord(java.lang.Object)
     */
    //@Override
    public List<Entity> parseRecord(Element record) throws 
            EntityInstantiationException, IllegalArgumentException, 
                InvocationTargetException, NoSuchMethodException, 
                    SecurityException {
        
        List<Entity> resources = new ArrayList<Entity>();

        try {
            
            // Again - is there a reason not to call the constructor directly,
            // when we know what kind of a builder we want?
            MarcxmlInstanceBuilder instanceBuilder = 
                    // TODO Does the EntityBuilder need the Configuration? So 
                    // far no.
                    (MarcxmlInstanceBuilder) EntityBuilder.instance(
                            MarcxmlInstanceBuilder.class, configuration);

            resources.addAll(instanceBuilder.build(record));
            
        } catch (InstantiationException | IllegalAccessException
                | ClassNotFoundException e) {
            throw new EntityInstantiationException(
                    e.getMessage(), e.getCause());
        } 
        
        return resources;
    }

}

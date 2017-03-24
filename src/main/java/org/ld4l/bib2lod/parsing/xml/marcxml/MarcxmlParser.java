/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 *
 */
public class MarcxmlParser extends XmlParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
              
    private static final String RECORD_TAG_NAME = "record";   
    private static final Class<?> RECORD_CLASS = MarcxmlRecord.class;
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.XmlParser#getRecordTagName()
     */
    @Override
    protected String getRecordTagName() {
        return RECORD_TAG_NAME;
    }
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.XmlParser#getRecordClass()
     */
    @Override
    protected Class<?> getRecordClass() {
        return RECORD_CLASS;
    }


    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.Parser#parseRecord(java.lang.Object)
     */
//    @Override
//    public List<Entity> parseRecord(Element record) throws 
//            ParserException {
//        
//        List<Entity> entities = new ArrayList<Entity>();
//
//        // Possibly instance should be treated differently from the other
//        // Entities that will be created from this record, since everything
//        // originates from the instance (the record). As such building the 
//        // instance Entity would just be part of MarcxmlParser, whereas there 
//        // would be individual builders for other Entity types.
//            
//        // Again - is there a reason not to call the constructor directly,
//        // when we know what kind of a builder we want?
//        MarcxmlInstanceBuilder instanceBuilder = 
//                (MarcxmlInstanceBuilder) EntityBuilder.instance(
//                        MarcxmlInstanceBuilder.class, configuration);
//
//        entities.addAll(instanceBuilder.build(record));
//            
//        return entities;
//    }

}

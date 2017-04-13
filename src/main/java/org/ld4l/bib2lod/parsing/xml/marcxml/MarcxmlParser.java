/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;

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

}

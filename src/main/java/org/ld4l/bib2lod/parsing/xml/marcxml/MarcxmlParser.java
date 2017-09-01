/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.w3c.dom.Element;

/**
 *
 */
public class MarcxmlParser extends XmlParser {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger(); 
              
    private static final String RECORD_TAG_NAME = "record";   
    
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
     * @see org.ld4l.bib2lod.parsing.XmlParser#createRecord()
     */
    @Override
    protected XmlRecord createRecord(Element recordElement)
            throws RecordException {
        return new MarcxmlRecord(recordElement);
    }

}

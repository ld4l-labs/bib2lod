/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.parsing.xml.XmlParser;
import org.ld4l.bib2lod.record.xml.MockXmlRecord;

/**
 * Mock XmlParser class used to test abstract XmlParser.
 */
public class MockXmlParser extends XmlParser {

    private static final String RECORD_TAG_NAME = "record";   
    private static final Class<?> RECORD_CLASS = MockXmlRecord.class;
    
    /**
     * Constructor
     */
    public MockXmlParser(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.XmlParser#getRecordTagName()
     */
    @Override
    protected String getRecordTagName() {
        return RECORD_TAG_NAME;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.XmlParser#getRecordClass()
     */
    @Override
    protected Class<?> getRecordClass() {
        return RECORD_CLASS;
    }

}

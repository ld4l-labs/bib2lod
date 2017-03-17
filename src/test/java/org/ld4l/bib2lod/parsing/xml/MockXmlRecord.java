/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml;

import org.ld4l.bib2lod.record.xml.BaseXmlRecord;
import org.w3c.dom.Element;

/**
 * Mock XmlRecord subclass to test XmlParser.
 */
public class MockXmlRecord extends BaseXmlRecord {
    
    private String textValue;
    
    /**
     * Constructor
     */
    public MockXmlRecord(Element record) {
        super(record);
        textValue = record.getFirstChild().getTextContent();    
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.Record#isValid()
     */
    @Override
    public boolean isValid() {
        if (! textValue.isEmpty()) {
            return false;
        }
        return true;
    }

}

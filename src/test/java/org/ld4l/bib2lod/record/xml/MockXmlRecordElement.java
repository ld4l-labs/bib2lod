/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import org.w3c.dom.Element;

/**
 * Mock XmlRecord class used to test abstract XmlParser.
 */
public class MockXmlRecordElement extends BaseXmlRecordElement {

    /**
     * Constructor
     */
    public MockXmlRecordElement(Element element) {
        super(element);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.RecordElement#isValid()
     */
    @Override
    public boolean isValid() {
        // This suffices for current tests
        return true;
    }

}

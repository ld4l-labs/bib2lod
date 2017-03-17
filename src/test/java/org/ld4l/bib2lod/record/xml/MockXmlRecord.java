/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Mock XmlRecord class used to test abstract XmlParser.
 */
public class MockXmlRecord extends BaseXmlRecord {
    
    private List<MockXmlRecordElement> children;

    /**
     * Constructor
     */
    public MockXmlRecord(Element record) {
        super(record);
        children = buildChildren(record);
    }
    
    private List<MockXmlRecordElement> buildChildren(Element record) {
        List<MockXmlRecordElement> children = 
                new ArrayList<MockXmlRecordElement>();
        NodeList nodes = record.getElementsByTagName("child");
        for (int i = 0; i < nodes.getLength(); i++) {
            children.add(
                    new MockXmlRecordElement ((Element) nodes.item(i)));
        }
        return children;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.Record#isValid()
     */
    @Override
    public boolean isValid() {      
        if (children.isEmpty()) {
            return false;
        }
        return true;
    }

}

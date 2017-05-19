/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.XmlElement;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Helper methods for testing XML input.
 */
public final class XmlTestUtils {

    public static Element buildRecordElementFromString(
            String s) throws RecordException {
        try {
            return DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(s.getBytes()))
                    .getDocumentElement();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RecordException(e);
        }
    }
    
    public static XmlElement buildElementFromString(
            Class<?> elementClass, String s) throws RecordFieldException {
        
        try {
            Element element = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(s.getBytes()))
                    .getDocumentElement();
            return XmlElement.instance(elementClass, element);  
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RecordFieldException(e);
        }
              
    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.ld4l.bib2lod.record.Record.RecordException;
import org.ld4l.bib2lod.record.RecordElement.RecordElementException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Provides helper methods for testing XML input.
 */
public final class XmlTestUtils {

    public static XmlRecord buildRecordFromString(
            Class<?> recordClass, String s) throws RecordException {
            
        Element element;
        try {
            element = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(s.getBytes()))
                    .getDocumentElement();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RecordException(e);
        }
        return XmlRecord.instance(recordClass, element);
    }
    
    public static XmlRecordElement buildElementFromString(
            Class<?> elementClass, String s) throws RecordElementException {
        
        Element element;
        try {
            element = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(new ByteArrayInputStream(s.getBytes()))
                    .getDocumentElement();
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new RecordElementException(e);
        }
        return XmlRecordElement.instance(elementClass, element);        
    }
}

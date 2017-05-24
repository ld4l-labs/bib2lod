/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing.xml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.parsing.BaseParser;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.Record.RecordException;
import org.ld4l.bib2lod.records.xml.XmlRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses XML input records.
 */
public abstract class XmlParser extends BaseParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.parsing.Parser#parse(org.ld4l.bib2lod.io.InputService.InputDescriptor)
     */
    @Override
    public List<Record> parse(InputDescriptor input) throws ParserException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;

        Document doc;
        try {
            docBuilder = dbFactory.newDocumentBuilder();
            doc = docBuilder.parse(input.getInputStream());
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new ParserException(e.getMessage(), e.getCause());
        } 
      
        doc.getDocumentElement().normalize();
 
        NodeList nodes = doc.getElementsByTagName(getRecordTagName());
        
        List<Record> records = new ArrayList<Record>();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Element recordElement = (Element) nodes.item(i);
            try {
                Record record = createRecord(recordElement);
                // TODO Or do in XmlRecord.instance() method and have it return 
                // null if not valid?
                records.add(record);
            } catch (RecordException e) {
                // Skip this record
                continue;
            }
        }
         
        return records;
    }
    
    /**
     * Returns the name of the tag enclosing a record. Each subclass will define
     * its own tag name; e.g., "record" in MarcxmlParser.
     */
    protected abstract String getRecordTagName();
    
    /**
     * Creates a new Record from the XML Element
     */
    protected abstract XmlRecord createRecord(Element recordElement)
            throws RecordException;
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.io.InputService.InputDescriptor;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.Record.RecordException;
import org.ld4l.bib2lod.record.xml.XmlRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses XML input records.
 */
// TODO Maybe make this an interface, so that we can provide a 
// parseRecord(Element record) method. Then need a BaseXmlParser implementation
// to provide the common XML parseRecord() method. Doing with generic methods
// in Parser now
public abstract class XmlParser extends BaseParser {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    /**
     * Constructor
     * @param configuration - the program configuration
     */
    public XmlParser(Configuration configuration) {
        super(configuration);
    }
    

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
 
        // TODO How do we guarantee that each XmlParser implementation defines
        // getRecordTagName()? It's not part of the public interface.
        // Could this be pushed up to BaseParser? There must be a record in 
        // every type of input, though not a record tag/XML element. But perhaps
        // it won't have a string name.
        NodeList nodes = doc.getElementsByTagName(getRecordTagName());
        
        List<Record> records = new ArrayList<Record>();
        Class<?> recordClass = getRecordClass();
        
        for (int i = 0; i < nodes.getLength(); i++) {
            Element recordElement = (Element) nodes.item(i);
            try {
                records.add(XmlRecord.instance(recordClass, recordElement));
            } catch (RecordException e) {
                throw new ParserException(e);
            }
        }
         
        return records;
    }
 
    
    /**
     * Returns the name of the tag enclosing a record. Each subclass will define
     * its own tag name; e.g., "record" in MarcxmlParser.
     * @return the XML record tag name
     */
    protected abstract String getRecordTagName();
    
    /**
     * Returns the Record class to instantiate.
     * @return the Record class
     */
    protected abstract Class<?> getRecordClass();

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.parsing;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
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
    
    // Turning the NodeList into a List<Node> allows implementation of the
    // interface method
    //public NodeList getRecords(Reader reader) { 
    // Not sure what to do about this warning: The return type List<Node> for 
    // getRecords(Reader) from the type XmlParser needs unchecked conversion to 
    // conform to List<Object> from the type Parser.
    // Is return type List<T> the same as List<Object>? Temporarily added 
    // @SuppressWarnings("unchecked")
    @SuppressWarnings("unchecked")
    @Override
    public List<Element> getRecords(Reader reader) {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
       
        try {
            docBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new InputParseException(e.getMessage(), e.getCause());
        }
        
        // TODO Or convert reader to an InputStream? Does it matter?
        InputSource inputSource = new InputSource(reader);
        Document doc;
        try {
            doc = docBuilder.parse(inputSource);
        } catch (SAXException | IOException e) {
            throw new InputParseException(e.getMessage(), e.getCause());
        } 
        
        doc.getDocumentElement().normalize();
    
        NodeList nodes = doc.getElementsByTagName(getRecordTagName());
        List<Element> records = new ArrayList<Element>();
        for (int index = 0; index < nodes.getLength(); index++) {
            records.add((Element) nodes.item(index));
        }
        
        return records;
    }
    
    /**
     * Returns the name of the tag enclosing a record. Each child will define
     * its own tag name; e.g., "record" in MarcxmlParser.
     * @return
     */
    protected abstract String getRecordTagName();

//    /**
//     * Parses the record into Resources.
//     * @param record - the XML record
//     * @return
//     */
//    public List<Entity> parseRecord(Element record) {
//        
//        List<Entity> datafields = new ArrayList<Entity>();
//
//        // Instance is the only field that can take ONLY a record.
//        // TODO: Use a factory method of Entity? Should Instance be an
//        // interface in order to apply decorators later?
//        Entity instance = new Instance(record);
//        datafields.add(instance);
//        
//        // Now loop through fields
//        
//        return datafields; 
//    }

}

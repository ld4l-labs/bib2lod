package org.ld4l.bib2lod.conversion.to_rdf;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MarcxmlToLd4lRdf extends MarcxmlToRdf {

    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlToLd4lRdf.class);
    
    protected StringBuilder convert() {
        return null;
    }


}

package org.ld4l.bib2lod.conversion;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.jena.rdf.model.Model;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

public interface Converter {

    public String convertFile(File file) 
            throws ParserConfigurationException, SAXException, IOException;
    
    // Would create file from path and call convertFile(File). Not sure if
    // needed.
    // public String convertFile(String path);
  
    public Model convertRecord(Element record);
}

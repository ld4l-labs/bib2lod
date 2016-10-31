package org.ld4l.bib2lod.conversion;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface Converter {

    public String convertFile(File file) 
            throws ParserConfigurationException, SAXException, IOException;
    
    // public String convertFile(String text);
  
    // public String convertRecord(Record record);
}

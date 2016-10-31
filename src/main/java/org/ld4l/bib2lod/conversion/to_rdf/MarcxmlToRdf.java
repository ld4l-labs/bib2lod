package org.ld4l.bib2lod.conversion.to_rdf;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.clean.marcxml.MarcxmlCleaner;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO Could have a deeper class hierarchy: FromXml > FromMarcxml >
// MarcxmlToRdf > FromMarcxmlToLd4lRdf. Aside from the tag name, convertFile()
// can be in FromXml. FromMarcxml specifies tag name "record"
public abstract class MarcxmlToRdf extends BaseConverter {
    
    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlToRdf.class);

    @Override
    public String convertFile(File file) 
            throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(file);
        // TODO What does this do?
        doc.getDocumentElement().normalize();
        
        Model model = ModelFactory.createDefaultModel();
        NodeList records = doc.getElementsByTagName("record");
        for (int index = 0; index < records.getLength(); index++) {
            Node record = records.item(index);
            model.add(convertRecord(record));
        }

        // TODO Is this the best way to convert a model to a string?
        return model.toString();

    }
    
    protected Model convertRecord(Node record) {
        
        // TODO Clean - use marcxml cleaner
        // TODO Use factory instead of constructor?
        Cleaner cleaner = new MarcxmlCleaner();
        // Node cleaned = clean(record);
        
        Model model = ModelFactory.createDefaultModel();

        // loop through fields
        // first headers etc
        // convert headers
        
        // then datafields
        // for each datafield - loop through subfields
        // but not so simple - may need to consider some fields together
        
        // model.add(convertDataFields())
        
        return model;
    }

}

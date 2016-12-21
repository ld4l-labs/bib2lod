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
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

// TODO Could have a deeper class hierarchy: FromXml > FromMarcxml >
// MarcxmlToRdf > FromMarcxmlToLd4lRdf. Aside from the tag name, convertFile()
// can be in FromXml. FromMarcxml specifies tag name "record"
public abstract class MarcxmlToRdf extends BaseConverter {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    public MarcxmlToRdf(Configuration configuration) {
        super(configuration);
    }

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
        for (int i = 0; i < records.getLength(); i++) {
            Element record = (Element) records.item(i);
            model.add(convertRecord(record));
        }

        // TODO Is this the best way to convert a model to a string?
        return model.toString();

    }
    
    public abstract Model convertRecord(Element record);
    

    // TODO Turn into a ControlFieldConverter?
    // Because we need to know the type of identifier to create (bf:Identifier),
    // it's hard to do this from here. We could pass in the Identifier type,
    // but seems too much trouble to save a little bit of code.
//    protected Model convertControlFields(Element record, Resource instance) {
//        
//        Model model = ModelFactory.createDefaultModel();
//        
//        NodeList fields = record.getElementsByTagName("controlfield");
//        for (int i = 0; i < fields.getLength(); i++) {
//            Element e = (Element) fields.item(i);
//            String tag = e.getAttribute("tag");
//            String value = e.getNodeValue();
//            if (tag.equals("008")) {
//                //model.add
//            }
//        }        
//
//        return model;
//    }

}

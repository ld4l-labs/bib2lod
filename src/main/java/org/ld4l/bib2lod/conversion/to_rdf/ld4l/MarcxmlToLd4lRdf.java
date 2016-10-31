package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.clean.marcxml.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.to_rdf.MarcxmlToRdf;
import org.ld4l.bib2lod.conversion.to_rdf.ResourceBuilder;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class MarcxmlToLd4lRdf extends MarcxmlToRdf {

    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlToLd4lRdf.class);
    
    public MarcxmlToLd4lRdf(Configuration configuration) {
        super(configuration);
    }
    
//    protected StringBuilder convert() {
//        return null;
//    }
    
    @Override
    public Model convertRecord(Element record) {

        // TODO Use factory instead of constructor?
        Cleaner cleaner = new MarcxmlCleaner();
        // Node cleaned = clean(record);
        
        Model model = ModelFactory.createDefaultModel();
        
        ResourceBuilder instanceBuilder = new BfInstanceBuilder(uriMinter);
        Resource instance = instanceBuilder.build(model);
        model.add(instance.getModel());
        
        model.add(convertControlFields(record, instance));

        // then datafields
        // for each datafield - loop through subfields
        // but not so simple - may need to consider some fields together
        
        // model.add(convertDataFields())
        
        return model;
    }    
    
    protected Model convertControlFields(Element record, Resource instance) {
  
        Model model = ModelFactory.createDefaultModel();
  
        NodeList fields = record.getElementsByTagName("controlfield");
        if (fields.getLength() == 0) {
            return model;
        }
        
        ResourceBuilder identifierBuilder = 
                new BfIdentifierBuilder(uriMinter);
        
        for (int i = 0; i < fields.getLength(); i++) {
            Element e = (Element) fields.item(i);
            String tag = e.getAttribute("tag");
            String value = e.getNodeValue();
            if (tag.equals("008")) {
              // TODO Actually we need the type and the value (for datatype
              // props like this one). ***
              Resource identifier = identifierBuilder.build(
                      "http://bib.ld4l.org/ontology/LocalIlsIdentifier", model);
              model.add(identifier.getModel());
            }

        }        

        return model;
    }

}

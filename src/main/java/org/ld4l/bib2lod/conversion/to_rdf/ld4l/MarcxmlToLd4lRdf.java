package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.clean.marcxml.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.to_rdf.MarcxmlToRdf;
import org.ld4l.bib2lod.conversion.to_rdf.ResourceBuilder;
import org.ld4l.bib2lod.util.rdf.RdfUtil;
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
        
        ResourceBuilder instanceBuilder = new InstanceBuilder(uriMinter);
        Resource instance = instanceBuilder.build(record, model);
        model.add(instance.getModel());
      
        model.add(convertControlFields(record, instance));
        
        model.add(convertDataFields(record, instance));
        
        LOGGER.debug(RdfUtil.printModel(
                model, "Model returned by convertRecord():"));
        return model;
    }    
 
    private Model convertControlFields(Element record, Resource instance) {

        Model model = ModelFactory.createDefaultModel();
        
        NodeList fields = record.getElementsByTagName("controlfield");
        if (fields.getLength() == 0) {
            return model;
        }
        
        ResourceBuilder identifierBuilder = 
                new IdentifierBuilder(uriMinter);
        
        for (int i = 0; i < fields.getLength(); i++) {
            Element e = (Element) fields.item(i);
            String tag = e.getAttribute("tag");
            // TODO Don't pass in text - do this in identifierBuilder.build() 
            // method ***
            String text = e.getFirstChild().getTextContent();
            LOGGER.debug("node value: " + text);
            if (tag.equals("001")) {
              Resource identifier = identifierBuilder.build(
                      "http://bib.ld4l.org/ontology/LocalIlsIdentifier", 
                          text, model);
              model.add(identifier.getModel());
              Property hasIdentifier = model.getProperty(
                      "http://id.loc.gov/ontologies/bibframe/identifier");
              model.add(instance, hasIdentifier, (RDFNode) identifier);
            }
        }        

        return model;
    }
    
    private Model convertDataFields(Element record, Resource instance) {
    
        Model model = ModelFactory.createDefaultModel();
        
        NodeList fields = record.getElementsByTagName("datafield");
        if (fields.getLength() == 0) {
            return model;
        }
        
        for (int i = 0; i < fields.getLength(); i++) {
            
            Element element = (Element) fields.item(i);
            String tag = element.getAttribute("tag");
            String text = element.getFirstChild().getTextContent();
            LOGGER.debug("node value: " + text);
            // Note: This isn't so simple. We may use other fields to construct
            // the title; in this case, 130 and 240. But we could skip these
            // in the loop and process them with the 245 field.
            if (tag.equals("245")) {
              ResourceBuilder titleBuilder = new TitleBulder(uriMinter);
              // Title type will be determine by the TitleBuilder, or will
              // assign the default type.
              // Pass in the entire record in order to also use fields 130 and
              // 240.
              Resource title = titleBuilder.build(element, record);
              if (title != null) {
                  model.add(title.getModel());
                  Property hasTitle = model.getProperty(
                      "http://id.loc.gov/ontologies/bibframe/title");
                  model.add(instance, hasTitle, (RDFNode) title);                 
                  // instance label = title label
                  Statement titleLabelStmt = title.getProperty(RDFS.label);
                  if (titleLabelStmt != null) {
                      String label = titleLabelStmt.getString();
                      
                      instance.addProperty(RDFS.label, label);
                  }
              }
            }
        }                    
        
        return model;
    }
    
}

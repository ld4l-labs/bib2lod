package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.conversion.to_rdf.ResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class InstanceBuilder extends BaseResourceBuilder {

    private static final Logger LOGGER = 
            LogManager.getLogger(InstanceBuilder.class);

    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            "http://id.loc.gov/ontologies/bibframe/Instance");
    
    public InstanceBuilder(UriMinter uriMinter)  {
        super(uriMinter);
    }
    
    protected Resource getDefaultType() {
        return DEFAULT_TYPE;
    }
    
    @Override
    public Resource build(Element record, Model model) {
        
        Resource instance = super.build(model);
        model.add(convertControlFields(record, instance));
        
        model.add(buildTitle(record, instance));
        
        return instance;
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
    
    // TODO *** Move this to new TitleBuilder class
    private Model buildTitle(Element record, Resource instance) {
        
        Model model = instance.getModel();
        return model;
    }

}

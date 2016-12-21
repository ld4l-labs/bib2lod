package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class TitleBulder extends BaseResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private static String DEFAULT_TYPE_URI = 
            "http://www.loc.gov/mads/rdf/v1#Title";
    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            DEFAULT_TYPE_URI);

    public TitleBulder(UriMinter uriMinter) {
        super(uriMinter);
    }

    @Override
    protected Resource getDefaultType() {
        // TODO Auto-generated method stub
        return DEFAULT_TYPE;
    }
    
    @Override
    public Resource build(Element element, Element record, Resource type) {
        
        Model model = ModelFactory.createDefaultModel();
        
        Resource title = model.createResource(uriMinter.mintUri());
        title.addProperty(RDF.type, type);
        
        // Get title rdfs:label
        
        Model subfieldModel = convertSubfields(title, element);
        if (subfieldModel != null) {
            model.add(subfieldModel);
        }

        return title;
        
    }
    
    @Override
    public Resource build(Element element, Element record) {
        return build(element, record, DEFAULT_TYPE);
    }
    
    protected Model convertSubfields(Resource title, Element element) {
        
        Model model = title.getModel();
        
        NodeList fields = element.getElementsByTagName("subfield");
        if (fields.getLength() == 0) {
            return null;
        }
        
        for (int i = 0; i < fields.getLength(); i++) {
            Element subfield = (Element) fields.item(i);
            String code = subfield.getAttribute("code");
            String text = subfield.getFirstChild().getTextContent();
            if (code.contentEquals("a")) {
                title.addProperty(RDFS.label, text);
            }
            // TODO Fill in other subfields
            
        }
        // TODO build MainTitleElement resource - add to title model
        
        // TODO build other TitleElement resources - add to title model
        
        return model;
    }

}

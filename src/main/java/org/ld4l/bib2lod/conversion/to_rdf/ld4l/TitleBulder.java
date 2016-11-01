package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;

public class TitleBulder extends BaseResourceBuilder {
    
    private static final Logger LOGGER = 
            LogManager.getLogger(TitleBulder.class);
    
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
        
        // Link title to resource
        // TODO For now, this works for instances. Figure out later if it 
        // also works for titles.
        
        // Get title rdfs:label
        
        // Build MainTitleElement resource
        
        // Build other TitleElement resources
        
        return title;
        
    }
    
    @Override
    public Resource build(Element element, Element record) {
        return build(element, record, DEFAULT_TYPE);
    }

}

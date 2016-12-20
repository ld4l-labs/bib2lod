package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;

public class InstanceBuilder extends BaseResourceBuilder {

    private static final Logger LOGGER = LogManager.getLogger(); 

    private static String DEFAULT_TYPE_URI = 
            "http://id.loc.gov/ontologies/bibframe/Instance";
    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            DEFAULT_TYPE_URI);
    
    public InstanceBuilder(UriMinter uriMinter)  {
        super(uriMinter);
    }
    
    @Override
    public Resource build(Element record, Model model) {
        Resource instance = model.createResource(uriMinter.mintUri());
        // TODO Account for more specific types by looking at the record.
        instance.addProperty(RDF.type, DEFAULT_TYPE);
        return instance;       
    }
    
    protected Resource getDefaultType() {
        return DEFAULT_TYPE;
    }
    
}

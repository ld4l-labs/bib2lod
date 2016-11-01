package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;

public class BfInstanceBuilder extends BaseResourceBuilder {

    private static final Logger LOGGER = 
            LogManager.getLogger(BfInstanceBuilder.class);

    
    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            "http://id.loc.gov/ontologies/bibframe/Instance");
    
    public BfInstanceBuilder(UriMinter uriMinter)  {
        super(uriMinter);
    }
    
    protected Resource getDefaultType() {
        return DEFAULT_TYPE;
    }



}

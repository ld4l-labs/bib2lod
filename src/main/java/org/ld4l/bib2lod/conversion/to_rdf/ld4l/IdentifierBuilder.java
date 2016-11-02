package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;

public class IdentifierBuilder extends BaseResourceBuilder {

    private static final Logger LOGGER = 
            LogManager.getLogger(IdentifierBuilder.class);
    
    private static String DEFAULT_TYPE_URI = 
            "http://id.loc.gov/ontologies/bibframe/Instance";
    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            DEFAULT_TYPE_URI);
    
    public IdentifierBuilder(UriMinter uriMinter)  {
        super(uriMinter);
    }

    @Override
    protected Resource getDefaultType() {
        return DEFAULT_TYPE;
    }



}

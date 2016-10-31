package org.ld4l.bib2lod.conversion.to_rdf.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.conversion.to_rdf.BaseResourceBuilder;
import org.ld4l.bib2lod.uri.UriMinter;

public class BfIdentifierBuilder extends BaseResourceBuilder {

    private static Resource DEFAULT_TYPE = ResourceFactory.createResource(
            "http://id.loc.gov/ontologies/bibframe/Instance");
    
    public BfIdentifierBuilder(UriMinter uriMinter)  {
        super(uriMinter);
    }

    @Override
    protected Resource getDefaultType() {
        return DEFAULT_TYPE;
    }




}

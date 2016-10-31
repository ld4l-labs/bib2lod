package org.ld4l.bib2lod.conversion.to_rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;

public abstract class BaseResourceBuilder implements ResourceBuilder {

    private static final Logger LOGGER = 
            LogManager.getLogger(BaseResourceBuilder.class);
    
    protected UriMinter uriMinter;
    
    public BaseResourceBuilder(UriMinter uriMinter) {
        this.uriMinter = uriMinter;
    }
    
    // NB Return a resource when we create a new one, so we have a handle to
    // the new resource. Otherwise can return a model.
    public Resource build(Resource type, Model model) {
        
        String uri = uriMinter.mintUri();
        // NB Can't add assertion to resource unless it has a model.
        // Resource resource = ResourceFactory.createResource(uri);
        Resource resource = model.getResource(uri);
        resource.addProperty(RDF.type, getDefaultType());  
        return resource;
    }
    
    /**
     * Build with default type
     * @return
     */
    // NB Return a resource when we create a new one, so we have a handle to
    // the new resource. Otherwise can return a model.
    @Override
    public Resource build(Model model) {        
        return build(getDefaultType(), model);
    }
    
    @Override
    public Resource build(String typeUri, Model model) {
        Resource resource = ResourceFactory.createResource(typeUri);
        return build(resource, model);
    }


//
//    @Override
//    public Resource build(Element record) {
//        Resource resource = ResourceFactory.createResource();
//        return resource;
//    }
//
//    @Override
//    public Resource build(Element record, String type) {
//        Resource resource = ResourceFactory.createResource();
//        return resource;
//    }
    
    protected abstract Resource getDefaultType();


}

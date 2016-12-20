package org.ld4l.bib2lod.conversion.to_rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.uri.UriMinter;
import org.w3c.dom.Element;

public abstract class BaseResourceBuilder implements ResourceBuilder {

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    protected UriMinter uriMinter;
    
    public BaseResourceBuilder(UriMinter uriMinter) {
        this.uriMinter = uriMinter;
    }
    

    // NB Return a resource when we create a new one, so we have a handle to
    // the new resource. Otherwise can return a model.
    
    /**
     * For non-implementing subclasses
     */
    // Default implementation for non-implementing subclasses.
    // TODO Put here or in the interface?
    public Resource build(Element element, Model model) {
        return null;
    }
    
    @Override
    public Resource build(String typeUri, Model model) {
        Resource resource = model.createResource(uriMinter.mintUri());
        Resource type = model.createResource(typeUri);
        resource.addProperty(RDF.type, type);
        return resource;       
    }
    
    @Override
    public Resource build(String typeUri, String label, Model model) {
        Resource resource = build(typeUri, model);
        if (label != null) {
            resource.addProperty(RDFS.label, label);
        }
        return resource;
    }
    
    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Element element, Resource resource) {
        return null;
    }
    
    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Element record, Resource resource, Resource type) {
        return null;      
    }

    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Resource record) {
        return null;
    }
    

    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Resource record, Resource type) {
        return null;
    }
    
    
    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Element element, Element record, Resource type) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    // Default implementation for non-implementing subclasses.
    public Resource build(Element element, Element record) {
        return null;
    }

//    /**
//     * Build with default type
//     * @return
//     */
      // NB Return a resource when we create a new one, so we have a handle to
      // the new resource. Otherwise can return a model.
//    @Override
//    public Resource build(Model model) {        
//        return build(getDefaultType(), model);
//    }
    
//    @Override
//    public Resource build(Resource type, Model model) {
//  
//        String uri = uriMinter.mintUri();
//        // NB Can't add assertion to resource unless it has a model.
//        Resource resource = model.getResource(uri);
//        resource.addProperty(RDF.type, type);  
//        return resource;
//    }
    
    protected abstract Resource getDefaultType();













    



}

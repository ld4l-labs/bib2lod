package org.ld4l.bib2lod.conversion.to_rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.w3c.dom.Element;

public interface ResourceBuilder {

    // NB The build() methods return the resource rather than a model, so that 
    // the caller has a reference to the resource. The model can be retrieved 
    // with resource.getModel(), if needed.
    
    // TODO Some of these methods are so far only used within a ResourceBuilder
    // instance, rather than from a Converter instance. Those could be
    // protected and removed from the interface.
    
    // TODO There are way too many of these. Consolidate!
    
    
    public Resource build(Element element, Model model);

    public Resource build(String typeUri, String label, Model model);
    
    public Resource build(Element element, Resource resource);
    
    public Resource build(String type, Model model);
    
    public Resource build(Element element, Resource resource, Resource type);
    
    public Resource build(Resource record);
    
    public Resource build(Resource record, Resource type);
    
    public Resource build(Element element, Element record, Resource type);

    public Resource build(Element element, Element record);
    

}

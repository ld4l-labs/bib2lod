package org.ld4l.bib2lod.conversion.to_rdf;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.w3c.dom.Element;

public interface ResourceBuilder {

    public Resource build(Model model);
    
    public Resource build(Element element, Model model);

    public Resource build(Resource type, Model model);
    
    public Resource build(String type, Model model);
    
    public Resource build(String type, String label, Model model);


}

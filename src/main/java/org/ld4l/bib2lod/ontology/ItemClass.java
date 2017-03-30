package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum ItemClass implements OntologyClass {
        
    // There may not be any other Item classes
    ITEM(Namespace.BIBFRAME, "Item");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    ItemClass(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClassResource() {
        return ontClass;
    } 

    public static Resource superClass() {
        return ITEM.ontClass;
    }
}

package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum ItemType implements Type {
        
    // There may not be any other Item classes
    ITEM(Namespace.BIBFRAME, "Item");
    
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    ItemType(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 

    public static Type superClass() {
        return ITEM;
    }
}

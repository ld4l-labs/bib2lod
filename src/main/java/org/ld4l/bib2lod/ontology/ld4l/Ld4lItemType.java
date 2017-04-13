package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Item types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lItemType implements Type {
        
    // There may not be any other Item classes
    ITEM(Ld4lNamespace.BIBFRAME, "Item");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lItemType(Namespace namespace, String localName) {
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

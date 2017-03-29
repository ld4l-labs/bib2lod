package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.entities.Type;

public enum InstanceClass implements OntologyClass {
    
    ARCHIVAL(Namespace.BIBFRAME, "Archival"),
    ELECTRONIC(Namespace.BIBFRAME, "Electronic"),
    INSTANCE(Namespace.BIBFRAME, "Instance"),
    MANUSCRIPT(Namespace.BIBFRAME, "Manuscript"),
    PRINT(Namespace.BIBFRAME, "Print");
    
    private String uri;
    private Resource ontClass;
    private Type type;
    
    /**
     * Constructor
     */
    InstanceClass(Namespace namespace, String localName) {
        this.uri = namespace.uri() + localName;
        this.ontClass = ResourceFactory.createResource(uri);
        this.type = Type.instance(ontClass);
    }
    
    @Override
    public String uri() {
        return uri;
    }

    @Override
    public Resource ontClass() {
        return ontClass;
    } 
    
    @Override
    public Type type() {
        return type;
    }
    
    public static Resource superClass() {
        return INSTANCE.ontClass;
    }
}

package org.ld4l.bib2lod.ontology;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public enum TitleElementClass implements OntologyClass {

    NON_SORT_ELEMENT(Namespace.LD4L, "NonSortTitleElement"),
    MAIN_TITLE_ELEMENT(Namespace.LD4L, "MainTitleElement"),
    PART_NAME_ELEMENT(Namespace.LD4L, "MainTitleElement"),
    PART_NUMBER_ELEMENT(Namespace.LD4L, "MainTitleElement"),
    SUBTITLE_ELEMENT(Namespace.LD4L, "MainTitleElement"),
    TITLE_ELEMENT(Namespace.LD4L, "TitleElement");
 
    private String uri;
    private Resource ontClass;
    
    /**
     * Constructor
     */
    TitleElementClass(Namespace namespace, String localName) {
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
        return TITLE_ELEMENT.ontClass;
    }
}

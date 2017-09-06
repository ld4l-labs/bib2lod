package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Title Element types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lTitleElementType implements Type {

    /* List in alpha order */
    MAIN_TITLE_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "MainTitleElement"),
    NON_SORT_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "NonSortElement"),
    PART_NAME_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "PartNameElement"),
    PART_NUMBER_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "PartNumberElement"),
    SUBTITLE_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "SubtitleElement"),
    TITLE_ELEMENT(Ld4lNamespace.BIBLIOTEKO, "TitleElement");
 
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lTitleElementType(Namespace namespace, String localName) {
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

    public static Type superclass() {
        return TITLE_ELEMENT;
    }
}

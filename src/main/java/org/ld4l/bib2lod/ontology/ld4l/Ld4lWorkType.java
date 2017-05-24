package org.ld4l.bib2lod.ontology.ld4l;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;

/**
 * Enumerates the Work types used in the LD4L BIBFRAME 2 extension and
 * application profile.
 */
public enum Ld4lWorkType implements Type {

    /* List in alpha order */
    AUDIO(Ld4lNamespace.BIBFRAME, "Audio"),
    CARTOGRAPHY(Ld4lNamespace.BIBFRAME, "Cartography"),
    MIXED_MATERIAL(Ld4lNamespace.BIBFRAME, "MixedMaterial"),
    MOVING_IMAGE(Ld4lNamespace.BIBFRAME, "MovingImage"),
    NOTATED_MUSIC(Ld4lNamespace.BIBFRAME, "NotatedMusic"),
    OBJECT(Ld4lNamespace.BIBFRAME, "Object"),
    SOFTWARE(Ld4lNamespace.BIBLIOTEKO, "Software"),
    STILL_IMAGE(Ld4lNamespace.BIBFRAME, "StillImage"),
    TEXT(Ld4lNamespace.BIBFRAME, "Text"),
    WORK(Ld4lNamespace.BIBFRAME, "Work");
    
    private final String uri;
    private final Resource ontClass;
    
    /**
     * Constructor
     */
    Ld4lWorkType(Namespace namespace, String name) {
        this.uri = namespace.uri() + name;
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
        return WORK;
    }
}

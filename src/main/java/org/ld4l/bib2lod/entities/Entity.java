package org.ld4l.bib2lod.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.OntologyClass;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public interface Entity {
    
    /**
     * Factory methods
     */
    public static Entity instance(Type type) {
        return Bib2LodObjectFactory.instance().createEntity(type);
    }
    
    public static Entity instance(Resource ontClass) {
        return Bib2LodObjectFactory.instance().createEntity(ontClass);
    }

    public void addChild(Link link, Entity entity);
    
    public void addChildren(Link link, List<Entity> entities);
    
    public HashMap<Link, List<Entity>> getChildren();
    
    public List<Entity> getChildren(Link link); 
    
    public void addType(Type type);
    
    public List<Type> getTypes();
    
    public void addAttribute(Link link, String textValue);
    
    public void addAttribute(Link link, Literal value);
    
    public void addAttributes(Link link, List<Literal> values);
    
    public Map<Link, List<Literal>> getAttributes();
    
    public List<Literal> getAttributes(Link link);
    
    public void buildResource();
    
    public void setResource(Resource resource);
    
    public Resource getResource();

    void addType(OntologyClass ontClass);
    
}

package org.ld4l.bib2lod.resources;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.Bib2LodObjectFactory;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public interface Entity {
    
    /**
     * Factory method
     */
    public static Entity instance() {
        return Bib2LodObjectFactory.instance().createEntity();
    }
    
    /**
     * Factory method
     */
    public static Entity instance(Type type) {
        return Bib2LodObjectFactory.instance().createEntity(type);
    }
    
    /**
     * Factory method
     */
    public static Entity instance(String uri) {
        return Bib2LodObjectFactory.instance().createEntity(uri);
    }
    
    public void addChildren(Link link, List<Entity> entities);
    
    public TreeMap<Link, List<Entity>> getChildren();
    
    public List<Entity> getChildren(Link link);
    
    public void addType(Type type);
    
    public List<Type> getTypes();
    
    public void addAttributes(Link link, List<Literal> values);
    
    public Map<Link, List<Literal>> getAttributes();
    
    public List<Literal> getAttributes(Link link);
    
    public void setResource(Resource resource);
    
    public Resource getResource();

}

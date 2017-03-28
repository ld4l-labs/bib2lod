package org.ld4l.bib2lod.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.resources.Type;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public class SimpleEntity implements Entity {
    
    TreeMap<Link, List<Entity>> children;
    Map<Link, List<Literal>> attributes;
    List<Type> types;
    Resource resource;
    

    /**
     * Constructor
     */
    public SimpleEntity() {
        this.children = new TreeMap<Link, List<Entity>>();
        this.attributes = new HashMap<Link, List<Literal>>();
        this.types = new ArrayList<Type>();
    }

    /**
     * Constructor
     */
    public SimpleEntity(Type type) {
        this();
        types.add(type);
    }
    
    /**
     * Constructor
     */
    public SimpleEntity(String typeUri) {
        this();
        types.add(Type.instance(typeUri));
    }
    
    @Override
    public void addChildren(Link link, List<Entity> entities) {
        
        if (children.containsKey(link)) {
            List<Entity> list = children.get(link);
            list.addAll(entities);  
        } else {
            children.put(link,  entities);
        }          
    }
    
    @Override
    public TreeMap<Link, List<Entity>> getChildren() {
        return children;
    }
    
    @Override
    public List<Entity> getChildren(Link link) {
        return children.get(link);
    }
    
    @Override
    public void addType(Type type) {
        types.add(type);
    }
    
    @Override
    public List<Type> getTypes() {
        return types;
    }
    
    @Override
    public void addAttributes(Link link, List<Literal> values) {
        
        if (attributes.containsKey(link)) {
            List<Literal> list = attributes.get(link);
            list.addAll(values);  
        } else {
            attributes.put(link, values);
        }          
    }
    
    @Override
    public Map<Link, List<Literal>> getAttributes() {
        return attributes;
    }
    
    @Override
    public List<Literal> getAttributes(Link link) {
        return attributes.get(link);
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

}

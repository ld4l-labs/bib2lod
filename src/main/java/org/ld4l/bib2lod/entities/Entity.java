package org.ld4l.bib2lod.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public interface Entity {
    
    /**
     * Factory methods
     */ 
    public static Entity instance(Type ontClass) {
        return Bib2LodObjectFactory.getFactory().createEntity(ontClass);
    }
    
    public static Entity instance(Entity entity) {
        return Bib2LodObjectFactory.getFactory().createEntity(entity);
    }

    public void addChild(ObjectProp prop, Entity entity);
    
    public void addChildren(ObjectProp prop, List<Entity> entities);
    
    public HashMap<ObjectProp, List<Entity>> getChildren();
    
    public List<Entity> getChildren(ObjectProp prop); 
    
    /**
     * Use when only a single child with this link is expected. Others are
     * discarded.
     */
    public Entity getChild(ObjectProp prop);  
    
    public void addType(Type type);
    
    public List<Type> getTypes();
    
    public void addAttribute(DatatypeProp prop, String textValue);
    
    public void addAttribute(DatatypeProp prop, int i);
    
    public void addAttribute(DatatypeProp prop, Literal value);
    
    public void addAttributes(DatatypeProp prop, List<Literal> values);
    
    public Map<DatatypeProp, List<Literal>> getAttributes();
    
    public List<Literal> getAttributes(DatatypeProp prop);
    
    public void buildResource();
    
    public void setResource(Resource resource);
    
    public Resource getResource();
                      
    public Model buildModel();

}

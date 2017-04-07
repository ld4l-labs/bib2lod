package org.ld4l.bib2lod.entitybuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.uris.UriService;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public final class Entity {
    
    private final HashMap<Ld4lObjectProp, List<Entity>> children;
    private final Map<Ld4lDatatypeProp, List<Literal>> attributes;
    private final List<Type> types;
    private Resource resource;
    

    /**
     * Constructors
     */
    private Entity() {
        this.children = new HashMap<Ld4lObjectProp, List<Entity>>();
        this.attributes = new HashMap<Ld4lDatatypeProp, List<Literal>>();
        this.types = new ArrayList<Type>();
    }

    public Entity(Type type) {
        this();
        types.add(type);
    }

    /**
     * Copy constructor.
     * Use to copy the contents of non-reusable resources. For example, create
     * create a copy of an Instance Title to assign to a Work, where Title and
     * TitleElements are non-reusable, and thus the Work cannot simply prop to
     * the Instance Title.
     */
    public Entity(Entity original) {
        this();
        
        // Attributes and types are simply copied
        this.attributes.putAll(original.getAttributes());
        this.types.addAll(original.getTypes());
        
        // Copy the dependent Entities in each child 
        for (Entry<Ld4lObjectProp, List<Entity>> child : original.getChildren().entrySet()) {
            Ld4lObjectProp prop = child.getKey();
            List<Entity> originalEntities = child.getValue();
            List<Entity> newEntities = new ArrayList<Entity>();
            for (Entity entity : originalEntities) {
                Entity copy = new Entity(entity);
                newEntities.add(copy);
            }
            this.children.put(prop, newEntities);
        } 
    }

    public void addChild(Ld4lObjectProp prop, Entity entity) {
        
        if (children.containsKey(prop)) {
            List<Entity> list = children.get(prop);
            list.add(entity);  
        } else {
            children.put(prop, Arrays.asList(entity));
        }  
    }
    
    public void addChildren(Ld4lObjectProp prop, List<Entity> entities) {
        
        if (entities.isEmpty()) {
            return;
        }       
        if (children.containsKey(prop)) {
            List<Entity> list = children.get(prop);
            list.addAll(entities);  
        } else {
            children.put(prop,  entities);
        }          
    }
    
    public HashMap<Ld4lObjectProp, List<Entity>> getChildren() {
        return children;
    }
    
    public List<Entity> getChildren(Ld4lObjectProp prop) {
        return children.get(prop);
    }
    
    public Entity getChild(Ld4lObjectProp prop) {
        List<Entity> entities = children.get(prop);
        if (!entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
    }
    
    public void addType(Type type) {
        types.add(type);
    }
    
    public List<Type> getTypes() {
        return types;
    }
    
    public void addAttribute(Ld4lDatatypeProp prop, String string) {
        addAttribute(prop, ResourceFactory.createStringLiteral(string));
    }
    
    public void addAttribute(Ld4lDatatypeProp prop, int i) {
        Literal literal = ResourceFactory.createTypedLiteral(
                Integer.toString(i), XSDDatatype.XSDinteger);
        addAttribute(prop, literal);
    }
    
    public void addAttribute(Ld4lDatatypeProp prop, Literal value) {
        
        if (attributes.containsKey(prop)) {
            List<Literal> list = attributes.get(prop);
            list.add(value);
        } else {
            attributes.put(prop, Arrays.asList(value));
        }
    }
    
    public void addAttributes(Ld4lDatatypeProp prop, List<Literal> values) {
        
        if (values.isEmpty()) {
            return;
        }
        if (attributes.containsKey(prop)) {
            List<Literal> list = attributes.get(prop);
            list.addAll(values);  
        } else {
            attributes.put(prop, values);
        }            
    }
    
    public Map<Ld4lDatatypeProp, List<Literal>> getAttributes() {
        return attributes;
    }
    
    public List<Literal> getAttributes(Ld4lDatatypeProp prop) {
        return attributes.get(prop);
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }

    public void buildResource() {
        // Build children of this Entity before building the Entity, so that
        // when building the assertion linking this Entity to the child, we
        // have the URI of the child's Resource.
        buildChildResources();
        buildThisResource();
    }
    
    private void buildChildResources() {

        Map<Ld4lObjectProp, List<Entity>> children = getChildren();
        for (Entry<Ld4lObjectProp, List<Entity>> entry : children.entrySet()) {
            List<Entity> entities = entry.getValue();
            for (Entity childEntity : entities) {
                childEntity.buildResource();
            }
        }        
    }
    
    private void buildThisResource() {

        Model model = ModelFactory.createDefaultModel();
        String uri = UriService.getUri(this);
        Resource resource = model.createResource(uri);
        
        // Add type assertions
        for (Type type : types) {
            resource.addProperty(RDF.type, type.ontClass());
        }
        
        // Add relationships to children
        for (Entry<Ld4lObjectProp, List<Entity>> child : children.entrySet()) {
            Ld4lObjectProp prop = child.getKey();
            List<Entity> childEntities = children.get(prop);
            for (Entity childEntity : childEntities) {
                resource.addProperty(prop.property(), childEntity.getResource());         
            }          
        }
        
        // Add attributes       
        for (Entry<Ld4lDatatypeProp, List<Literal>> attribute : attributes.entrySet()) {
            Ld4lDatatypeProp prop = attribute.getKey();
            List<Literal> literals = getAttributes(prop);
            for (Literal literal : literals) {
                resource.addLiteral(prop.property(), literal);
            }
        }
        
        setResource(resource);        
    }
    
    public Model buildModel() {
        
        Model model = ModelFactory.createDefaultModel();
        model.add(buildChildModels());
        model.add(resource.getModel());
        return model;
    }
    
    private Model buildChildModels() {
        
        Model model = ModelFactory.createDefaultModel();
        
        for (Entry<Ld4lObjectProp, List<Entity>> child : children.entrySet()) {
            Ld4lObjectProp prop = child.getKey();
            List<Entity> childEntities = children.get(prop);
            for (Entity childEntity : childEntities) {
                Model childModel = childEntity.buildModel();
                model.add(childModel);
            }          
        }
        
        return model;
    }

}

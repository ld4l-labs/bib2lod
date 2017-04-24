package org.ld4l.bib2lod.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.uris.UriService;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public final class Entity {
    
    // Relationships of this entity to other local entities (objects of object
    // properties)
    private MapOfLists<ObjectProp, Entity> children;
    
    // Attributes of this entity (objects of datatype properties)
    private MapOfLists<DatatypeProp, Attribute> attributes;
    
    // Relationships of this entity to external resources. Map values are
    // lists of URIs of these resources. Since we already know the URIs of the 
    // external resources, and we will not make local assertions about them, 
    // there is no need to create an Entity.
    private MapOfLists<ObjectProp, String> externals;
    
    // The types the entity belongs to
    private List<Type> types;
    
    // The resource built from this entity
    private Resource resource;
    

    /**
     * Constructors
     */
    private Entity() {
        this.children = new MapOfLists<>();
        this.attributes = new MapOfLists<>();
        this.externals = new MapOfLists<>();
        this.types = new ArrayList<>();
    }

    public Entity(Type type) {
        this();
        types.add(type);
    }

    /**
     * Copy constructor.
     * Use to copy the contents of an Entity with non-reusable child Entities. 
     * For example, create a copy of an Instance Title to assign to a Work, 
     * where Title and TitleElements are non-reusable. New Title and TitleElement
     * Entities must be created with the same attributes and relationship
     * structure as the original.
     * 
     * Note that this should NOT be used to make an identical copy of the 
     * original Entity. Use MapOfLists.duplicate() for that purpose.
     */
    public Entity(Entity original) {
        
        // Attributes, types, and externals are simply copied
        this.attributes = original.attributes.duplicate();
        this.types = original.getTypes();
        this.externals = original.externals.duplicate();
        
        // Create new child entities
        this.children = new MapOfLists<>();      
        for (ObjectProp prop : original.children.keys()) {
            List<Entity> originalChildren = original.children.getValues(prop);
            List<Entity> newChildren = new ArrayList<Entity>();
            for (Entity originalChild : originalChildren) {
                Entity copy = new Entity(originalChild);
                newChildren.add(copy);
            }
            children.addValues(prop, newChildren);
        }  
    }

    public void addChild(ObjectProp prop, Entity entity) {
        children.addValue(prop, entity);
    }
    
    public void addChildren(ObjectProp prop, List<Entity> entities) {
        children.addValues(prop, entities);        
    }
    
    public MapOfLists<ObjectProp, Entity> getChildren() {
        return children;
    }
    
    public List<Entity> getChildren(ObjectProp prop) {
        return children.getValues(prop);
    }
    
    public Entity getChild(ObjectProp prop) {
        return children.getValue(prop);
    }
    
    public void addExternal(ObjectProp prop, String uri) {
        externals.addValue(prop, uri);       
    }
    
    public MapOfLists<ObjectProp, String> getExternals() {
        return externals;
    }
    
    public List<String> getExternals(ObjectProp prop) {
        return externals.getValues(prop);
    }
    
    public String getExternal(ObjectProp prop) {
        return externals.getValue(prop);
    }
     
    public void addType(Type type) {
        types.add(type);
    }
    
    public List<Type> getTypes() {
        return types;
    }
    
    public void addAttribute(DatatypeProp prop, String string) {
        addAttribute(prop, new Attribute(string));
    }
    
    public void addAttribute(DatatypeProp prop, int i) {
        addAttribute(prop, new Attribute(i));                
    }
    
    public void addAttribute(DatatypeProp prop, Attribute value) {
        attributes.addValue(prop, value);
    }
    
    public void addAttributes(DatatypeProp prop, List<Attribute> values) {
        attributes.addValues(prop, values);          
    }
    
    public MapOfLists<DatatypeProp, Attribute> getAttributes() {
        return attributes;
    }
    
    public List<Attribute> getValues(DatatypeProp prop) {
        return attributes.getValues(prop);
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
        
        for (ObjectProp prop : children.keys()) {
            for (Entity entity : children.getValues(prop)) {
                entity.buildResource();
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
        for (ObjectProp prop : children.keys()) {
            List<Entity> childEntities = children.getValues(prop);
            for (Entity entity : childEntities) {
                resource.addProperty(prop.property(), entity.getResource());
            }
        }
        
        // Add attributes       
        for (DatatypeProp prop : attributes.keys()) {
            for (Attribute attr : attributes.getValues(prop)) {
                resource.addProperty(prop.property(), attr.toLiteral());
            }
        }
        
        for (ObjectProp prop : externals.keys()) {
            for (String externalUri : externals.getValues(prop)) {
                resource.addProperty(prop.property(), 
                        ResourceFactory.createResource(externalUri));
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

        for (ObjectProp prop : children.keys()) {
            for (Entity entity : children.getValues(prop)) {
                Model childModel = entity.buildModel();
                model.add(childModel);
            }
        }       
        return model;
    }

}

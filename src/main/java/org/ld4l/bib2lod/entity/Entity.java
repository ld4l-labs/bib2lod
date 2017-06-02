package org.ld4l.bib2lod.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.ld4l.bib2lod.datatypes.Datatype;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.uris.UriService;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public class Entity {
    
    // Relationships of this entity to other local entities (objects of object
    // properties)
    protected MapOfLists<ObjectProp, Entity> relationships;
    
    // Attributes of this entity (objects of datatype properties)
    protected MapOfLists<DatatypeProp, Attribute> attributes;
    
    // Relationships of this entity to external resources. Map values are
    // lists of URIs of these resources. Since we already know the URIs of the 
    // external resources, and we will not make local assertions about them, 
    // there is no need to create an Entity.
    protected MapOfLists<ObjectProp, String> externalRelationships;
    
    // The types the entity belongs to
    protected List<Type> types;
    
    // The resource built from this entity
    private Resource resource;
    
    // The model built from this entity's resource
    private Model model;
    

    /**
     * Constructor
     */
    protected Entity() {
        this.relationships = new MapOfLists<>();
        this.attributes = new MapOfLists<>();
        this.externalRelationships = new MapOfLists<>();
        this.types = new ArrayList<>();
        this.resource = null;
        this.model = null;
    }

    /**
     * Constructor
     */
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
        this.externalRelationships = original.externalRelationships.duplicate();
        
        // Create new relationships to "child" entities
        this.relationships = new MapOfLists<>();      
        for (ObjectProp prop : original.relationships.keys()) {
            List<Entity> originalChildren = original.relationships.getValues(prop);
            List<Entity> newChildren = new ArrayList<Entity>();
            for (Entity originalChild : originalChildren) {
                Entity copy = new Entity(originalChild);
                newChildren.add(copy);
            }
            relationships.addValues(prop, newChildren);
        }  
    }

    public void addRelationship(ObjectProp prop, Entity entity) {
        relationships.addValue(prop, entity);
    }
    
    public void addRelationships(ObjectProp prop, List<Entity> entities) {
        relationships.addValues(prop, entities);        
    }
    
    public MapOfLists<ObjectProp, Entity> getRelationships() {
        return relationships;
    }
    
    public List<Entity> getChildren(ObjectProp prop) {
        return relationships.getValues(prop);
    }
    
    public Entity getChild(ObjectProp prop) {
        return relationships.getValue(prop);
    }
    
    public void addExternalRelationship(ObjectProp prop, String uri) {
        externalRelationships.addValue(prop, uri);       
    }
    
    public MapOfLists<ObjectProp, String> getExternalRelationships() {
        return externalRelationships;
    }
    
    public List<String> getExternals(ObjectProp prop) {
        return externalRelationships.getValues(prop);
    }
    
    public String getExternal(ObjectProp prop) {
        return externalRelationships.getValue(prop);
    }
     
    public void addType(Type type) {
        types.add(type);
    }
    
    public List<Type> getTypes() {
        return types;
    }
    
    public boolean hasType(Type type)  {
        return types.contains(type);
    }
    
    public void addAttribute(DatatypeProp prop, String string) {
        addAttribute(prop, new Attribute(string));
    }
    
    public void addAttribute(DatatypeProp prop, String string, Datatype type) {
        addAttribute(prop, new Attribute(string, type));
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
    
    public Attribute getAttribute(DatatypeProp prop) {
        return attributes.getValue(prop);
    }
    
    public List<Attribute> getValues(DatatypeProp prop) {
        return attributes.getValues(prop);
    }
    
    /**
     * Build the Entity's Resource with a specified URI
     */
    public void buildResource(String uri) {
        
        if (resource != null) {
            return;
        }
        
        Model model = ModelFactory.createDefaultModel();       
        this.resource = model.createResource(uri);
        
        // Add type assertions
        for (Type type : types) {
            resource.addProperty(RDF.type, type.ontClass());
        }
        
        // Add relationships to children
        for (ObjectProp prop : relationships.keys()) {
            List<Entity> childEntities = relationships.getValues(prop);
            for (Entity entity : childEntities) {
                // Build  out child resource
                entity.buildResource();
                resource.addProperty(prop.property(), entity.resource);
            }
        }
        
        // Add attributes       
        for (DatatypeProp prop : attributes.keys()) {
            for (Attribute attr : attributes.getValues(prop)) {
                resource.addProperty(prop.property(), attr.toLiteral());
            }
        }
        
        // Add relationships to external URIs
        for (ObjectProp prop : externalRelationships.keys()) {
            for (String externalUri : externalRelationships.getValues(prop)) {
                resource.addProperty(prop.property(), 
                        ResourceFactory.createResource(externalUri));
            }
        }   
    }

    public void buildResource() {
        
        if (resource != null) {
            return;
        }

        String uri = getUri();
        this.buildResource(uri);    
    }
    
    public Resource getResource() {
        return this.resource;
    }
    
    public Model getModel() {
        
        if (model == null) {
            this.model = ModelFactory.createDefaultModel();

            for (ObjectProp prop : relationships.keys()) {
                for (Entity entity : relationships.getValues(prop)) {
                    model.add(entity.getModel());
                }
            }    

            model.add(resource.getModel());
        }

        return model;
    }

    protected String getUri() {
        return UriService.getUri(this);
    }
}

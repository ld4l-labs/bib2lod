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
    
    /*
     * Relationships of this Entity to other local Entities (objects of object
     * properties)
     */
    protected MapOfLists<ObjectProp, Entity> relationships;
    
    /*
     * Attributes of this Entity (objects of datatype properties)
     */
    protected MapOfLists<DatatypeProp, Attribute> attributes;
    
    /*
     * Relationships of this Entity to external resources. Map values are
     * lists of URIs of these resources. Since we already know the URIs of the
     * external resources, and we will not make local assertions about them,
     * there is no need to create an Entity.
     */
    protected MapOfLists<ObjectProp, String> externalRelationships;
    
    /*
     * The classes the Entity belongs to
     */
    protected List<Type> types;
    
    /*
     * The Resource built from this Entity
     */
    private Resource resource;
    
    /*
     * The model built from this Entity's Resource
     */
    private Model model;
    

    /**
     * Constructor
     */
    public Entity() {
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
        
        // Create  relationships to copies of original child Entities
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
    
    /**
     * Returns the full map of relationships, or an empty map if there are none.
     * Never returns null;
     */
    public MapOfLists<ObjectProp, Entity> getRelationships() {
        return relationships;
    }
    
    /**
     * Returns the list of related Entities for the specified object property,
     * or an empty list if there are none. Never returns null.
     */
    public List<Entity> getChildren(ObjectProp prop) {
        return relationships.getValues(prop);
    }
    
    /**
     * Returns the first Entity in the list of related Entities for the 
     * specified object property, or null if there are none. Use when only a 
     * single value of the property is expected. 
     */
    public Entity getChild(ObjectProp prop) {
        return relationships.getValue(prop);
    }
    
    public void addExternalRelationship(ObjectProp prop, String uri) {
        externalRelationships.addValue(prop, uri);       
    }
    
    /**
     * Returns the full map of related external resources (as URIs), or an empty 
     * map if there are none. Never returns null. 
     */
    public MapOfLists<ObjectProp, String> getExternalRelationships() {
        return externalRelationships;
    }
    
    /**
     * Returns the list of related external resources (as URIs) for the 
     * specified object property, or an empty list of there are none. Never 
     * returns null.
     */
    public List<String> getExternals(ObjectProp prop) {
        return externalRelationships.getValues(prop);
    }
    
    /**
     * Returns the first item in the list of related external resources (as a 
     * URI) for this property, or null if there are none. Use when only a single 
     * value of the property is expected.
     */
    public String getExternal(ObjectProp prop) {
        return externalRelationships.getValue(prop);
    }
     
    public void addType(Type type) {
        types.add(type);
    }
    
    /**
     * Returns the first item in the list of types, or null if there are none. 
     * Use when only a single type is expected.
     */
    public Type getType() {
        if (types.isEmpty()) {
            return null;
        } else {
            return types.get(0);
        }     
    }
    
    /**
     * Returns all the types of this Entity, or an empty list if there are none.
     * Never returns null.
     */
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
    
    /** 
     * Returns the full attributes map, or an empty map if there are none. Never 
     * returns null.
     */
    public MapOfLists<DatatypeProp, Attribute> getAttributes() {
        return attributes;
    }
 
    /**
     * Returns the first item in the list of Attributes for the specified
     * datatype property, or null if there are none. Use when only a single
     * value of the property is expected.
     */
    public Attribute getAttribute(DatatypeProp prop) {
        return attributes.getValue(prop);
    }
    
    /** 
     * Returns the list of Attributes for this property, or an empty list if 
     * there are none. Never returns null.
     */
    public List<Attribute> getAttributes(DatatypeProp prop) {
        return attributes.getValues(prop);
    }
    
    /**
     * Returns the value of the first item in the list of Attributes for this
     * property, or null if there are no attributes. Use when only a single
     * value of the property is expected.
     */
    public String getValue(DatatypeProp prop) {
        Attribute attribute = attributes.getValue(prop);
        if (attribute == null) {
            return null;
        }
        return attributes.getValue(prop).getValue();
    }
    
    /**
     * Returns the values of the Attributes for this property, or an empty list
     * if there are none. Never returns null.
     */
    public List<String> getValues(DatatypeProp prop) {
        List<String> values = new ArrayList<>();
        List<Attribute> attribs = attributes.getValues(prop);
        for (Attribute attribute : attribs) {
            values.add(attribute.getValue());
        }
        return values;     
    }
    
    /**
     * Return true iff this Entity has no attributes, relationships, or
     * external relationships. (It may have types.)
     * @return
     */
    public boolean isEmpty() {
        return (relationships.isEmpty() && attributes.isEmpty() &&
                externalRelationships.isEmpty());
    }
    
    /**
     * Builds the Entity's Resource with a specified URI
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
                // Recursively build out child resource
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

    /**
     * Builds the Entity's Resource, using the configured UriServices.
     */
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
    
    /**
     * Returns the Model built from this Entity's Resource. 
     * @return
     */
    public Model getModel() {
        
        if (model == null) {
            this.model = ModelFactory.createDefaultModel();

            // Add models of child Entities 
            for (ObjectProp prop : relationships.keys()) {
                for (Entity entity : relationships.getValues(prop)) {
                    // Recursively build out child resource's model
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

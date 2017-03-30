package org.ld4l.bib2lod.entities;

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
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.uris.UriService;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public class SimpleEntity implements Entity {
    
    private final HashMap<ObjectProp, List<Entity>> children;
    private final Map<DatatypeProp, List<Literal>> attributes;
    private final List<Type> types;
    private Resource resource;
    

    /**
     * Constructors
     */
    private SimpleEntity() {
        this.children = new HashMap<ObjectProp, List<Entity>>();
        this.attributes = new HashMap<DatatypeProp, List<Literal>>();
        this.types = new ArrayList<Type>();
    }

    public SimpleEntity(Type type) {
        this();
        types.add(type);
    }
    
    public SimpleEntity(Resource ontClass) {
        this();
        types.add(Type.instance(ontClass));
    }
        
    public SimpleEntity(OntologyClass ontClass) {
        this(ontClass.ontClassResource());
    }
    
    /**
     * Copy constructor.
     * Use to copy the contents of non-reusable resources. For example, create
     * create a copy of an Instance Title to assign to a Work, where Title and
     * TitleElements are non-reusable, and thus the Work cannot simply prop to
     * the Instance Title.
     */
    public SimpleEntity(Entity original) {
        this();
        
        // Attributes and types are simply copied
        this.attributes.putAll(original.getAttributes());
        this.types.addAll(original.getTypes());
        
        // Copy the dependent Entities in each child 
        for (Entry<ObjectProp, List<Entity>> child : original.getChildren().entrySet()) {
            ObjectProp prop = child.getKey();
            List<Entity> originalEntities = child.getValue();
            List<Entity> newEntities = new ArrayList<Entity>();
            for (Entity entity : originalEntities) {
                Entity copy = Entity.instance(entity);
                newEntities.add(copy);
            }
            this.children.put(prop, newEntities);
        } 
    }

    @Override
    public void addChild(ObjectProp prop, Entity entity) {
        
        if (children.containsKey(prop)) {
            List<Entity> list = children.get(prop);
            list.add(entity);  
        } else {
            children.put(prop, Arrays.asList(entity));
        }  
    }
    
    @Override
    public void addChildren(ObjectProp prop, List<Entity> entities) {
        
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
    
    @Override
    public HashMap<ObjectProp, List<Entity>> getChildren() {
        return children;
    }
    
    @Override
    public List<Entity> getChildren(ObjectProp prop) {
        return children.get(prop);
    }
    
    @Override
    public Entity getChild(ObjectProp prop) {
        List<Entity> entities = children.get(prop);
        if (!entities.isEmpty()) {
            return entities.get(0);
        }
        return null;
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
    public void addAttribute(DatatypeProp prop, String string) {
        addAttribute(prop, ResourceFactory.createStringLiteral(string));
    }
    
    @Override
    public void addAttribute(DatatypeProp prop, int i) {
        Literal literal = ResourceFactory.createTypedLiteral(
                Integer.toString(i), XSDDatatype.XSDinteger);
        addAttribute(prop, literal);
    }
    
    @Override
    public void addAttribute(DatatypeProp prop, Literal value) {
        
        if (attributes.containsKey(prop)) {
            List<Literal> list = attributes.get(prop);
            list.add(value);
        } else {
            attributes.put(prop, Arrays.asList(value));
        }
    }
    
    @Override
    public void addAttributes(DatatypeProp prop, List<Literal> values) {
        
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
    
    @Override
    public Map<DatatypeProp, List<Literal>> getAttributes() {
        return attributes;
    }
    
    @Override
    public List<Literal> getAttributes(DatatypeProp prop) {
        return attributes.get(prop);
    }

    @Override
    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public Resource getResource() {
        return resource;
    }

    @Override
    public void addType(OntologyClass ontClass) {
        types.add(Type.instance(ontClass));
    }
    
    @Override
    public void buildResource() {
        // Build children of this Entity before building the Entity, so that
        // when building the assertion linking this Entity to the child, we
        // have the URI of the child's Resource.
        buildChildResources();
        buildThisResource();
    }
    
    private void buildChildResources() {

        Map<ObjectProp, List<Entity>> children = getChildren();
        for (Entry<ObjectProp, List<Entity>> entry : children.entrySet()) {
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
            resource.addProperty(RDF.type, type.getOntClass());
        }
        
        // Add relationships to children
        for (Entry<ObjectProp, List<Entity>> child : children.entrySet()) {
            ObjectProp prop = child.getKey();
            List<Entity> childEntities = children.get(prop);
            for (Entity childEntity : childEntities) {
                resource.addProperty(prop.property(), childEntity.getResource());         
            }          
        }
        
        // Add attributes       
        for (Entry<DatatypeProp, List<Literal>> attribute : attributes.entrySet()) {
            DatatypeProp prop = attribute.getKey();
            List<Literal> literals = getAttributes(prop);
            for (Literal literal : literals) {
                resource.addLiteral(prop.property(), literal);
            }
        }
        
        setResource(resource);        
    }
    
    @Override
    public Model buildModel() {
        
        Model model = ModelFactory.createDefaultModel();
        model.add(buildChildModels());
        model.add(resource.getModel());
        return model;
    }
    
    private Model buildChildModels() {
        
        Model model = ModelFactory.createDefaultModel();
        
        for (Entry<ObjectProp, List<Entity>> child : children.entrySet()) {
            ObjectProp prop = child.getKey();
            List<Entity> childEntities = children.get(prop);
            for (Entity childEntity : childEntities) {
                Model childModel = childEntity.buildModel();
                model.add(childModel);
            }          
        }
        
        return model;
    }

}

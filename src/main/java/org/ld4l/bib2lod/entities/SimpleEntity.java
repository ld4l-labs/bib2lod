package org.ld4l.bib2lod.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.uris.UriService;

/**
 * An object built from the input record representing a single resource in the
 * output model. 
 */
public class SimpleEntity implements Entity {
    
    private final HashMap<Link, List<Entity>> children;
    private final Map<Link, List<Literal>> attributes;
    private final List<Type> types;
    private Resource resource;
    

    /**
     * Constructors
     */
    private SimpleEntity() {
        this.children = new HashMap<Link, List<Entity>>();
        this.attributes = new HashMap<Link, List<Literal>>();
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
    
    
    @Override
    public void addChild(Link link, Entity entity) {
        
        if (children.containsKey(link)) {
            List<Entity> list = children.get(link);
            list.add(entity);  
        } else {
            children.put(link, Arrays.asList(entity));
        }  
    }
    
    @Override
    public void addChildren(Link link, List<Entity> entities) {
        
        if (entities.isEmpty()) {
            return;
        }
        
        if (children.containsKey(link)) {
            List<Entity> list = children.get(link);
            list.addAll(entities);  
        } else {
            children.put(link,  entities);
        }          
    }
    
    @Override
    public HashMap<Link, List<Entity>> getChildren() {
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
    public void addAttribute(Link link, String string) {
        addAttribute(link, ResourceFactory.createStringLiteral(string));
    }
    
    @Override
    public void addAttribute(Link link, Literal value) {
        
        if (attributes.containsKey(link)) {
            List<Literal> list = attributes.get(link);
            list.add(value);
        } else {
            attributes.put(link, Arrays.asList(value));
        }
    }
    
    @Override
    public void addAttributes(Link link, List<Literal> values) {
        
        if (values.isEmpty()) {
            return;
        }

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

        Map<Link, List<Entity>> children = getChildren();
        for (Entry<Link, List<Entity>> entry : children.entrySet()) {
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
        for (Entry<Link, List<Entity>> child : children.entrySet()) {
            Link link = child.getKey();
            List<Entity> childEntities = children.get(link);
            for (Entity childEntity : childEntities) {
                resource.addProperty(
                        link.getProperty(), childEntity.getResource());
            }          
        }
        
        // Add attributes       
        for (Entry<Link, List<Literal>> attribute : attributes.entrySet()) {
            Link link = attribute.getKey();
            List<Literal> literals = getAttributes(link);
            for (Literal literal : literals) {
                resource.addLiteral(link.getProperty(), literal);
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
        
        for (Entry<Link, List<Entity>> child : children.entrySet()) {
            Link link = child.getKey();
            List<Entity> childEntities = children.get(link);
            for (Entity childEntity : childEntities) {
                Model childModel = childEntity.buildModel();
                model.add(childModel);
            }          
        }
        
        return model;
    }

}

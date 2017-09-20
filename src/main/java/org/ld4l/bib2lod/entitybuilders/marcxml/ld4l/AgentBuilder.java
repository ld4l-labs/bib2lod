package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.List;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class AgentBuilder extends MarcxmlEntityBuilder {
    
    private static ObjectProp DEFAULT_RELATIONSHIP = 
            Ld4lObjectProp.HAS_AGENT;

    private MarcxmlDataField field;
    private Entity grandparent;
    private String name;
    private Entity parent;
    private ObjectProp relationship;
    private MarcxmlSubfield subfield;
    private Type type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        
        parseBuildParams(params);
        
        Entity agent;
        
        Entity existingAgent = findDuplicateAgent();
        if (existingAgent != null) {
            agent = existingAgent;
        } else {
            agent = buildAgent();
        }
        
        parent.addRelationship(relationship, agent);
        
        return agent;
    }
    
    private void reset() {
        this.field = null;
        this.name = null;
        this.parent = null;  
        this.relationship = null;
        this.subfield = null;     
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "A parent entity is required to build an agent.");
        }
        
        this.subfield = (MarcxmlSubfield) params.getSubfield();
        this.name = params.getValue(); 
        
        if (subfield == null && name == null) {
            throw new EntityBuilderException("A subfield or name value " + 
                    "is required to build an agent.");
        }
        if (name == null) {
            name = subfield.getTrimmedTextValue();                  
        }
        
        this.field = (MarcxmlDataField) params.getField();

        this.type = params.getType();
        if (type != null && ! (type instanceof Ld4lAgentType)) {
            throw new EntityBuilderException("Invalid agent type");
        } 
        
        this.relationship = params.getRelationship();       
        if (relationship == null) {
            this.relationship = DEFAULT_RELATIONSHIP;
        }

        this.grandparent = params.getGrandparent();
    }
    
    /**
     * If this agent duplicates an agent of another activity of the same
     * type for the same bib resource, use that agent rather than creating a 
     * new one. Current deduping is based only on the agent name strings, 
     * since that is what is available in, e.g., MARC 260$b.
     */
    private Entity findDuplicateAgent() {

        if (grandparent == null) {
            return null;
        }
        
        List<Entity> activities = grandparent.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY, parent.getType());
        for (Entity activity : activities) {
            Entity agent = activity.getChild(Ld4lObjectProp.HAS_AGENT);
            if (agent != null) {
                String agentName = agent.getValue(Ld4lDatatypeProp.NAME);
                if (name.equals(agentName)) {
                    return agent;
                }                
            }
        }
        
        return null;        
    }
    
    private Entity buildAgent() { 

        // First use type specified in build params, if any.
        if (type == null) {
            // If none specified, try to determine from input data.
            type = getType();
        }
        
        Entity agent = new Entity(type);
        
        if (type.equals(Ld4lAgentType.PERSON)) {
            return buildPerson(agent);
        } 
        if (type.equals(Ld4lAgentType.FAMILY)) {
            return buildFamily(agent);
        } else {
            return buildDefaultAgent(agent);    
        }
    }
    
    /**
     * Determines type from input data. Defaults to Ld4lAgentType default
     * type. Never returns null.
     */
    private Type getType() {
        
        // Try to determine person vs family from 100 etc.
        
        return Ld4lAgentType.defaultType();        
    }
    
    /** 
     * Builds a generic Agent.
     */
    private Entity buildDefaultAgent(Entity agent) {
        agent.addAttribute(Ld4lDatatypeProp.NAME, name);  
        return agent;
    }
    
    /**
     * Builds a Person Entity.
     */
    /*
     * For now, define a method rather than a subclass, since not much 
     * content. Later can define subclass if needed.
     */
    private Entity buildPerson(Entity agent) {
        agent.addAttribute(Ld4lDatatypeProp.NAME, name);    
        return agent;
    }
    
    /**
     * Builds a Family Entity.
     */
    /*
     * For now, define a method rather than a subclass, since not much 
     * content. Later can define subclass if needed.
     */
    private Entity buildFamily(Entity agent) {
        agent.addAttribute(Ld4lDatatypeProp.NAME, name); 
        return agent;
    }

}

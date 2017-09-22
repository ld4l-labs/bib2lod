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

    private Entity agent;
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
        
        buildAgent();
        
        parent.addRelationship(relationship, agent);
        
        return agent;
    }
    
    private void reset() {
        this.agent = null;
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

        this.type = params.getType();
        if (type != null && ! (type instanceof Ld4lAgentType)) {
            throw new EntityBuilderException("Invalid agent type");
        } 
        
        this.name = params.getValue();                
        this.subfield = (MarcxmlSubfield) params.getSubfield(); 
        this.field = (MarcxmlDataField) params.getField();       
        if (name == null && subfield == null && field == null) {
            throw new EntityBuilderException("A name value, subfield, or " +
                    "field is required to build an agent.");
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
    private void dedupeAgent() {

        if (grandparent == null) {
            return;
        }
        
        List<Entity> activities = grandparent.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY, parent.getType());
        for (Entity activity : activities) {
            Entity existingAgent = 
                    activity.getChild(Ld4lObjectProp.HAS_AGENT);
            if (existingAgent != null) {
                String agentName = 
                        existingAgent.getValue(Ld4lDatatypeProp.NAME);
                if (name.equals(agentName)) {
                    agent = existingAgent;
                }                
            }
        }       
    }
    
    private void buildAgent() { 

        // Use type specified in build params, if any.
        if (type == null) {
            // Otherwise determine type from input data.
            type = getType();
        }
        
        this.agent = new Entity(type);
        
        addAgentName();
        
        dedupeAgent();
    }
    
    private void addAgentName() {
        
        if (name == null) {
            if (subfield != null) {
                this.name = subfield.getTrimmedTextValue();
            } else {
                this.name = buildNameFromDataField(agent);
            }           
        } 
        
        if (name != null) {
            agent.addAttribute(Ld4lDatatypeProp.NAME, name);
        }       
    }
    
    /**
     * Determines type from input data. Defaults to Ld4lAgentType default
     * type. Never returns null.
     */
    private Type getType() {

        if (field != null && field.getTag().equals("100")) {
            if (field.getFirstIndicator() == 3) {
                return Ld4lAgentType.FAMILY;
            } else {
                return Ld4lAgentType.PERSON;
            }
        } 
        
        return Ld4lAgentType.defaultType();
    }
    
    private String buildNameFromDataField(Entity agent) {
        
        /*
         * Note that in field 100, ind1 value 0 means given name first, 
         * value 1 means family name first, but since for now we are not  
         * parsing the individual name parts we just set the generic name 
         * property.
         * 
         * TODO: if first indicator == 1, split on comma, assign first part
         * to family name and last part to given name.
         * 
         * TODO Add other agent attributes from other subfields.
         */
        if (field != null && field.getTag().equals("100")) {
            return field.getSubfield('a').getTrimmedTextValue();
        }
        
        return null;
    }
    
}

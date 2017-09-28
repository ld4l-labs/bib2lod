package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.Arrays;
import java.util.List;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class AgentBuilder extends MarcxmlEntityBuilder {
    
    private static ObjectProp DEFAULT_RELATIONSHIP = 
            Ld4lObjectProp.HAS_AGENT;

    private MarcxmlDataField field;
    private Entity grandparent;
    private Entity parent;
    private ObjectProp relationship;
    private MarcxmlSubfield subfield;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        
        parseBuildParams(params);
        
        Entity agent = buildAgent();
        
        if (agent != null) {
            parent.addRelationship(relationship, agent);
        }
        
        return agent;
    }
    
    private void reset() {
        this.field = null;
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
        this.field = (MarcxmlDataField) params.getField();       
        if (subfield == null && field == null) {
            throw new EntityBuilderException("A subfield or field is " +
                    "required to build an agent.");
        }
        
        this.relationship = params.getRelationship();       
        if (relationship == null) {
            this.relationship = DEFAULT_RELATIONSHIP;
        }

        this.grandparent = params.getGrandparent();
    }
    
   
    private Entity buildAgent() { 
        
        Entity agent = null;
        
        // Subfield only
        if (field == null) {
            agent = new Entity(Ld4lAgentType.defaultType());
            // TODO Add legacySourceData datatype?
            agent.addAttribute(Ld4lDatatypeProp.NAME, 
                    subfield.getTrimmedTextValue());
            
        } else  {
            switch (field.getTag()) {
            case "100":
                agent = convert100();
                break;
            case "260":
                agent = convert260();
                break;
            default: 
                break;
            }   
        }

        return agent;
    }
    
    private Entity convert100() {
        
        // Person or Family 
        Type type = field.getFirstIndicator() == 3 ? 
                Ld4lAgentType.FAMILY : Ld4lAgentType.PERSON; 
        Entity agent = new Entity(type);
        
        // Name
        String name = null;
        MarcxmlSubfield subfield = field.getSubfield('a');
        if (subfield != null) {
            if (type.equals(Ld4lAgentType.FAMILY)) {
                name = subfield.getTextValue();
            } else {
                // Personal name concatenates $a (name) $b (numeration) $c 
                // (titles and other words associated with the name)
                name = field.concatenateSubfieldValues(
                        Arrays.asList('a', 'b', 'c'));
            }
            agent.addLegacySourceDataAttribute(Ld4lDatatypeProp.NAME, 
                    // Trim final punct and whitespace from the name
                    XmlTextElement.trim(name));
        }   
        
        // Person birth and death dates. Variable values, no attempt to 
        // parse at this time, so use dcterms:date instead of 
        // schema:birthDate, schema:deathDate. 
        // Examples: "1775-1817", "d. 1683", "282-133 B.C."
        MarcxmlSubfield dateSubfield = field.getSubfield('d');
        if (dateSubfield != null) {
            agent.addLegacySourceDataAttribute(Ld4lDatatypeProp.DATE, 
                    dateSubfield.getTextValue());
        }
        
        return agent;      
    }
    
    private Entity convert260() {
       
        Entity agent = new Entity(Ld4lAgentType.defaultType());
        
        if (subfield != null) {
            agent.addAttribute(Ld4lDatatypeProp.NAME, 
                    subfield.getTrimmedTextValue());
        } 

        agent = dedupeAgent(agent);
        
        return agent;       
    }
    
    /**
     * If this agent duplicates an agent of another activity of the same
     * type for the same bib resource, use that agent rather than creating a 
     * new one. Current deduping is based only on the agent name strings, 
     * since that is what is available in, e.g., MARC 260$b.
     */
    private Entity dedupeAgent(Entity agent) {

        if (grandparent == null) {
            return agent;
        }
        
        String name = agent.getValue(Ld4lDatatypeProp.NAME);
        
        List<Entity> activities = grandparent.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY, parent.getType());
        for (Entity activity : activities) {
            Entity existingAgent = 
                    activity.getChild(Ld4lObjectProp.HAS_AGENT);
            if (existingAgent != null) {
                String agentName = 
                        existingAgent.getValue(Ld4lDatatypeProp.NAME);
                if (name.equals(agentName)) {
                    return existingAgent;
                }                
            }
        }  
        
        return agent;
    }   
    

    
}

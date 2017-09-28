package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.ObjectProp;
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
        
        Entity agent = new Entity();
        MarcxmlSubfield subfield$a = field.getSubfield('a');
        
        // Family
        if (field.getFirstIndicator() == 3) {
            agent.addType(Ld4lAgentType.FAMILY);
            
            // Name
            if (subfield$a != null) {
                agent.addLegacySourceDataAttribute(Ld4lDatatypeProp.NAME, 
                        subfield$a.getTrimmedTextValue());
            }
                
        // Person
        } else {
            agent.addType(Ld4lAgentType.PERSON);
 
            // Person name: concatenate $a (name) $b (numeration) $c (titles 
            // and other words associated with the name)
            if (subfield$a != null) {

                String name = field.concatenateSubfieldValues(
                        Arrays.asList('a', 'b', 'c', 'q'));
                if (name.endsWith(",")) {
                    name = StringUtils.chop(name);
                }
                agent.addLegacySourceDataAttribute(Ld4lDatatypeProp.NAME, 
                        name);
            }
            
            // Person birth and death dates: variable values, no attempt to 
            // parse at this time, so use dcterms:date instead of 
            // schema:birthDate, schema:deathDate. 
            // Examples: "1775-1817", "d. 1683", "282-133 B.C."
            // "dd. ca. 1558", "d1240 or 41-ca. 1316"
            MarcxmlSubfield subfield$d = field.getSubfield('d');
            if (subfield$d != null) {
                agent.addLegacySourceDataAttribute(Ld4lDatatypeProp.DATE, 
                        subfield$d.getTextValue());
            }
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

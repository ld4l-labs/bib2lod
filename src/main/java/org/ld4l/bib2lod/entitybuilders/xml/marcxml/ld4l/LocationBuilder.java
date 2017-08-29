package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.List;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class LocationBuilder extends BaseEntityBuilder {

    private Entity grandparent;
    private Entity location;
    private String name;    
    private Entity parent;
    private MarcxmlSubfield subfield;
    private Type type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        
        processBuildParams(params);
        
        if (type == null) {
            type = Ld4lLocationType.superClass();
        }

        if (name == null) {
            name = subfield.getTrimmedTextValue();                 
        }
        
        Entity existingLocation = findDuplicateLocation();
        if (existingLocation != null) {
            this.location = existingLocation;
        } else {
            this.location = new Entity(type);      
            location.addAttribute(Ld4lDatatypeProp.NAME, name);
        }
        
        parent.addRelationship(Ld4lObjectProp.HAS_LOCATION, location);
        
        return location;
    }
    
    private void reset() {
        this.grandparent = null;
        this.location = null;
        this.name = null;
        this.parent = null;
        this.subfield = null;
        this.type = null;
    }
    
    private void processBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "A parent entity is required to build a location.");
        }

        this.subfield = (MarcxmlSubfield) params.getSubfield(0);
        this.name = params.getValue();

        if (subfield == null && name == null) {
            throw new EntityBuilderException(
                    "A subfield or name value is required to build a " +
                            "location.");
        }
        
        this.type = params.getType(); 
        if (type != null && ! (type instanceof Ld4lLocationType)) {
            throw new EntityBuilderException("Invalid location type");
        } 
        
        this.grandparent = params.getGrandparent();
    }

    /**
     * If this location duplicates a location of another activity of the 
     * same type for the same resource, use that location rather than  
     * creating a new one. Current deduping is based only on the location  
     * name strings, since that is what is available in, e.g., MARC 260$a.
     */
    private Entity findDuplicateLocation() {

        if (grandparent == null) {
            return null;
        }
        
        List<Entity> activities = grandparent.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY, parent.getType());
        for (Entity activity : activities) {
            Entity location = activity.getChild(Ld4lObjectProp.HAS_LOCATION);
            if (location != null) {
                String agentName = location.getValue(Ld4lDatatypeProp.NAME);
                if (name.equals(agentName)) {
                    return location;
                }                
            }
        }
        
        return null;        
    }
}

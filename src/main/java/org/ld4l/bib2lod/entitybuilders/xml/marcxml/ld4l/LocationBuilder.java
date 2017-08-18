package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class LocationBuilder extends BaseEntityBuilder {
    
    private Entity parentEntity;
    private MarcxmlSubfield subfield;
    private String name;
    private Entity location;
    private Ld4lLocationType type;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.parentEntity = params.getParentEntity();
        if (parentEntity == null) {
            throw new EntityBuilderException(
                    "A parent entity is required to build an activity.");
        }

        this.subfield = (MarcxmlSubfield) params.getSubfield();
        this.name = params.getValue();

        if (subfield == null && name == null) {
            throw new EntityBuilderException(
                    "A subfield or name value is required to build an activity.");
        }
        
        Type typeParam = params.getType();
        this.type = (Ld4lLocationType) (typeParam != null ? 
                typeParam : Ld4lLocationType.superClass());
        this.location = new Entity(type);
        
        if (name == null) {
            name = subfield.getTrimmedTextValue();                 
        }
        
        location.addAttribute(Ld4lDatatypeProp.NAME, name);
        
        parentEntity.addRelationship(Ld4lObjectProp.HAS_LOCATION, location);
        
        return location;
    }

}

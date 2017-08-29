package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lExtentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;


public class PhysicalDescriptionBuilder extends BaseEntityBuilder {
    
    private Entity entity;
    private MarcxmlDataField field;
    private Entity parent;
    private MarcxmlSubfield subfield;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        reset();
        processBuildParams(params);
        return convertByField();
    }
    
    private void reset() {
        this.entity = null;
        this.parent = null;
    }
    
    private void processBuildParams(BuildParams params) 
            throws EntityBuilderException {

        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException("Cannot build physical " + 
                    "description without a related entity.");
        }
        
        this.field = (MarcxmlDataField) params.getField();
        if (field == null) {
            throw new EntityBuilderException("Cannot build physical  " + 
                    "description without an input field.");
        }
        
        this.subfield = (MarcxmlSubfield) params.getSubfield();
        if (subfield == null) {
            throw new EntityBuilderException("Cannot build physical  " + 
                    "description without a subfield.");
        }
    }
    
    private Entity convertByField() {

        switch (field.getTag()) {
        case 300: 
            return convert300();
        default:
            return null;
        }
    }
    
    private Entity convert300() {
        
        if (subfield.hasCode('a')) {
            entity = new Entity(Ld4lExtentType.superClass());
            entity.addAttribute(Ld4lDatatypeProp.LABEL, subfield.getTextValue());
            parent.addRelationship(Ld4lObjectProp.HAS_EXTENT, entity);
        }
        
        return entity;       
    }

}

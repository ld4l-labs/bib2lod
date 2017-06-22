package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;

/**
 * Builds an Identifier for a bib resource from a field in the record.
 */
public class MarcxmlToLd4lIdentifierBuilder extends BaseEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private MarcxmlField field;
    private Entity bibEntity;
    private Entity identifier;
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.bibEntity = params.getRelatedEntity();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without a related entity.");
        }
        
        this.field = (MarcxmlField) params.getField();
        if (field == null) {
            throw new EntityBuilderException(
                    "Cannot build identifier without an input field.");
        }
        
        this.identifier = new Entity(Ld4lIdentifierType.superClass());
        
        if (field instanceof MarcxmlControlField) {
            buildFromControlField();
        } else {
            buildFromDataField();
        }

        bibEntity.addRelationship(
                Ld4lObjectProp.IDENTIFIED_BY, identifier);
 
        return identifier;
    }
    
    /**
     * Builds an Identifier from the 001 control field. Returns null if 
     * this.field is not an 001 control field.
     */   
    private void buildFromControlField() {
        
        if (((MarcxmlControlField) field).getControlNumber() == 1) {
            identifier.addType(Ld4lIdentifierType.LOCAL);
            String label = field.getTextValue();
            identifier.addAttribute(Ld4lDatatypeProp.VALUE, label);    
            if (bibEntity instanceof InstanceEntity) {
                ((InstanceEntity) bibEntity).setBibId(label);
            }
        }
        
        // TODO Are there other control fields that contain identifiers?  
    }
    
    private void buildFromDataField() {
        throw new RuntimeException("Method not implemented");
    }

}

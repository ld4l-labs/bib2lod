package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.Entity;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.record.RecordField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;

/**
 * Builds an Identifier for a bib resource from a field in the record.
 */
public class MarcxmlToLd4lIdentifierBuilder extends MarcxmlToLd4lEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private MarcxmlField field;
    private Entity bibEntity;
    private Entity identifier;
    
    public Entity build(BuildParams params) {

        this.bibEntity = params.getRelatedEntity();
        this.field = (MarcxmlField) params.getField();
        this.identifier = new Entity(Ld4lIdentifierType.superClass());
        
        if (field instanceof MarcxmlControlField) {
            buildFromControlField();
        } else {
            buildFromDataField();
        }

        bibEntity.addChild(Ld4lObjectProp.IS_IDENTIFIED_BY, identifier);
 
        return identifier;
    }
    
    /**
     * Builds an Identifier from the 001 control field. Returns null if 
     * this.field is not an 001 control field.
     */   
    private void buildFromControlField() {
        
        if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
            identifier.addType(Ld4lIdentifierType.LOCAL);
            identifier.addAttribute(Ld4lDatatypeProp.VALUE, field.getTextValue());       
        }
        
        // TODO Are there other control fields that contain identifiers?  
    }
    
    private void buildFromDataField() {
        throw new RuntimeException("Method not implemented");
    }

}

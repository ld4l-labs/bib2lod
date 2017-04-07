package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    
    private final MarcxmlField field;
    private final Entity bibEntity;
    
    /**
     * Constructor
     * @param field - the relevant field in the record
     * @param bibEntity - the related entity 
     * @throws EntityBuilderException 
     */
    public MarcxmlToLd4lIdentifierBuilder(RecordField field, Entity bibEntity) 
            throws EntityBuilderException {
        this.field = (MarcxmlField) field;
        this.bibEntity = bibEntity;
    }
     
    public Entity build() {

        Entity identifier;
        
        if (field instanceof MarcxmlControlField) {
            identifier = buildFromControlField();
        } else {
          identifier = buildFromDataField();
        }

        bibEntity.addChild(Ld4lObjectProp.IDENTIFIED_BY, identifier);
 
        return identifier;
    }
    
    /**
     * Builds an Identifier from the 001 control field. Returns null if 
     * this.field is not an 001 control field.
     */   
    private Entity buildFromControlField() {
        
        if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
            Entity identifier = new Entity(Ld4lIdentifierType.superClass());
            identifier.addType(Ld4lIdentifierType.LOCAL);
            identifier.addAttribute(Ld4lDatatypeProp.VALUE, field.getTextValue());
            return identifier;             
        }
        
        // TODO Are there other control fields that contain identifiers?
   
        return null;     
    }
    
    private Entity buildFromDataField() {
        throw new RuntimeException("Method not implemented");
    }

}

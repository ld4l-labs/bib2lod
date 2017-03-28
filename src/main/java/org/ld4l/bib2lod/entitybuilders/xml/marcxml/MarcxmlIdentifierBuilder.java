package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.EntityInterface;
import org.ld4l.bib2lod.entities.Identifier;
import org.ld4l.bib2lod.entities.Identifier.IdentifierType;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.resources.Entity;

public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Construct a new identifier from a control field.
     * @param field - the record field 
     * @param instance - the related Instance
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(MarcxmlField field, Entity bibEntity) 
            throws EntityBuilderException {
        super(field, bibEntity);
    }
     
    public List<Entity> build() {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        Entity identifier;
        
        identifier = buildFromControlField();
        
        if (identifier == null) {
            // TODO Get identifier from a datafield
        }

//        if (identifier != null) {
//            ((BibEntity) relatedEntity).addIdentifier(identifier); 
//            entities.add(identifier);
//        }
        
        return entities;
    }
    
    /**
     * Builds an Identifier from the 001 controlfield. Returns null if 
     * this.field is not an 001 controlfield.
     */   
    private Entity buildFromControlField() {
        
//        if (field instanceof MarcxmlControlField) {
//            if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
//                Entity identifier = new Identifier();
//                identifier.addType(IdentifierType.LOCAL);
//                identifier.setRdfValue(field.getTextValue());
//                return identifier;
//            }           
//        }    
        return null;
    }

}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Identifier;
import org.ld4l.bib2lod.entities.Identifier.IdentifierType;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;

public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Construct a new identifier from a control field.
     * @param field - the record field 
     * @param instance - the related Instance
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(MarcxmlField field, BibEntity bibEntity) 
            throws EntityBuilderException {
        super(null, field, bibEntity);
    }
     
    public List<Entity> build() {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        Identifier identifier;
        
        identifier = buildFromControlField();
        
        if (identifier == null) {
            // TODO Get identifier from a datafield
        }

        if (identifier != null) {
            ((BibEntity) relatedEntity).addIdentifier(identifier); 
            // Don't add to entities here - then they get gets added twice.
        }
        
        return entities;
    }
    
    /**
     * Builds an Identifier from the 001 controlfield. Returns null if 
     * this.field is not an 001 controlfield.
     */   
    private Identifier buildFromControlField() {
        
        if (field instanceof MarcxmlControlField) {
            if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
                Identifier identifier = new Identifier();
                identifier.addType(IdentifierType.LOCAL);
                identifier.setRdfValue(field.getTextValue());
                return identifier;
            }           
        }    
        return null;
    }

}

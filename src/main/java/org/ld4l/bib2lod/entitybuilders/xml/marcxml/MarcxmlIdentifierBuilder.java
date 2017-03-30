package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.apache.jena.vocabulary.RDF;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Link;
import org.ld4l.bib2lod.ontology.IdentifierClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.record.RecordField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;

/**
 * Builds an Identifier for a bib resource from a field in the record.
 */
public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private final MarcxmlField field;
    private final Entity bibEntity;
    
    /**
     * Constructor
     * @param field - the relevant field in the record
     * @param bibEntity - the related entity 
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(RecordField field, Entity bibEntity) 
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

        bibEntity.addChild(
                OntologyProperty.IDENTIFIED_BY.link(), identifier);
  
        return identifier;
    }
    
    /**
     * Builds an Identifier from the 001 control field. Returns null if 
     * this.field is not an 001 control field.
     */   
    private Entity buildFromControlField() {
        
        if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
            Entity identifier = Entity.instance(IdentifierClass.superClass());
            identifier.addType(IdentifierClass.LOCAL);
            Link link = Link.instance(RDF.value);
            identifier.addAttribute(link, field.getTextValue());
            return identifier;             
        }
        
        // TODO Are there other control fields that contain identifiers?
   
        return null;     
    }
    
    private Entity buildFromDataField() {
        throw new RuntimeException("Method not implemented");
    }

}

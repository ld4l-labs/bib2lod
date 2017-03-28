package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

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

public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private final MarcxmlField field;
    private final Entity bibEntity;
    
    /**
     * Construct a new identifier from a control field.
     * @param fields - the relevant fields in the record
     * @param instance - the related Instance
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(RecordField field, Entity bibEntity) 
            throws EntityBuilderException {
        this.field = (MarcxmlField) field;
        this.bibEntity = bibEntity;
    }
     
    public List<Entity> build() {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        Entity identifier;
        
        identifier = buildFromControlField();
        
        if (identifier == null) {
            //identifier = buildFromDataField();
        }
        
        if (identifier != null) {
            bibEntity.addChild(
                    OntologyProperty.IDENTIFIED_BY.link(), identifier);
            entities.add(identifier);
        }
  
        return entities;
    }
    
    /**
     * Builds an Identifier from the 001 controlfield. Returns null if 
     * this.field is not an 001 controlfield.
     */   
    private Entity buildFromControlField() {
        
        if (field instanceof MarcxmlControlField) {
            if (((MarcxmlControlField) field).getControlNumber().equals("001")) {
                Entity identifier = Entity.instance(IdentifierClass.superClass());
                identifier.addType(IdentifierClass.LOCAL);
                Link link = Link.instance(RDF.value);
                identifier.addAttribute(link, field.getTextValue());
                
            }
        }    
        return null;
    }
    
    private Entity buildFromDataField() {
        throw new RuntimeException("Method not implemented");
    }

}

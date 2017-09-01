package org.ld4l.bib2lod.entitybuilders.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.ObjectProp;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.XmlTextElement;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class MarcxmlEntityBuilder extends BaseEntityBuilder {
    
/*
 * TODO
 * Record as a whole - done
 * Field as a whole
 * Field with one or more specific subfields
 * Field iterating through each subfield (e.g., each subfield generates a distinct entity
 * Field iterating through all subfields  (or is that just field as a whole?)
 */

    private static final Logger LOGGER = LogManager.getLogger();
    
    private Entity parent;
    private DatatypeProp property;
    private ObjectProp relationship;
    private MarcxmlSubfield subfield;
    private Type type;
    private String value;

    @Override
    /**
     * Builds a resource with a single datatype property. Used to generalize
     * standard builds without requiring a more specific builder. Called
     * from a subclass using super.build() to build a child entity.
     * 
     * TODO Could we extend this to also be able to add an object property
     * assertion? One way is to set a build param for child params, which
     * would include type and other information. Could get too complicated. 
     */
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        parseBuildParams(params);
        
        Entity entity = new Entity(type);
        
        if (value == null) {
            value = subfield.getTrimmedTextValue();                  
        }
        
        entity.addAttribute(property, value);
        
        parent.addRelationship(relationship, entity);
        
        return entity;
    }
    
    private void reset() {
        this.parent = null;
        this.property = null;
        this.relationship = null;
        this.subfield = null;
        this.type = null;
        this.value = null;        
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.type = params.getType();
        if (type == null) {
            throw new EntityBuilderException(
                    "A type is required to build this entity.");
        }
        
        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException("A parent entity is " + 
                    "required to build this entity.");
        }
        
        this.property = params.getProperty();
        if (property == null) {
                throw new EntityBuilderException("A datatype property is " + 
                        "required to build this entity.");
        }            
        
        this.subfield = (MarcxmlSubfield) params.getSubfield();
        this.value = params.getValue(); 
        
        if (subfield == null && value == null) {
            throw new EntityBuilderException("A subfield or string value " + 
                    "is required to build an agent.");
        }
        
        this.relationship = params.getRelationship();
        if (relationship == null) {
            throw new EntityBuilderException("A relationship to the " +
                    "parent entity is required to build this entity.");
        }
    }
    
    /*
     * Utility methods to build a child of the current Entity 
     */
    
    protected Entity buildChildFromRecord(Type type, Entity parent, 
            MarcxmlRecord record) throws EntityBuilderException {

        EntityBuilder builder = getBuilder(type);
 
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setRecord(record);
        return builder.build(params); 
    }
    
    protected Entity buildChildFromControlField(Type type, Entity parent, 
            MarcxmlRecord record, int tag) throws EntityBuilderException {
        
        MarcxmlControlField field = record.getControlField(tag);
        if (field == null) {
            return null;
        }
        
        EntityBuilder builder = getBuilder(type);
        
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setField(field);
        
        return builder.build(params);
    }
    
    /*
     * Utility methods to build the current Entity.
     */
    
    protected Entity buildFromTextField(Type type,  
            Ld4lDatatypeProp property, XmlTextElement textField) {
        
        Entity entity = new Entity();
        entity.addAttribute(property, textField.getTextValue());
        
        return entity;
    }
    
    protected Entity buildFromString(
            Type type, Ld4lDatatypeProp property, String value) {
        
        Entity entity = new Entity(type);
        entity.addAttribute(property, value);
        
        return entity;
    }
   
   
}

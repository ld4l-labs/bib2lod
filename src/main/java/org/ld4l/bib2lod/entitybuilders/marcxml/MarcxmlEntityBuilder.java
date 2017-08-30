package org.ld4l.bib2lod.entitybuilders.marcxml;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public abstract class MarcxmlEntityBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /*
     * Consider: store instance variables here (with generic names like
     * parent, entity, etc.) rather than in subclasses. Then can do reset()
     * here. First check whether there might be problems with, say,
     * different types. (E.g., Ld4lActivityType has a label() method.)
     * 
     * convert non-repeating field - pass in field
     * convert non-repeating field - pass in field & subfield
     * Convert non-repeating field with repeating subfield ?
     * 
     * convert repeating field - pass in field
     * convert repeating field - pass in field & subfield
     * convert repeating field with repeating subfield 
     * convert repeating field with non-repeating subfield ?
     * 
     * repeat all with a list of subfields? 
     */

    protected void buildEntityFromRecord(Type type, Entity parent, 
            MarcxmlRecord record) throws EntityBuilderException {

        EntityBuilder builder = getBuilder(type);
 
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setRecord(record);
        builder.build(params); 
    }
    
    protected void convertFields(Type type, Entity parent, 
            MarcxmlRecord record, int tag) throws EntityBuilderException {
        
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setType(type);
                     
        if (MarcxmlDataField.isRepeating(tag)) {
            List<MarcxmlDataField> fields = record.getDataFields(tag);
            if (fields.isEmpty()) {
                return;
            }
            for (MarcxmlDataField field : fields) {
                convertField(type, field, params);
            }   
        } else {
            MarcxmlDataField field = record.getDataField(tag);
            if (field != null) {
                convertField(type, field, params);
            }
        }
         
    }
    
    // TODO We don't always want to go through the subfields...
    protected void convertField(Type type, MarcxmlDataField field, 
            BuildParams params) throws EntityBuilderException {
        params.setField(field);
        convertSubfields(type, field, params);        
    }
    
    protected void convertSubfields(Type type, MarcxmlDataField field, 
            BuildParams params) throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(type);        
        List<MarcxmlSubfield> subfields = field.getSubfields();
        for (MarcxmlSubfield subfield : subfields) {
            params.setSubfield(subfield);
            builder.build(params);                
        }        
    }
}

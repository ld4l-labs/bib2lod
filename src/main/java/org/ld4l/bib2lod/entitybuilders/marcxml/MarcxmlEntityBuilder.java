package org.ld4l.bib2lod.entitybuilders.marcxml;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public abstract class MarcxmlEntityBuilder extends BaseEntityBuilder {
    
/*
 * TODO
 * Record as a whole - done
 * Field as a whole
 * Field with one or more specific subfields
 * Field iterating through each subfield (e.g., each subfield generates a distinct entity
 * Field iterating through all subfields  (or is that just field as a whole?)
 */

    private static final Logger LOGGER = LogManager.getLogger();

    protected void buildEntityFromRecord(Type type, Entity parent, 
            MarcxmlRecord record) throws EntityBuilderException {

        EntityBuilder builder = getBuilder(type);
 
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setRecord(record);
        builder.build(params); 
    }
    
    protected void convertControlField(Type type, Entity parent, 
            MarcxmlRecord record, int tag) throws EntityBuilderException {
        
        MarcxmlControlField field = record.getControlField(tag);
        if (field == null) {
            return;
        }
        
        EntityBuilder builder = getBuilder(type);
        
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setField(field);
        
        builder.build(params);
    }

/*    
    protected void convertDataField(Type type, Entity parent, 
            MarcxmlRecord record, int tag) throws EntityBuilderException {
        
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setType(type);
                     
        // Use a loop even for non-repeating fields; the MARC is responsible
        // for maintaining the cardinality constraints.
        List<MarcxmlDataField> fields = record.getDataFields(tag);
        if (fields.isEmpty()) {
            return;
        }
        
        EntityBuilder builder = getBuilder(type); 
        
        for (MarcxmlDataField field : fields) {
            params.setField(field);
            builder.build(params);
        } 
    }
*/
 
/*
    protected void convertSubfields(Type type, MarcxmlDataField field, 
            BuildParams params) throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(type);        
        List<MarcxmlSubfield> subfields = field.getSubfields();
        for (MarcxmlSubfield subfield : subfields) {
            params.setSubfield(subfield);
            builder.build(params);                
        }        
    }
*/
    
}

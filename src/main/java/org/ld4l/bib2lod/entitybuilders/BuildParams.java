package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.RecordField;

public class BuildParams {

    private Record record;
    private RecordField field;
    private RecordField subfield;
    private Entity relatedEntity;
    private Type type;
    private String value;
    
    public BuildParams() {
        this.record = null;
        this.field = null;
        this.subfield = null;
        this.relatedEntity = null;
        this.type = null;
        this.value = null;
    }

    /**
     * Returns null if no record has been set.
     */
    public Record getRecord() {
        return record;
    }

    public BuildParams setRecord(Record record) {
        this.record = record;
        // Return this for method chaining
        return this;
    }
    
    /**
     * Returns null if no field has been set.
     */
    public RecordField getField() {
        return field;
    }

    public BuildParams setField(RecordField field) {
        this.field = field;
        // Return this for method chaining
        return this;
    }
    
    /**
     * Returns null if no subfield has been set.
     */
    public RecordField getSubfield() {
        return subfield;
    }

    public BuildParams setSubfield(RecordField subfield) {
        this.subfield = subfield;
        // Return this for method chaining
        return this;
    }

    /**
     * Returns null if no related entity has been set.
     */
    public Entity getRelatedEntity() {
        return relatedEntity;
    }

    public BuildParams setRelatedEntity(Entity relatedEntity) {
        this.relatedEntity = relatedEntity;
        // Return this for method chaining
        return this;
    }

    /**
     * Returns null if no type has been set.
     */
    public Type getType() {
        return type;
    }

    public BuildParams setType(Type type) {
        this.type = type;
        // Return this for method chaining
        return this;
    }
    
    /**
     * Returns null if no value has been set.
     */
    public String getValue() {
        return value;
    }

    public BuildParams setValue(String value) {
        this.value = value;
        // Return this for method chaining
        return this;
    }
}

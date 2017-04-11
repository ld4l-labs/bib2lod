package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.RecordField;

public class BuildParams {

    private Record record;
    private RecordField field;
    private Entity relatedEntity;
    private Type type;
    

    public RecordField getField() {
        return field;
    }

    public BuildParams setField(RecordField field) {
        this.field = field;
        // Return this for method chaining
        return this;
    }

    public Record getRecord() {
        return record;
    }

    public BuildParams setRecord(Record record) {
        this.record = record;
        // Return this for method chaining
        return this;
    }

    public Entity getRelatedEntity() {
        return relatedEntity;
    }

    public BuildParams setRelatedEntity(Entity relatedEntity) {
        this.relatedEntity = relatedEntity;
        // Return this for method chaining
        return this;
    }

    public Type getType() {
        return type;
    }

    public BuildParams setType(Type type) {
        this.type = type;
        // Return this for method chaining
        return this;
    }
}

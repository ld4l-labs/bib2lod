package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.RecordField;

public class BuildParams {

  
    private RecordField field;
    // Parent of the parent; e.g., the bib resource of an Activity of an 
    // Agent
    private Entity grandparent; 
    // The "parent" Entity of this Entity: e.g., the Instance of a Title or
    // PublicationActivity
    private Entity parent;
    private Record record; 
    private RecordField subfield;
    private Type type;
    private String value;
    
    // Use to send multiple subfields, with codes. Currently not using.
    // private MapOfLists<Character, String> subfieldMap;
    
    /**
     * Constructor
     */
    public BuildParams() {
        this.field = null;
        this.grandparent = null;
        this.parent = null;
        this.record = null;
        this.subfield = null;
        this.type = null;
        this.value = null;      
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
     * Returns null if no grandparent entity has been set.
     */
    public Entity getGrandparent() {
        return grandparent;
    }

    public BuildParams setGrandparent(Entity grandparent) {
        this.grandparent = grandparent;
        // Return this for method chaining
        return this;
    }

    /**
     * Returns null if no parent entity has been set.
     */
    public Entity getParent() {
        return parent;
    }

    public BuildParams setParent(Entity parent) {
        this.parent = parent;
        // Return this for method chaining
        return this;
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

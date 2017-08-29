package org.ld4l.bib2lod.entitybuilders;

import java.util.ArrayList;
import java.util.List;

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
    private List<RecordField> subfields;
    private Type type;
    private String value;

    /**
     * Constructor
     */
    public BuildParams() {
        this.field = null;
        this.grandparent = null;
        this.parent = null;
        this.record = null;
        this.subfields = new ArrayList<>();
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
     * Returns an empty List if there are no subfields; never returns null.
     * @return
     */
    public List<RecordField> getSubfields() {
        return subfields;
    }
    
    /**
     * Returns the first subfield in subfields. Returns null if the list is
     * empty.
     */
    public RecordField getSubfield() {
        return getSubfield(0);
    }
  
    /**
     * Returns the subfield at the specified index in the subfields list.
     * Returns null if index is out of bounds.
     */
    public RecordField getSubfield(int index) {
        try {
            return subfields.get(index);
        // TODO - is this what we want, or just throw the exception?
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    /**
     * Add the subfield to the list of subfields.
     */
    public BuildParams addSubfield(RecordField subfield) {
        this.subfields.add(subfield);
        // Return this for method chaining
        return this;
    }
    
    /**
     * Add the list of subfields to this list of subfields.
     */
    public BuildParams addSubfields(List<RecordField> subfields) {
        this.subfields.addAll(subfields);
        // Return this for method chaining
        return this;
    }
    
    public BuildParams setSubfield(RecordField subfield) {
        this.subfields.clear();
        subfields.add(subfield);
        // Return this for method chaining
        return this;
    }
    
    public BuildParams setSubfields(List<RecordField> subfields) {
        this.subfields = subfields;
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

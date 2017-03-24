package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public class MarcxmlEntityBuilder implements EntityBuilder {
    
    protected final MarcxmlRecord record;
    protected final MarcxmlField field;

    /**
     * Constructor used when the EntityBuilder requires reference to the entire
     * record and/or a specific field to build the Entity (e.g., an Instance
     * requires the entire Record, a Title requires the Record and a specific
     * data field), an Identifier requires only a specific field. One of the
     * arguments but not both may be null.
     * @throws EntityBuilderException 
     */
    public MarcxmlEntityBuilder(MarcxmlRecord record, MarcxmlField field) 
            throws EntityBuilderException {
        
        if (record == null && field == null) {
            throw new EntityBuilderException("A Record and/or a field must " +
                    "be provided");
        }
        this.record = record;
        this.field = field;     
    }
    

    @Override
    public Entity build() throws EntityBuilderException {
        // TODO Auto-generated method stub
        return null;
    }

}

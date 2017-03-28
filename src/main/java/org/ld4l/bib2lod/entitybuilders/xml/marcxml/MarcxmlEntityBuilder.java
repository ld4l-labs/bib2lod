package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

public abstract class MarcxmlEntityBuilder extends BaseEntityBuilder {

    /**
     * Constructor
     */
    public MarcxmlEntityBuilder(Record record) {
        super(record);
    }
    
    /**
     * Constructor
     */
    public MarcxmlEntityBuilder(MarcxmlRecord record, Entity relatedEntity) {
        super(record, null, relatedEntity);
    }

    /**
     * Constructor
     */
    public MarcxmlEntityBuilder(MarcxmlRecord record, MarcxmlField field,
            Entity relatedEntity) {
        super(record, field, relatedEntity);
    }
    
    /**
     * Constructor
     */
    public MarcxmlEntityBuilder(MarcxmlField field, Entity relatedEntity) {
        super(null, field, relatedEntity);
    }

    /**
     * Constructor. Requires a full Record and/or a specific field in the 
     * Record and/or a related Entity. Examples: The full Record is required to 
     * build an Instance. A data field and the related Instance are required to
     * build an Identifier. The full record and the related Instance are 
     * required to build a Work. 
     * @throws EntityBuilderException 
     */
//    public MarcxmlEntityBuilder(MarcxmlRecord record, MarcxmlField field, 
//            EntityInterface relatedEntity) 
//            throws EntityBuilderException {
//        
//        if (record == null && field == null && relatedEntity == null) {
//            throw new EntityBuilderException("A Record and/or a field and/or " +
//                    "a related Entity must be provided");
//        }
//        this.record = record;
//        this.field = field;   
//        this.relatedEntity = relatedEntity;
//    }


    

}

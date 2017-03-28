/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.List;

import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.Field;
import org.ld4l.bib2lod.resources.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseEntityBuilder implements EntityBuilder {
    
    protected Record record;
    protected Field field;
    protected Entity relatedEntity;
    // ?? protected Entity entity - the entity being build

    /**
     * Constructor
     * @param record - the Record from which to build the Entity
     */
    public BaseEntityBuilder(Record record) {
        this(record, null, null);
    }
    
    /**
     * Constructor
     * @param record - the Record from which to build the Entity
     * @param element - the specific field in the record from which this Entity
     * will be built
     * @param relatedEntity - a related Entity (such as an Instance related to
     * an Identifier
     */
    public BaseEntityBuilder(Record record, 
            Field field, Entity relatedEntity) {
        this.record = record;
        this.field = field;
        this.relatedEntity = relatedEntity;
    }
    
    /**
     * Constructor
     * @param record - the Record from which to build the Entity
     * @param relatedEntity - an Entity to link the new Entity to
     */
    // TODO Probably don't need - implementing classes need to define the
    // type of related entity they are expecting.
//    public BaseEntityBuilder(Record record, Entity relatedEntity) {
//        this.record = record;
//        this.relatedEntity = relatedEntity;
//    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build()
     */
    @Override
    public abstract List<Entity> build() throws EntityBuilderException;

}

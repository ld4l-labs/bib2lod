/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.List;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.record.Record;

/**
 * An abstract implementation.
 */
public abstract class BaseEntityBuilder implements EntityBuilder {
    
    // *** NOT CURRENTLY USED. ENTITY BUILDERS NEED TO STORE THE RECORD AS THE
    // SPECIFIC TYPE - E.G., MarcxmlRecord, etc.
    protected Record record;
    //protected Entity relatedEntity;

    /**
     * Constructor
     * @param record - the Record from which to build the Entity
     */
    public BaseEntityBuilder(Record record) {
        this.record = record;
        //this.relatedEntity = null;
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

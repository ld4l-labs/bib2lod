/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.record.Field;
import org.ld4l.bib2lod.record.Record;

/**
 * Builds an Entity from a Record
 */
public interface EntityBuilder {
    
    public static class EntityBuilderException extends RecordConversionException {
        private static final long serialVersionUID = 1L;

        public EntityBuilderException(String message, Throwable cause) {
            super(message, cause);
        }

        public EntityBuilderException(String message) {
            super(message);
        }

        public EntityBuilderException(Throwable cause) {
            super(cause);
        }
    }  
    
    /**
     * Factory methods
     */
    public static EntityBuilder instance(Class<?> builderClass, Record record) {
        return Bib2LodObjectFactory.instance().createEntityBuilder(
                builderClass, record);
    }
    
    public static EntityBuilder instance(Class<?> builderClass, 
            Field field, Entity relatedEntity) {
        return Bib2LodObjectFactory.instance().createEntityBuilder(
                builderClass, field, relatedEntity);
    }

    
    /**
     * Builds an Entity.
     * @throws EntityBuilderException 
     */
    // TODO It remains to be seen whether this should return a single Entity or
    // a list of Entities. That is, might we build more than one Entity from a
    // single EntityBuilder.build() call, or will the additional Entities always
    // be built from a call to another EntityBuilder's build() method? For now,
    // define return type as a list for flexibility. If not needed, change to
    // Entity.
    public List<Entity> build() throws EntityBuilderException;

}

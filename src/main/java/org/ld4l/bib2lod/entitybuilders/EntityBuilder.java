/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.Field;

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
     * Factory method
     */
    public static EntityBuilder instance(Class<?> builderClass, Record record) {
        return Bib2LodObjectFactory.instance().createEntityBuilder(
                builderClass, record);
    }
    
    /**
     * Factory method
     */
    public static EntityBuilder instance(Class<?> builderClass, Record record, 
            Field field, Entity relatedEntity) {
        return Bib2LodObjectFactory.instance().createEntityBuilder(
                builderClass, record, field, relatedEntity);
    }
    
    /**
     * Builds an Entity.
     * @throws EntityBuilderException 
     */
    public List<Entity> build() throws EntityBuilderException;

}

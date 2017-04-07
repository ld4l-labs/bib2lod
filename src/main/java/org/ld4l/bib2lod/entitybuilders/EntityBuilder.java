/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.conversion.Converter.RecordConversionException;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.RecordField;

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
        return Bib2LodObjectFactory.getFactory().createEntityBuilder(
                builderClass, record);
    }
        
    public static EntityBuilder instance(Class<?> builderClass, 
            RecordField field, Entity relatedEntity) {
        return Bib2LodObjectFactory.getFactory().createEntityBuilder(
                builderClass, field, relatedEntity);
    }
    
    public static EntityBuilder instance(Class<?> builderClass, Record record, 
            Entity relatedEntity) {
        return Bib2LodObjectFactory.getFactory().createEntityBuilder(
                builderClass, record, relatedEntity);
    }

    
    /**
     * Builds an Entity, including its dependent Entities (e.g., Identifiers
     * and Titles of Works and Instances).
     * @throws EntityBuilderException 
     */
    public Entity build() throws EntityBuilderException;

}

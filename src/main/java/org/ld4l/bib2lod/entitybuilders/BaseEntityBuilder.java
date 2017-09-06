/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * An abstract implementation.
 */
public abstract class BaseEntityBuilder implements EntityBuilder { 
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Override
    public EntityBuilder getBuilder(Type type) {
        EntityBuilderFactory factory = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilderFactory.class);
        return factory.getBuilder(type);
// If no builder defined for this type, use builder for its supertype
//        EntityBuilder builder = factory.getBuilder(type);
//        if (builder != null) {
//            return builder;
//        }
//        Type superType = type.superType();
//        if (superType != null) {
//            return factory.getBuilder(superType);
//        }
//        return null;
    }
    
}

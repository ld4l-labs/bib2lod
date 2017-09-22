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
    public EntityBuilder getBuilder(Type type) 
            throws EntityBuilderException {
        
        EntityBuilderFactory factory = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilderFactory.class);
        
        // Get builder for the specified type
        EntityBuilder builder = factory.getBuilder(type);
        if (builder != null) {
            return builder;
        }

        // If no builder defined for this type, use builder for its
        // superclass
        Type superclass = type.superclass();
        if (superclass != null) {
            return factory.getBuilder(superclass);
        }
        
        throw new EntityBuilderException("No builder defined for type " + 
                type.getClass().getCanonicalName() + ".");
    }
    
}

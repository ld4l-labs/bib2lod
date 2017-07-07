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
    
    private static final Logger LOGGER = LogManager.getLogger();
 
    @Override
    public EntityBuilder getBuilder(Class<? extends Type> type) {
        EntityBuilderFactory factory = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilderFactory.class);
        return factory.getBuilder(type);
    }
    
}

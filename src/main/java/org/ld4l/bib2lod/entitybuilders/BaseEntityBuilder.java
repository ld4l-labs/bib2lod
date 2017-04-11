/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.ontology.Type;

/**
 * An abstract implementation.
 */
public abstract class BaseEntityBuilder implements EntityBuilder {
    
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build()
     */
//    @Override
//    public abstract Entity build(Map<String, Object> params) 
//            throws EntityBuilderException;
//        
    
    @Override
    public EntityBuilder getBuilder(Class<? extends Type> type) {
        EntityBuilders builders = Bib2LodObjectFactory.getFactory()
                .instanceForInterface(EntityBuilders.class);
        return builders.getBuilder(type);
    }
}
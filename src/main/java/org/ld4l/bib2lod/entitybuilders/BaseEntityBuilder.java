/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.util.List;

import org.ld4l.bib2lod.entities.Entity;

/**
 * An abstract implementation.
 */
public abstract class BaseEntityBuilder implements EntityBuilder {
   
    // The Entity being built
    protected Entity entity; 

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.EntityBuilder#build()
     */
    @Override
    public abstract List<Entity> build() throws EntityBuilderException;

}

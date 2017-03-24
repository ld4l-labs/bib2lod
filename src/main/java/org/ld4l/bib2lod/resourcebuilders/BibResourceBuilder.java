/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;

/**
 *
 */
public abstract class BibResourceBuilder extends BaseResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Constructor
     */
    public BibResourceBuilder(Entity entity) {
        super(entity);
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.resourcebuilders.BaseResourceBuilders#build()
     */
    @Override
    public Resource build() {
        return super.build();
    }

}

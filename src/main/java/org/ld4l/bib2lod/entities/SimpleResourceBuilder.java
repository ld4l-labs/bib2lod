/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An abstract implementation.
 */
public class SimpleResourceBuilder implements ResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    protected Entity entity;

    /**
     * Constructor
     */
    public SimpleResourceBuilder(Entity entity) {
        this.entity = entity;
    }

    @Override
    public Resource build() {
        // TODO Auto-generated method stub
        throw new RuntimeException("Method not implemented");
    }

}

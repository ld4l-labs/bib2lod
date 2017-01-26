/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import org.ld4l.bib2lod.configuration.Configuration;

/**
 *
 */
public abstract class BaseEntityBuilder implements EntityBuilder {
    
    protected Configuration configuration;

    /**
     * Constructor
     */
    public BaseEntityBuilder(Configuration configuration) {
        this.configuration = configuration;
    }
    



}

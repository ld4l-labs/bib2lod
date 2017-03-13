/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.modelbuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.uris.UriGetter;

/**
 * Builds a model for an Instance entity.
 */
public class InstanceModelBuilder extends BaseModelBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor
     * @param resource - the Entity from which to build the model
     * @param configuration - the program configuration
     */
    public InstanceModelBuilder(
            Entity resource, Configuration configuration) {
        super(resource, configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.modelbuilders.ModelBuilder#build()
     */
    @Override
    public Model build() {
        
        Resource instance = model.createResource(UriGetter.getUri(entity));
        
        addTypes(instance);

        return model;
    }

}

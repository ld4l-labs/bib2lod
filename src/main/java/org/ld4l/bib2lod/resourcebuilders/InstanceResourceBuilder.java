/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Instance;

/**
 * Builds a model for an Instance entity.
 */
public class InstanceResourceBuilder extends BibResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor
     * @param resource - the Entity from which to build the Resource
     */
    public InstanceResourceBuilder(Instance instance, Model model) {         
        super(instance, model);
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.resourcebuilders.ResourceBuilder#build()
     */
    @Override
    public Resource build() {     
        Resource resource = super.build();
        
        return resource;
    }

}

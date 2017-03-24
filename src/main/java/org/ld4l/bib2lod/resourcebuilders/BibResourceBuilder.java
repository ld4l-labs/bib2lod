/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.resourcebuilders;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;
import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Identifier;

/**
 * Builds a Resource representing a bibliographic entity (Work, Instance, or 
 * Item).
 */
public abstract class BibResourceBuilder extends BaseResourceBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();
    
    /**
     * Constructor
     */
    public BibResourceBuilder(Entity entity, Model model) {
        super(entity, model);
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.resourcebuilders.BaseResourceBuilders#build()
     */
    @Override
    public Resource build() {
        super.build();    
        addIdentifiers();
        return resource;
    }
    
    private void addIdentifiers() {
        BibEntity bibEntity = (BibEntity) entity;
        
        Property identifiedBy = ResourceFactory.createProperty(
                Namespace.BIBFRAME.uri() + "identifiedBy");
        
        for (Identifier identifier : bibEntity.getIdentifiers()) {         
            IdentifierResourceBuilder builder = new IdentifierResourceBuilder(identifier, model);
            Resource identifierResource = builder.build();
            resource.addProperty(identifiedBy, identifierResource);
        }
    }

}

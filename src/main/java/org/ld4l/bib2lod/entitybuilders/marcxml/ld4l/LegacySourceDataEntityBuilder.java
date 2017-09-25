package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;

/**
 * Builds an entity used to store unparsed, unnormalized legacy data. Use
 * when no specific type is assigned; if there is a specific type, use the
 * builder for that type and add the datatype to the appropriate literal 
 * value.
 */
public class LegacySourceDataEntityBuilder extends MarcxmlEntityBuilder {

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        String label = params.getValue();
        if (label == null) {
            throw new EntityBuilderException("Cannot build legacy source " +
                    "data entity without a value.");
        }

        Entity entity = new Entity();
        
        entity.addLegacySourceDataAttribute(Ld4lDatatypeProp.LABEL, label);
        
        return entity;
    }
}

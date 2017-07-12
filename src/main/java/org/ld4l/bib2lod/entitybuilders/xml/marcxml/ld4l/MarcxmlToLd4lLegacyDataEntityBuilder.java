package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;

/**
 * Builds an entity used to store unparsed, unnormalized legacy data. May or
 * may not have a specific type assigned.
 */
public class MarcxmlToLd4lLegacyDataEntityBuilder extends BaseEntityBuilder {

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        String label = params.getValue();
        if (label == null) {
            throw new EntityBuilderException(
                    "Cannot build legacy entity without a value.");
        }

        Entity entity = new Entity();
        
        entity.addAttribute(Ld4lDatatypeProp.LABEL, label, 
                BibDatatype.LEGACY_SOURCE_DATA);
        
        Type type = params.getType();
        if (type != null) {
            entity.addType(type);
        }
        
        return entity;
    }
}

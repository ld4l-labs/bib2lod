/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.conversion.xml.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.parsing.xml.marcxml.MarcxmlParser;
import org.ld4l.bib2lod.records.Record;

/**
 * Converts MARCXML records
 */
public class MarcxmlConverter extends BaseConverter{
    
    private static final Logger LOGGER = LogManager.getLogger();


    @Override
    protected Entity buildEntity(Record record) 
            throws EntityBuilderException {
             
        /*
         * The Instance is the fundamental Entity created from the Record.
         * From the InstanceBuilder we create dependent Entities such as 
         * the Titles and Identifiers of the Instance.
         * 
         * TODO This commits us to always creating an Instance, and thus
         * adopting the model in which a painting, for example, is an Instance,
         * Work, and Item simultaneously. Consider how to avoid the dependency
         * on this model. We may need to inspect the leader first to determine
         * what kind of work it is. Interesting dependency of converter on the
         * application profile.
         */       
        EntityBuilder instanceBuilder = getBuilder(Ld4lInstanceType.class);
        BuildParams params = new BuildParams()
                .setRecord(record);
        return instanceBuilder.build(params);

    }
  
}

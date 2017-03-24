/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds an Instance from a Record.
 */
public class MarcxmlInstanceBuilder extends MarcxmlEntityBuilder {

    /**
     * Constructor
     * @throws EntityBuilderException 
     */
    public MarcxmlInstanceBuilder(MarcxmlRecord record) 
            throws EntityBuilderException {
        super(record, null, null);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder#build()
     */
    @Override
    // TODO May want to instead return a list of entities - i.e., all those 
    // emanating from the instance. Depends on how we handle this in the converter.
    public Entity build() throws EntityBuilderException {
        
        Instance instance = new Instance();

        for (MarcxmlControlField field : record.getControlFields()) {
            String controlNumber = field.getControlNumber();
            if (controlNumber.equals("001")) {
                new MarcxmlIdentifierBuilder(field, instance)
                        .build();
            }
            // TODO Assumes this is the only control field that produces an
            // Identifier. Is that correct?
            break;
        }
        
        return instance;
    }



}

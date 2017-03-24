/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Title;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds a Title Entity from a MARCXML record and an Instance.
 */
public class MarcxmlTitleBuilder extends MarcxmlEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger(); 

    private BibEntity bibEntity;
    
    /**
     * Constructor
     * @param record - the MARCXML record
     * @param bibResource - the bib resource to which this title is related
     * @throws EntityBuilderException 
     */
    public MarcxmlTitleBuilder(MarcxmlRecord record, MarcxmlField field, 
            BibEntity bibEntity) throws EntityBuilderException {
        super(record, field);
        this.bibEntity = bibEntity;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        Entity title = Entity.instance(Title.class, bibEntity);
        
        List<MarcxmlDataField> dataFields = record.getDataFields();
        
        return title;
    }
    
    public BibEntity getBibEntity() {
        return bibEntity;
    }

}

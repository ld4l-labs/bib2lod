/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities_deprecated.BibEntity;
import org.ld4l.bib2lod.entities_deprecated.Title;
import org.ld4l.bib2lod.entities_deprecated.TitleElement;
import org.ld4l.bib2lod.entities_deprecated.TitleElement.TitleElementType;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds a Title Entity from a MARCXML record and an Instance.
 */
public class MarcxmlTitleBuilder extends MarcxmlEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private List<MarcxmlDataField> dataFields;
   
    /**
     * Constructor
     * @param record - the MARCXML record
     * @param bibEntity - the bib entity (Work or Instance) of which the title
     * being built is the title
     * @throws EntityBuilderException 
     */
    public MarcxmlTitleBuilder(MarcxmlRecord record, 
            Entity bibEntity) throws EntityBuilderException {
        super(record, bibEntity);
        

    }

    @Override
    public List<Entity> build() throws EntityBuilderException {
        
        List<Entity> entities = new ArrayList<Entity>();
        
        Entity title = Entity.instance();
//        String titleLabel = null;
//        
//        MarcxmlDataField field245 = record.getDataField("245");
//        MarcxmlDataField field130 = record.getDataField("130");
//        MarcxmlDataField field240 = record.getDataField("240");
//        
//        if (field245 != null) {
//            // Full title always comes from 245. If 130 and/or 240 are present,
//            // the $a fields are the same.
//            // 245$a stores full title
//            titleLabel = field245.getSubfield("a").getTextValue();
//        }
        
        // Could there be a 130 or 240 without 245? Then need to look for
        // $a in those fields if no 245.
           
        // Return an empty list if the title has no text value.
//        if (titleLabel != null) {
//            title.setRdfsLabel(titleLabel);
//            
//            // Build TitleElements
//            List<TitleElement> titleElements = 
//                    buildTitleElements(field245, titleLabel);
//            
//            // TODO Add values from 130 or 240
//            // NB If 130 is present, 240 is ignored
//        
//            title.addTitleElements(titleElements);
//            entities.add(title);
//            entities.addAll(titleElements);
//        }

        return entities;
    }
    
    private List<TitleElement> buildTitleElements(
            MarcxmlField field, String titleLabel) {
         
        // TODO: get title  parts from subfields
        // Send each substring to the appropriate method.
        List<TitleElement> titleElements = new ArrayList<TitleElement>();

        // MainTitleElement label = titleLabel minus parts.
        // Temporarily, build only the MainTitleElement and assign it same label
        // as title.
        titleElements.add(new TitleElement(
                TitleElementType.MAIN_TITLE_ELEMENT, titleLabel));
        
        // TODO set ranks
        
        return titleElements;
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.vocabulary.RDFS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Link;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.ontology.TitleClass;
import org.ld4l.bib2lod.ontology.TitleElementClass;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;

/**
 * Builds a Title Entity from a MARCXML record and an Instance.
 */
public class MarcxmlTitleBuilder extends MarcxmlEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private final MarcxmlRecord record;
    private final Entity bibEntity;
   
    /**
     * Constructor
     * @param record - the MARCXML record
     * @param bibEntity - the bib entity (Work or Instance) of which the title
     * being built is the title
     * @throws EntityBuilderException 
     */
    public MarcxmlTitleBuilder(Record record, Entity bibEntity)
             throws EntityBuilderException {
        this.record = (MarcxmlRecord) record;
        this.bibEntity = bibEntity;
    }

    @Override
    public Entity build() throws EntityBuilderException {
        
        // The title
        entity = Entity.instance(TitleClass.superClass());
        
        String titleLabel = null;
      
        // Could there be a 130 or 240 without 245? Then need to look for
        // $a in those fields if no 245.
        
        MarcxmlDataField field245 = record.getDataField("245");
        MarcxmlDataField field130 = record.getDataField("130");
        MarcxmlDataField field240 = record.getDataField("240");
      
        if (field245 != null) {
            // Full title always comes from 245. If 130 and/or 240 are present,
            // the $a fields should be the same.
            // 245$a stores full title
          titleLabel = field245.getSubfield("a").getTextValue();
          entity.addAttribute(OntologyProperty.LABEL.link(), titleLabel); 
        }
        
        // Add other values from 130/240
        
        // After building Title, build TitleElements
        List<Entity> titleElements = buildTitleElements(field245, titleLabel);
        entity.addChildren(OntologyProperty.HAS_PART.link(), titleElements);
        
        bibEntity.addChild(OntologyProperty.TITLE.link(), entity);
        
        return entity;
    }

    
    private List<Entity> buildTitleElements(
            MarcxmlField field, String titleLabel) {
                 
        // TODO: get title  parts from subfields
        // Send each substring to the appropriate method.
        List<Entity> titleElements = new ArrayList<Entity>();

        // MainTitleElement label = titleLabel minus parts.
        // Temporarily, build only the MainTitleElement and assign it the title
        // label
        
        Entity mainTitleElement = Entity.instance(
                TitleElementClass.MAIN_TITLE_ELEMENT);
        mainTitleElement.addAttribute(
                OntologyProperty.LABEL.link(), titleLabel);
        titleElements.add(mainTitleElement);
        
//        titleElements.add(new TitleElement(
//                TitleElementType.MAIN_TITLE_ELEMENT, titleLabel));
        
        // TODO set ranks
        
        return titleElements;
                
    }

}

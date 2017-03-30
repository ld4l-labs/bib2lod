/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.ontology.TitleClass;
import org.ld4l.bib2lod.ontology.TitleElementClass;
import org.ld4l.bib2lod.record.Record;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlSubfield;

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
            for (MarcxmlSubfield subfield : field245.getSubfields()) {
 
                // 245$a always stores the full title. If 130 and/or 240 are 
                // present,the $a fields should be the same.
                if (subfield.getCode().equals("a")) {
                    titleLabel = subfield.getTextValue();
                    entity.addAttribute(OntologyProperty.LABEL.link(), titleLabel);
                }
                
                if (subfield.getCode().equals("c")) {
                    bibEntity.addAttribute(
                            OntologyProperty.RESPONSIBILITY_STATEMENT.link(), 
                            subfield.getTextValue());
                }
                
                // TODO Convert other subfields
            }
        }
        
        // TODO convert other subfields from 130/240
        
        List<Entity> titleElements = buildTitleElements(field245, titleLabel);
        entity.addChildren(OntologyProperty.HAS_PART.link(), titleElements);
        
        // TODO Figure out how to recognize the preferred title vs other titles
        bibEntity.addChild(OntologyProperty.HAS_PREFERRED_TITLE.link(), entity);
        
        return entity;
    }
    
    private List<Entity> buildTitleElements(
            MarcxmlField field, String titleLabel) {
                 
        // TODO: get title  parts from subfields
        // Send each substring to the appropriate method.
        List<Entity> titleElements = new ArrayList<Entity>();
        
        /*
         * Assign ranks as follows:
         * NonSortElement - 0
         * MainTitleElement - 10
         * SubtitleElement - 20
         * PartNumberElement - 30
         * PartNameElement - 40
         * The gaps allow for multiple elements of one type.
         */
        
        // Create MainTitleElement last, since its label is the Title label
        // minus the other element labels.
        Entity mainTitleElement = buildTitleElement(
                TitleElementClass.MAIN_TITLE_ELEMENT, titleLabel, 10); 
        titleElements.add(mainTitleElement);
        
        return titleElements;               
    }
        
    private Entity buildTitleElement(
            TitleElementClass elementClass, String label, int rank) {
        
         Entity titleElement = Entity.instance(elementClass);
         titleElement.addAttribute(OntologyProperty.LABEL.link(), label);
         titleElement.addAttribute(OntologyProperty.RANK.link(), rank);  
         return titleElement;
    }
    
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.datatypes.BibliotekoCustomDatatype.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;


/**
 * Builds a Title Entity from a MARCXML record and an Instance.
 */
public class MarcxmlToLd4lTitleBuilder extends MarcxmlToLd4lEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private MarcxmlRecord record;
    private Entity bibEntity;
    private Entity title;
    

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        this.record = (MarcxmlRecord) params.getRecord();
        this.bibEntity = params.getRelatedEntity();
        this.title = new Entity(Ld4lTitleType.superClass());
        
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
                    title.addAttribute(Ld4lDatatypeProp.LABEL, titleLabel);
                }
                
                if (subfield.getCode().equals("c")) {
                    bibEntity.addAttribute(Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT,
                            subfield.getTextValue());
                }
                
                // TODO Convert other subfields
            }
        }
        
        // TODO convert other subfields from 130/240
        
        List<Entity> titleElements = buildTitleElements(titleLabel);
        title.addRelationships(Ld4lObjectProp.HAS_PART, titleElements);
        
        // TODO Figure out how to recognize the preferred title vs other titles
        bibEntity.addRelationship(Ld4lObjectProp.HAS_PREFERRED_TITLE, title);
        
        return title;
    }
    
    private List<Entity> buildTitleElements(String titleLabel) {
                 
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
                Ld4lTitleElementType.MAIN_TITLE_ELEMENT, titleLabel, 10); 
        titleElements.add(mainTitleElement);
        
        return titleElements;               
    }
        
    private Entity buildTitleElement(
            Ld4lTitleElementType elementClass, String label, int rank) {
        
         Entity titleElement = new Entity(elementClass);
         titleElement.addAttribute(Ld4lDatatypeProp.LABEL, label);
         titleElement.addAttribute(Ld4lDatatypeProp.RANK, rank);  
         return titleElement;
    }
    
}

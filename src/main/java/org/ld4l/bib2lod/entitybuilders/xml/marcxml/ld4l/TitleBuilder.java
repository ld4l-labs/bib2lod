/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.ontology.Type;
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
public class TitleBuilder extends BaseEntityBuilder {

    private static final Logger LOGGER = LogManager.getLogger();
    
    private MarcxmlRecord record;
    private Entity bibEntity;
    private Entity title;
    private EntityBuilder titleElementBuilder;
    private List<Entity> titleElements;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build a title.");
        }
 
        this.bibEntity = params.getParentEntity();
        if (bibEntity == null) {
            throw new EntityBuilderException(
                    "A related entity is required to build a title.");
        }
        
        this.title = new Entity(Ld4lTitleType.superClass());
        
        addTitleElements();       
        addTitleValue();
   
        // TODO How to recognize the preferred title vs other titles?
        bibEntity.addRelationship(Ld4lObjectProp.HAS_PREFERRED_TITLE, title);
        
        return title;
    }
    
    private void addTitleElements() throws EntityBuilderException {

        this.titleElementBuilder = getBuilder(Ld4lTitleElementType.superClass());
        
        buildTitleElements();

        // Add rank attribute to title elements
        int rank = 1;
        for (Entity titleElement : titleElements) {
            titleElement.addAttribute(Ld4lDatatypeProp.RANK, rank);
            rank++;
        }
    }

    private void buildTitleElements() throws EntityBuilderException {   
        
        titleElements = new ArrayList<>();
            
        // Note that every record must have a 245
        MarcxmlDataField field245 = record.getDataField(245);   

        for (MarcxmlSubfield subfield : field245.getSubfields()) {
 
            char code = subfield.getCode();
            
            switch (code) {
            // 245$a always stores the full title. If 130 and/or 240 are
            // present,the $a fields should be the same.
            case 'a':
                addNonSortAndMainTitleElements(field245, subfield);
                break;
            case 'b':
                addSubtitleElements(subfield);
                break;
            case 'n':
                addPartNumberAndNameElement(subfield,
                        Ld4lTitleElementType.PART_NUMBER_ELEMENT);
                break;
            case 'p':
                addPartNumberAndNameElement(subfield,
                        Ld4lTitleElementType.PART_NAME_ELEMENT);
                break;
            default:
                // not a title element - do nothing
            }
        }
               
    }
    
    private void addNonSortAndMainTitleElements(MarcxmlDataField field, 
            MarcxmlSubfield subfield) throws EntityBuilderException { 
        
        String subfieldAValue = subfield.getTextValue(); 
        String nonSort = null;
        String main = null;
        
        int ind2 = field.getSecondIndicator();
        if (ind2 > 0) {
            nonSort = subfieldAValue.substring(0, ind2);
            main = subfieldAValue.substring(ind2+1);
        } else {
            main = subfieldAValue;
        }

        if (nonSort != null) {
            BuildParams params = new BuildParams() 
                    .setParentEntity(title)
                    .setValue(nonSort)
                    .setType(Ld4lTitleElementType.NON_SORT_ELEMENT);
            titleElements.add(titleElementBuilder.build(params));
        }
      
        BuildParams params = new BuildParams() 
                .setParentEntity(title)
                .setValue(main)
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT);      
        titleElements.add(titleElementBuilder.build(params));       
    }
    
    private void addSubtitleElements(MarcxmlSubfield subfield) 
            throws  EntityBuilderException {
        
        String text = subfield.getTextValue();
        if (text == null) {
            // Don't throw exception, just don't build a subtitle
            return;
        }

        BuildParams params = new BuildParams() 
                .setParentEntity(title)
                .setType(Ld4lTitleElementType.SUBTITLE_ELEMENT);
        
        /* 
         * E.g., Cornell 3673479
         * $c = "why children kill : the story of Mary Bell"
         */
        String[] values = text.split("\\s*:\\s*");
        for (String value : values) {
            params.setValue(value);
            titleElements.add(titleElementBuilder.build(params));
        }
    }
    
    private void addPartNumberAndNameElement(MarcxmlSubfield subfield, Type type) 
            throws EntityBuilderException {
        
        BuildParams params = new BuildParams() 
                .setParentEntity(title)
                .setType(type)
                .setValue(subfield.getTextValue());
    
        titleElements.add(titleElementBuilder.build(params));
    }
     
    /**
     * Construct title value by concatenating title elements in order.
     */
    private void addTitleValue() {
        
        List<String> elementValues = new ArrayList<>();
        for (Entity titleElement : titleElements) {
            // TODO What about xml:lang value, which could be different for 
            // different title elements?
            Attribute attribute = 
                    titleElement.getAttribute(Ld4lDatatypeProp.VALUE);
            if (attribute == null) {
                continue;
            }
            elementValues.add(attribute.getValue());           
        }             
        
        // Perhaps not so simple: do different element types need a different glue?
        String titleValue = StringUtils.join(elementValues, " : ");
        title.addAttribute(Ld4lDatatypeProp.VALUE, titleValue);
    }

}

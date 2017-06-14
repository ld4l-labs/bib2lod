package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.apache.commons.lang3.StringUtils;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class MarcxmlToLd4lTitleElementBuilder extends BaseEntityBuilder {

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
 
        Entity title = params.getRelatedEntity();
        if (title == null) {
            throw new EntityBuilderException(
                    "A title is required to build a title element.");
        }

        Type type = params.getType();
        
        if (type == null) {
            throw new EntityBuilderException(
                    "A title element type is required to build a title element.");            
        }
        
        if (! (type instanceof Ld4lTitleElementType) ) {
            throw new EntityBuilderException(
                    "Cannot build title element: invalid title element type.");            
        }
        
        Entity titleElement;
        
        if (type.equals(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)) {
            titleElement = buildMainTitleElement(params);
        } else if (type.equals(Ld4lTitleElementType.SUBTITLE_ELEMENT)) {
            titleElement = buildSubtitleElement(params);
        } else {
            // Unimplemented types
            throw new EntityBuilderException(
                    "Don't know how to build title element of type " + type);
        }  

        title.addRelationship(Ld4lObjectProp.HAS_PART, titleElement);      
        return titleElement;       
    }
    
    private Entity buildMainTitleElement(BuildParams params) 
            throws EntityBuilderException {
        
        RecordField subfield = params.getSubfield();
        if (! (subfield instanceof MarcxmlSubfield) ) {
            throw new EntityBuilderException(
                    "Specified subfield is not a MarcxmlSubfield");
        }
        
        MarcxmlSubfield marcxmlSubfield = (MarcxmlSubfield) subfield;
        if (! marcxmlSubfield.getCode().equals("a")) {
            throw new EntityBuilderException(
                    "Subfield $a required to build MainTitleElement");
        }
        
        Entity titleElement = new Entity(params.getType());
        
        String value = marcxmlSubfield.getTextValue();
        // TODO Add other final punct as needed
        value = StringUtils.removePattern(value, "\\s*:\\s*$");     
        value = value.trim();
        
        titleElement.addAttribute(Ld4lDatatypeProp.VALUE, value);
        
        return titleElement;
    }
    
    private Entity buildSubtitleElement(BuildParams params) 
            throws EntityBuilderException {
               
        String value = params.getValue();
        if (value == null || value.isEmpty()) {
            throw new EntityBuilderException(
                    "Non-empty value required to build subtitle.");        
        }
        
        Entity titleElement = new Entity(params.getType());
        titleElement.addAttribute(Ld4lDatatypeProp.VALUE, value.trim());
        
        return titleElement;               
    }
    
}

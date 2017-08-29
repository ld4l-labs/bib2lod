package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.util.Bib2LodStringUtils;

public class TitleElementBuilder extends BaseEntityBuilder {

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
 
        Entity title = params.getParent();
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
        
        String value = params.getValue();
        if (value == null || value.isEmpty()) {
            throw new EntityBuilderException(
                    "Non-empty string value required to build title element.");        
        }
        
        Entity titleElement = new Entity(type);
        
        /*
         * Remove final punctuation. Colon is used at the end of the main title 
         * when a subtitle follows, and should be removed. Final periods also
         * occur.
         */
        value = Bib2LodStringUtils.removeFinalPunctAndWhitespace(value);    
        
        /*
         * NB Final space must be retained in non-sort elements, in order to
         * correctly reconstruct the title: E.g., French "L'" vs. "Le ".
         */
        if (! type.equals(Ld4lTitleElementType.NON_SORT_ELEMENT)) {
            value = value.trim();
        } 

        titleElement.addAttribute(Ld4lDatatypeProp.VALUE, value);
        title.addRelationship(Ld4lObjectProp.HAS_PART, titleElement);      
        return titleElement;       
    }
    
}

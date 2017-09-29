package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAnnotationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class AnnotationBuilder extends MarcxmlEntityBuilder {
    
    protected Entity annotation;
    protected MarcxmlDataField field;
    protected Entity parent;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
               
        reset();      
        parseBuildParams(params);
        
        build();
        
        parent.addRelationship(Ld4lObjectProp.HAS_ANNOTATION, annotation);
        
        return annotation;
    }
    
    private void reset() {
        this.annotation = null;
        this.field = null;
        this.parent = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException("A related instance is " +
                    "required to build an Annotation.");
        }
        
        RecordField field = params.getField();
        if (field == null) {
            throw new EntityBuilderException("A field is required to " +
                    "build an Annotation.");
        }
        if (! (field instanceof MarcxmlDataField)) {
            throw new EntityBuilderException("A data field is required " +
                    "to build an Annotation.");
        }
        this.field = (MarcxmlDataField) field;

    }
    
    private void build() {
        
        this.annotation = new Entity(Ld4lAnnotationType.defaultType());
        
        switch (field.getTag()) {
        case "500":
            buildGenericAnnotation();
            break;
        default:
            break;
        }
    }
    
    private void buildGenericAnnotation() {
 
        MarcxmlSubfield subfield = field.getSubfield('a');
        if (subfield == null) {
            return;
        }
        
        annotation.addExternalRelationship(Ld4lObjectProp.HAS_MOTIVATION, 
                Ld4lNamedIndividual.DESCRIBING);
        // For now, using datatype prop rather than building a body resource.
        annotation.addLegacySourceDataAttribute(Ld4lDatatypeProp.BODY_VALUE, 
                subfield.getTextValue());        
    }
    
}

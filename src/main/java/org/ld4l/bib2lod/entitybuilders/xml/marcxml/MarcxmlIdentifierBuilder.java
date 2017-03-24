package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Identifier;
import org.ld4l.bib2lod.entities.Instance;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;

public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 


    /**
     * Construct a new identifier from a control field.
     * @param field - the control field 
     * @param instance - the related Instance
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(MarcxmlField field, Entity instance) 
            throws EntityBuilderException {
        super(null, field, instance);
    }
     
    public Identifier build() {
        
        // Allows access to Instance-specific methods.
        Instance instance = (Instance) relatedEntity;
        
        Identifier identifier = new Identifier();
        if (field instanceof MarcxmlControlField) {
            MarcxmlControlField controlField = (MarcxmlControlField) field;
            if (controlField.getControlNumber().equals("001")) {
                identifier.addType(Identifier.Type.LOCAL);
                identifier.setValue(controlField.getTextValue());
            }
            
        }
        instance.addIdentifier(identifier);
        
        return identifier;           
    }

}

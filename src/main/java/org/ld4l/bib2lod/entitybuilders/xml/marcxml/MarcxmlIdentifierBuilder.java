package org.ld4l.bib2lod.entitybuilders.xml.marcxml;

import org.ld4l.bib2lod.entities.BibEntity;
import org.ld4l.bib2lod.entities.Identifier;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlField;

public class MarcxmlIdentifierBuilder extends MarcxmlEntityBuilder { 
    
    private BibEntity bibEntity;

    /**
     * Construct a new identifier from a control field.
     * @param field
     * @throws EntityBuilderException 
     */
    public MarcxmlIdentifierBuilder(MarcxmlField field) 
            throws EntityBuilderException {
        super(null, field);
    }
    
    public MarcxmlIdentifierBuilder(MarcxmlField field, BibEntity bibEntity) 
            throws EntityBuilderException {
        super(null, field);
        this.bibEntity = bibEntity;
    }
    
    public Identifier build() {
        
        Identifier identifier = new Identifier();
        if (field instanceof MarcxmlControlField) {
            MarcxmlControlField controlField = (MarcxmlControlField) field;
            if (controlField.getControlNumber().equals("001")) {
                identifier.addType(Identifier.Type.LOCAL);
                identifier.setValue(controlField.getTextValue());
            }
            
        }
        bibEntity.addIdentifier(identifier);
        return identifier;           
    }

}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class MarcxmlToLd4lAdminMetadataBuilder extends BaseEntityBuilder {
    
    private MarcxmlRecord record;
    private Entity relatedEntity;
    private Entity adminMetadata;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        this.relatedEntity = params.getRelatedEntity();
        if (relatedEntity == null) {
            throw new EntityBuilderException(
                    "Cannot build admin metadata without a related entity.");
        }
        
        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "Cannot build admin metadata without an input record.");
        }
        
        this.adminMetadata = new Entity(Ld4lAdminMetadataType.superClass());
        
        convert040();
        
        if (adminMetadata.isEmpty()) {
            return null;
        }
      
        relatedEntity.addRelationship(
                Ld4lObjectProp.HAS_ADMIN_METADATA, adminMetadata);
        
        return adminMetadata;
    }
    
    private void convert040() {
        
        MarcxmlDataField field040 = record.getDataField(40);
        
        if (field040 == null) {
            return;
        }
        addSource(field040);
        addDescriptionModifier(field040);        
    }
    
    private void addSource(MarcxmlDataField field) {
      
        MarcxmlSubfield subfield = field.getSubfield('c');
        if (subfield != null) {
            Entity agent = new Entity(Ld4lAgentType.superClass());
            agent.addAttribute(Ld4lDatatypeProp.NAME, subfield.getTextValue());
            adminMetadata.addRelationship(Ld4lObjectProp.HAS_SOURCE, agent);                  
        }        
    }
    
    private void addDescriptionModifier(MarcxmlDataField field) {

        MarcxmlSubfield subfield = field.getSubfield('d');
        if (subfield != null) {
            Entity agent = new Entity(Ld4lAgentType.superClass());
            agent.addAttribute(Ld4lDatatypeProp.NAME, subfield.getTextValue());
            adminMetadata.addRelationship(
                    Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER, agent);
        }       
    }

}

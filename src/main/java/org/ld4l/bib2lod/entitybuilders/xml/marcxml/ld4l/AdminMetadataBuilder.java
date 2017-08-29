package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.regex.Pattern;

import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDescriptionConventionsType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

public class AdminMetadataBuilder extends BaseEntityBuilder {
 
    private static final Pattern PATTERN_005 = 
            Pattern.compile("^[\\d]{14}\\.\\d$");
    
    private Entity adminMetadata;
    private MarcxmlRecord record;
    private Entity parent;
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        reset();
        processBuildParams(params);

        this.adminMetadata = new Entity(Ld4lAdminMetadataType.superClass());
        
        convert001();
        convert040();
        convert005();
        
        if (adminMetadata.isEmpty()) {
            return null;
        }
      
        parent.addRelationship(
                Ld4lObjectProp.HAS_ADMIN_METADATA, adminMetadata);
        
        return adminMetadata;
    }
    
    private void reset() {
        this.adminMetadata = null;
        this.record = null;
        this.parent = null;
    }
    
    private void processBuildParams(BuildParams params) 
            throws EntityBuilderException {

        this.parent = params.getParent();
        if (parent == null) {
            throw new EntityBuilderException(
                    "Cannot build admin metadata without a related entity.");
        }
        
        this.record = (MarcxmlRecord) params.getRecord();
        if (record == null) {
            throw new EntityBuilderException(
                    "Cannot build admin metadata without an input record.");
        }
    }
    
    private void convert001() throws EntityBuilderException {
        
        MarcxmlControlField controlField_001 = record.getControlField(1);

        if (controlField_001 == null) {
            return;
        }
        
        EntityBuilder builder = getBuilder(Ld4lIdentifierType.superClass());
                
        BuildParams params = new BuildParams()
                .setParent(adminMetadata)
                .setField(controlField_001);
        builder.build(params);
    }     
    
    private void convert040() {
        
        MarcxmlDataField field_040 = record.getDataField(40);
        
        if (field_040 == null) {
            return;
        }
        addDescriptionLanguage(field_040);
        addSource(field_040);
        addDescriptionModifier(field_040); 
        addDescriptionConventions(field_040);  
    }
    
    private void addDescriptionLanguage(MarcxmlDataField field) {

        MarcxmlSubfield subfield = field.getSubfield('b');
        if (subfield != null) {
            String language = subfield.getTextValue();
            adminMetadata.addExternalRelationship( 
                    Ld4lObjectProp.HAS_LANGUAGE, 
                        Ld4lNamespace.LC_LANGUAGES.uri() + language); 
        }
    }
    
    private void addSource(MarcxmlDataField field) {
      
        MarcxmlSubfield subfield = field.getSubfield('c');
        if (subfield != null) {
            Entity agent = new Entity(Ld4lAgentType.superClass());
            agent.addAttribute(Ld4lDatatypeProp.NAME, 
                    subfield.getTextValue());
            adminMetadata.addRelationship(Ld4lObjectProp.HAS_SOURCE, agent);                  
        }        
    }
    
    private void addDescriptionModifier(MarcxmlDataField field) {

        for (MarcxmlSubfield subfield : field.getSubfields('d')) {
            Entity agent = new Entity(Ld4lAgentType.superClass());
            agent.addAttribute(Ld4lDatatypeProp.NAME, 
                    subfield.getTextValue());
            adminMetadata.addRelationship(
                    Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER, agent);
        }       
    }
    
    private void addDescriptionConventions(MarcxmlDataField field) {

        for (MarcxmlSubfield subfield : field.getSubfields('e')) {
            Entity conventions = 
                    new Entity(Ld4lDescriptionConventionsType.superClass());
            conventions.addAttribute(Ld4lDatatypeProp.LABEL, 
                    subfield.getTextValue());
            adminMetadata.addRelationship(
                    Ld4lObjectProp.HAS_DESCRIPTION_CONVENTIONS, conventions);
        }       
    }
    
    private void convert005() throws EntityBuilderException {
        
        MarcxmlControlField field_005 = record.getControlField(5);
        
        if (field_005 == null) {
            return;
        }    
        
        String value = field_005.getTextValue();
        // Convert format 20130330145647.0 to 2013-03-30T14:56:47
        if (! PATTERN_005.matcher(value).matches()) {
            throw new EntityBuilderException(
                    "Invalid value for control field 005.");
        }

        String datetime = value.substring(0, 4) + "-" + 
                value.substring(4, 6) + "-" + value.substring(6, 8) + "T" + 
                value.substring(8, 10) + ":" + value.substring(10,12) + 
                ":" + value.substring(12, 14);
        adminMetadata.addAttribute(Ld4lDatatypeProp.CHANGE_DATE, 
                datetime, XsdDatatype.DATETIME);       
    }

}

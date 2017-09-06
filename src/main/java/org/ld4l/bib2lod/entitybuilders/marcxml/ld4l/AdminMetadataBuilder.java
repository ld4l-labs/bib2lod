package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.List;
import java.util.regex.Pattern;

import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
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

public class AdminMetadataBuilder extends MarcxmlEntityBuilder {
 
    private static final Pattern PATTERN_005 = 
            Pattern.compile("^[\\d]{14}\\.\\d$");
    
    private Entity adminMetadata;
    private MarcxmlRecord record;
    private Entity parent;
    
    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {
        
        reset();
        parseBuildParams(params);

        this.adminMetadata = new Entity(Ld4lAdminMetadataType.superclass());
     
        // Control field 001: local identifier 
        buildChildFromControlField(
                Ld4lIdentifierType.superclass(), adminMetadata, record, "001");
        
        convert_040();      
        
        convert_005();

        
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
    
    private void parseBuildParams(BuildParams params) 
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
    
    private void convert_040() throws EntityBuilderException {
        
        MarcxmlDataField field = record.getDataField("040");
        
        if (field == null) {
            return;
        }
        addSources(field);
        addDescriptionLanguage(field);
        addDescriptionModifiers(field); 
        addDescriptionConventions(field);  
    }

    private void addSources(MarcxmlDataField field) 
            throws EntityBuilderException {
        
        // 040 $a non-repeating, $c non-repeating
        // $a and $c use the same predicate though they are distinct in
        // MARC.
        List<MarcxmlSubfield> subfields =  field.getSubfields('a', 'c');        
        
        if (subfields.isEmpty()) {
            return;
        }

        EntityBuilder builder = getBuilder(Ld4lAgentType.superclass());
        BuildParams params = new BuildParams()
                .setParent(adminMetadata)
                .setRelationship(Ld4lObjectProp.HAS_SOURCE);
        
        for (MarcxmlSubfield subfield : subfields) {
            if (subfield != null) {
                params.setSubfield(subfield);
                builder.build(params); 
            }
        }                       
    }
    
    private void addDescriptionLanguage(MarcxmlDataField field) {

        // 040 $b non-repeating
        MarcxmlSubfield subfield = field.getSubfield('b');
        if (subfield == null) {
            return;
        }
        
        String language = subfield.getTextValue();
        adminMetadata.addExternalRelationship( 
                Ld4lObjectProp.HAS_LANGUAGE, 
                    Ld4lNamespace.LC_LANGUAGES.uri() + language); 
    }

    
    private void addDescriptionModifiers(MarcxmlDataField field) 
            throws EntityBuilderException {

        // 040 $d repeating
        List<MarcxmlSubfield> subfields = field.getSubfields('d');
        
        if (subfields.isEmpty()) {
            return;
        }
        
        EntityBuilder builder = getBuilder(Ld4lAgentType.superclass());
        BuildParams params = new BuildParams()
                .setParent(adminMetadata)
                .setRelationship(Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER);
        
        for (MarcxmlSubfield subfield : subfields) {
            if (subfield != null) {
                params.setSubfield(subfield);
                builder.build(params); 
            }
        }     
    }
    
    private void addDescriptionConventions(MarcxmlDataField field) 
            throws EntityBuilderException {

        for (MarcxmlSubfield subfield : field.getSubfields('e')) {

            /* Add a generic method to MarcxmlEntityBuilder - pass in
            type, subfield, property to add subfield text value to, and
            relationship.*/
            
            BuildParams params = new BuildParams()
                    .setType(Ld4lDescriptionConventionsType.superclass())
                    .setProperty(Ld4lDatatypeProp.LABEL)
                    .setSubfield(subfield)
                    .setParent(adminMetadata)
                    .setRelationship(
                            Ld4lObjectProp.HAS_DESCRIPTION_CONVENTIONS);
            super.build(params);
        }       
    }
    
    private void convert_005() throws EntityBuilderException {
        
        MarcxmlControlField field_005 = record.getControlField("005");
        
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

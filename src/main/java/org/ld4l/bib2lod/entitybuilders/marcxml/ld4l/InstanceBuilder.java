/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.MarcxmlEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities.ProviderActivityBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAdminMetadataType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lExtentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lIdentifierType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;

/**
 * Builds an Instance from a Record.
 */
public class InstanceBuilder extends MarcxmlEntityBuilder {
    
    @SuppressWarnings("unused")
    private static final Logger LOGGER = LogManager.getLogger();
    
    public static List<Character> _260_PUBLISHER_CODES = 
            Arrays.asList('a', 'b', 'c');
    public static List<Character> _260_MANUFACTURER_CODES = 
            Arrays.asList('e', 'f', 'g');

    private InstanceEntity instance;
    private MarcxmlRecord record;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        parseBuildParams(params);
        
        this.instance = new InstanceEntity();
 
        // Admin metadata is built from multiple fields
        buildChildFromRecord(
                Ld4lAdminMetadataType.defaultType(), instance, record);  
 
        buildWorks();
        buildItem();
        buildIdentifiers();       
        buildTitles();
        buildActivities();
        buildProvisionActivityStatements();
        buildResponsiblityStatement();
        buildPhysicalDescriptions();

        return instance;
    }
    
    private void reset() {
        this.instance = null;
        this.record = null;
    }
    
    private void parseBuildParams(BuildParams params) 
            throws EntityBuilderException {
        
        // Use this if it generates better error messages 
        // this.record = (MarcxmlRecord.class.cast(params.getRecord()));
        this.record = (MarcxmlRecord) params.getRecord(); 
        
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build an instance.");
        }        
    }
    
    private void buildIdentifiers() throws EntityBuilderException {
        convert035();
    }

    private void convert035() throws EntityBuilderException {

        // 035 is a repeating field
        List<MarcxmlDataField> fields = record.getDataFields("035");
        if (fields.isEmpty()) {
            return;
        }

        EntityBuilder builder = getBuilder(Ld4lIdentifierType.defaultType());
        BuildParams params = new BuildParams()
                .setParent(instance);
        for (MarcxmlDataField field : fields) {
            params.setField(field);
            List<MarcxmlSubfield> subfields = field.getSubfields();
            for (MarcxmlSubfield subfield : subfields) {
                params.setSubfield(subfield);
                builder.build(params);                
            }
        }   
    }
    
    private void buildTitles() throws EntityBuilderException { 
        
        // TODO Not yet handling titles from other fields
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.defaultType());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildPhysicalDescriptions() throws EntityBuilderException {
        
        buildExtent();
    
    }
    
    private void buildExtent() throws EntityBuilderException {
        
        // 300
        List<MarcxmlDataField> fields = record.getDataFields("300");
        
        if (fields.size() == 0) {
            return;
        }
        
        EntityBuilder builder = getBuilder(Ld4lExtentType.defaultType());
        BuildParams params = new BuildParams()
                .setParent(instance);     
        
        for (MarcxmlDataField field : fields) {
            params.setField(field);
            for (MarcxmlSubfield subfield : field.getSubfields('a')) {
                params.setSubfield(subfield);
                builder.build(params);
            }
        }  
    }
    
    private void buildWorks() throws EntityBuilderException {
        // NB There are special cases where one Instance has multiple Works.
        
        // For now, the work will take its title from the instance title
        // need to build a new title with all the same elements and attributes,
        // but new resources.
        // Need method of EntityBuilder.clone() or copy?
        
        EntityBuilder builder = getBuilder(Ld4lWorkType.defaultType());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.defaultType());

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance); 
        builder.build(params);
    }   
    
    private void buildActivities() throws EntityBuilderException  {
        buildPublisherActivities();
        buildManufacturerActivities();
    } 
 
    private void buildPublisherActivities() throws EntityBuilderException {
        
        EntityBuilder builder = 
                getBuilder(Ld4lActivityType.PUBLISHER_ACTIVITY);

        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);
        
        // First build current publisher activity from mandatory 008.        
        builder.build(params.setField(record.getControlField("008")));

        // 260 fields: build additional publisher activities and add data to 
        // current publisher activity from 008.
        for (MarcxmlDataField field : record.getDataFields("260")) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = ProviderActivityBuilder.
                    getActivitySubfields(field, _260_PUBLISHER_CODES);

            for (List<RecordField> subfields : subfieldLists) {
                params.setSubfields(subfields);                     
                builder.build(params); 
            }
        }  
    }
   
    private void buildProvisionActivityStatements() {

        // Each 260 and 264 yields one statement from all $a$b$c concatenated.
        buildProvisionActivityStatements(
                Arrays.asList("260", "264"), Arrays.asList('a', 'b', 'c'));
        
        // Each 260 yields one statement from all $e$f$g concatenated.
        buildProvisionActivityStatements(
                Arrays.asList("260"), Arrays.asList('e', 'f', 'g'));
    }
    
    private void buildProvisionActivityStatements(
            List<String> tags, List<Character> codes) {

        for (MarcxmlDataField field : record.getDataFields(tags)) {            
            String statement = field.concatenateSubfieldValues(codes);
            if (statement != null) {
                instance.addAttribute
                    (Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT, 
                      statement);               
            }      
        } 
    }

    private void buildManufacturerActivities() throws EntityBuilderException {

        EntityBuilder builder = 
                getBuilder(Ld4lActivityType.MANUFACTURER_ACTIVITY);
        
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);

        // Build manufacturer activities from 260$e$f$g
        for (MarcxmlDataField field : record.getDataFields("260")) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = 
                    ProviderActivityBuilder.getActivitySubfields(
                            field, _260_MANUFACTURER_CODES);

            for (List<RecordField> subfields : subfieldLists) {
                params.setField(field)
                      .setSubfields(subfields);
                builder.build(params); 
            }
        }  
    }
    
    /**
     * Adds responsibility statement 245$c.
     */
    private void buildResponsiblityStatement() {
        
        MarcxmlDataField field = record.getDataField("245");        
        if (field == null) {
            return;
        }
        
        MarcxmlSubfield subfield = field.getSubfield('c');
        if (subfield == null) {
            return;
        } 
        
        instance.addAttribute(Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT, 
                subfield.getTextValue());             
    }

}

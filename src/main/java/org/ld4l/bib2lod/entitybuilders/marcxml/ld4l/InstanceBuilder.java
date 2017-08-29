/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BaseEntityBuilder;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
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
public class InstanceBuilder extends BaseEntityBuilder {
    
    private static final Logger LOGGER = LogManager.getLogger();

    private InstanceEntity instance;
    private MarcxmlRecord record;

    @Override
    public Entity build(BuildParams params) throws EntityBuilderException {

        reset();
        
        // Use this if it generates better error messages 
        // this.record = (MarcxmlRecord.class.cast(params.getRecord()));
        this.record = (MarcxmlRecord) params.getRecord(); 
        
        if (record == null) {
            throw new EntityBuilderException(
                    "A record is required to build an instance.");
        }
        
        this.instance = new InstanceEntity();
 
        // Critical ordering: build admin metadata before identifiers. Remove
        // an identifier with value matching the admin metadata identifier.
        buildAdminMetadata();
        buildIdentifiers();       
        buildTitles();
        buildActivities();
        buildProvisionActivityStatements();
        buildResponsiblityStatement();
        buildPhysicalDescriptions();
        buildWorks();
        buildItem();

        return instance;
    }
    
    private void reset() {
        this.instance = null;
        this.record = null;
    }
    
    private void buildAdminMetadata() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lAdminMetadataType.superClass());
 
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);
        builder.build(params);          
    }
    
    private void buildIdentifiers() throws EntityBuilderException {
        convert_035();
    }

    private void convert_035() throws EntityBuilderException {

        // 035 is a repeating field
        List<MarcxmlDataField> fields = record.getDataFields(35);
        if (fields.isEmpty()) {
            return;
        }

        EntityBuilder builder = getBuilder(Ld4lIdentifierType.superClass());
        BuildParams params = new BuildParams()
                .setParent(instance);
        for (MarcxmlDataField field : fields) {
            List<MarcxmlSubfield> subfields = field.getSubfields();
            params.setField(field);
            for (MarcxmlSubfield subfield : subfields) {
                params.setSubfield(subfield);
                builder.build(params);                
            }
        }   
    }
    
    private void buildTitles() throws EntityBuilderException { 
        
        // NB There may be multiple, so this isn't sufficient.
        
        EntityBuilder builder = getBuilder(Ld4lTitleType.superClass());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildPhysicalDescriptions() throws EntityBuilderException {
        
        // TODO Not sure yet if there are others. 
        buildExtent();
    
    }
    
    private void buildExtent() throws EntityBuilderException {
        
        // 300
        List<MarcxmlDataField> fields = record.getDataFields(300);
        
        if (fields.size() == 0) {
            return;
        }
        
        EntityBuilder builder = getBuilder(Ld4lExtentType.superClass());
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
        
        EntityBuilder builder = getBuilder(Ld4lWorkType.superClass());
        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance);
        builder.build(params);
    }
    
    private void buildItem() throws EntityBuilderException {
        
        EntityBuilder builder = getBuilder(Ld4lItemType.superClass());

        BuildParams params = new BuildParams()
                .setRecord(record)
                .setParent(instance); 
        builder.build(params);
    }   
    
    private void buildActivities() throws EntityBuilderException  {
        buildPublisherActivities();
        buildManufacturerActivities();
        buildProviderActivities();
    } 
 
    private void buildPublisherActivities() throws EntityBuilderException {
        
        EntityBuilder builder = 
                getBuilder(Ld4lActivityType.PUBLISHER_ACTIVITY);

        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);
        
        // First build current publisher activity from mandatory 008.        
        builder.build(params.setField(record.getControlField(8)));

        // 260 fields: build additional publisher activities and add data to 
        // current publisher activity from 008.
        List<Character> publisherCodes = Arrays.asList('a', 'b', 'c');
        for (MarcxmlDataField field : record.getDataFields(260)) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = ProviderActivityBuilder.
                    getActivitySubfields(field, publisherCodes);

            for (List<RecordField> subfields : subfieldLists) {
                params.setSubfields(subfields);                     
                builder.build(params); 
            }
        }  
    }
   
    private void buildProvisionActivityStatements() {

        // Each 260 and 264 yields one statement from all $a$b$c concatenated.
        buildProvisionActivityStatements(
                Arrays.asList(260, 264), Arrays.asList('a', 'b', 'c'));
        
        // Each 260 yields one statement from all $e$f$g concatenated.
        buildProvisionActivityStatements(
                Arrays.asList(260), Arrays.asList('e', 'f', 'g'));
    }
    
    private void buildProvisionActivityStatements(
            List<Integer> tags, List<Character> codes) {

        for (MarcxmlDataField field : record.getDataFields(tags)) {
            
            List<String> textValues = new ArrayList<>();
            
            for (MarcxmlSubfield subfield : field.getSubfields(codes)) {
                textValues.add(subfield.getTextValue());
            }

            if (textValues.size() > 0) {
                String statement = StringUtils.join(textValues, " ");
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
        List<Character> manufacturerCodes = Arrays.asList('e', 'f', 'g');
        for (MarcxmlDataField field : record.getDataFields(260)) {
            params.setField(field);
            List<List<RecordField>> subfieldLists = 
                    ProviderActivityBuilder.getActivitySubfields(
                            field, manufacturerCodes);

            for (List<RecordField> subfields : subfieldLists) {
                params.setField(field)
                      .setSubfields(subfields);
                builder.build(params); 
            }
        }  
    }
    
    private void buildProviderActivities() throws EntityBuilderException {
        
    }
    
    /**
     * Add responsibility statement to instance from 245$c.
     */
    private void buildResponsiblityStatement() {
        
        MarcxmlDataField field = record.getDataField(245);        
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

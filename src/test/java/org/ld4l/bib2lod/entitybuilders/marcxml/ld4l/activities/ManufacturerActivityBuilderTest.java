package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

public class ManufacturerActivityBuilderTest extends AbstractTestClass {
    
    private static BaseMockBib2LodObjectFactory factory;
    private ManufacturerActivityBuilder builder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.builder = new ManufacturerActivityBuilder();              
    }
    
    public static final MockMarcxml _260_MANUFACTURER = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .addDatafield("260", " ", " ")
            .addSubfield("e", "Oak Ridge, Tenn. :")
            .addSubfield("f", "Oak Ridge National Laboratory ")
            .addSubfield("g", "1974")
            .lock();        
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testManufacturer_260() throws Exception {
       Entity activity = buildActivity(_260_MANUFACTURER, "260", 
               Arrays.asList('e', 'f', 'g'));     
       Assert.assertEquals(Ld4lActivityType.MANUFACTURER_ACTIVITY, activity.getType());
    }  
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildActivity(MockMarcxml input, String tag, 
            List<Character> codes) throws Exception {
        MarcxmlRecord record = input.toRecord();
        MarcxmlDataField field = record.getDataField(tag);  
        List<RecordField> subfields = new ArrayList<>();
        for (char code : codes) {
            subfields.add(field.getSubfield(code));
        }
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(record)
                .setField(record.getDataField(tag))
                .setSubfields(subfields);
        return builder.build(params);
    }

}

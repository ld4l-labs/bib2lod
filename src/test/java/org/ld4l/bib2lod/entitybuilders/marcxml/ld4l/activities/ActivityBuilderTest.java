package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;


public class ActivityBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml _260_PUBLISHER = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("260", " ", " ").addSubfield("b", "Grune & Stratton,")
            .lock();

    private static BaseMockBib2LodObjectFactory factory;
    private PublisherActivityBuilder activityBuilder;  
    private InstanceBuilder instanceBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.activityBuilder = new PublisherActivityBuilder();  
        this.instanceBuilder = new InstanceBuilder();
    }   
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void nullParent() throws Exception {
        expectException(EntityBuilderException.class, 
                "A parent entity is required");
        BuildParams params = new BuildParams()
                .setParent(null);        
        activityBuilder.build(params);        
    }
    
    @Test
    public void nullField_ThrowsException() throws Exception {     
        expectException(EntityBuilderException.class, 
                "A field is required");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(null)
                .setField(null);
        activityBuilder.build(params);        
    }

    @Test
    public void invalidFieldType_ThrowsException() throws Exception {     
        expectException(EntityBuilderException.class, 
                "A data field or control field is required");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(null)
                .setField(new MarcxmlSubfield(
                        XmlTestUtils.buildElementFromString(
                                "<subfield code='a'>test</subfield>")));
        activityBuilder.build(params);        
    }
    
    @Test 
    public void testAddLabel() throws Exception {
        Entity activity = buildActivity(_260_PUBLISHER, "260");
        Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY.label(), 
                activity.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    @Test
    public void testRelationshipToBibResource() throws Exception {
        Entity instance = buildInstance(MINIMAL_RECORD);
        Assert.assertNotNull(instance.getChild(Ld4lObjectProp.HAS_ACTIVITY));
    }

    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
         
    private Entity buildActivity(MockMarcxml input, String tag) 
            throws Exception {
        MarcxmlRecord record = input.toRecord(); 
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(record)
                .setField(record.getDataField(tag));
        return activityBuilder.build(params);
    }
    
    private Entity buildInstance(MockMarcxml input) throws Exception {
        return instanceBuilder.build(
                new BuildParams().setRecord(input.toRecord()));
    }
}

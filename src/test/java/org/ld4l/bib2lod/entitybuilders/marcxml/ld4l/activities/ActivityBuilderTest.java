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
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;
public class ActivityBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml _260_PUBLISHER =  MINIMAL_RECORD.openCopy()
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
                .setField(MarcxmlTestUtils.buildSubfieldFromString(
                        "<subfield code='a'>test</subfield>"));
        activityBuilder.build(params);        
    }
    
    @Test 
    public void testAddLabel() throws Exception {
        Entity activity = buildActivity(_260_PUBLISHER);
        Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY.label(), 
                activity.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
         
    private Entity buildActivity(MockMarcxml input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(input.toRecord());
        return buildActivity(params);         
    }   
    
    private Entity buildActivity(BuildParams params) 
            throws Exception { 
        Entity instance = instanceBuilder.build(params);
        return instance.getChild(Ld4lObjectProp.HAS_ACTIVITY); 
    }
}

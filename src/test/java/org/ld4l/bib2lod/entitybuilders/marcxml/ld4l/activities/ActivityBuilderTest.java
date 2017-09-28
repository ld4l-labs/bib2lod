package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.Type;
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
            .addDatafield("260", " ", " ").addSubfield("b", "Grune & Stratton,")
            .lock();

    public static final MockMarcxml _100_AUTHOR = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "0", " ").addSubfield("a", "Manya K'Omalowete a Djonga,")
            .lock();

    private static BaseMockBib2LodObjectFactory factory;
    private PublisherActivityBuilder publisherActivityBuilder;  
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.publisherActivityBuilder = new PublisherActivityBuilder();  
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
        publisherActivityBuilder.build(params);        
    }
    
    @Test
    public void nullFieldAndProperty_ThrowsException() throws Exception {     
        expectException(EntityBuilderException.class, 
                "A field or property and value");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(null)
                .setProperty(Ld4lDatatypeProp.NAME);
        publisherActivityBuilder.build(params);        
    }
    
    @Test
    public void nullFieldAndValue_ThrowsException() throws Exception {     
        expectException(EntityBuilderException.class, 
                "A field or property and value");
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(null)
                .setValue("value");
        publisherActivityBuilder.build(params);        
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
        publisherActivityBuilder.build(params);        
    }
    
    @Test 
    public void testAddLabel() throws Exception {
        Entity activity = buildActivity(_260_PUBLISHER, "260");
        Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY.label(), 
                activity.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    @Test
    public void testRelationshipToResource() throws Exception {
        Entity instance = new Entity();
        Entity activity = buildActivity(instance, _260_PUBLISHER, "260");
        Assert.assertTrue(instance.hasChild(Ld4lObjectProp.HAS_ACTIVITY, activity));
    }
    
    @Test
    public void testCopyrightHolderActivity() throws Exception {
        Entity activity = buildActivity(Ld4lActivityType.COPYRIGHT_HOLDER_ACTIVITY,
                Ld4lDatatypeProp.DATE, "1957");
        Assert.assertEquals("1957", activity.getValue(Ld4lDatatypeProp.DATE));
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
         
    private Entity buildActivity(MockMarcxml input, String tag) 
            throws Exception {
        return buildActivity(new Entity(), input, tag);
    }
    
    private Entity buildActivity(Entity parent, MockMarcxml input, String tag) 
            throws Exception {
        MarcxmlRecord record = input.toRecord(); 
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setRecord(record)
                .setField(record.getDataField(tag));
        return publisherActivityBuilder.build(params);
    }
    
    private Entity buildActivity(Type type, DatatypeProp property, String value) 
            throws Exception {
        EntityBuilder builder = new ActivityBuilder();
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setProperty(property)
                .setType(type)
                .setValue(value);
        return builder.build(params);
    }
        
}

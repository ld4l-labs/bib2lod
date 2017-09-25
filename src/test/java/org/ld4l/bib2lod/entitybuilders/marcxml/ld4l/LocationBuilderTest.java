package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class LocationBuilder.
 */
public class LocationBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml DUPLICATE_LOCATIONS = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .addDatafield("260", "3", " ")
            .addSubfield("a", "Leiden")
            .addSubfield("b", "E.J. Brill")
            .addDatafield("260", " ", " ")
            .addSubfield("a", "Leiden :")
            .addSubfield("b", "E.J. Brill")
            .lock(); 
    
    public static final MockMarcxml DIFFERENT_LOCATIONS = DUPLICATE_LOCATIONS.openCopy()
            .findDatafield("260", 0).replaceSubfield("a", "Amsterdam :")
            .findDatafield("260", 1).replaceSubfield("b", "Random House")
            .lock();
    
    private static final String NAME_SUBFIELD = 
            "<subfield code='b'>Leiden :</subfield>";
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    private LocationBuilder locationBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new InstanceBuilder();   
        this.locationBuilder = new LocationBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void nullParent_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A parent entity is required"); 
        locationBuilder.build(new BuildParams());
    }
    
    @Test
    public void noNameOrSubfield_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A subfield or name value is required");  
        BuildParams params = new BuildParams()
                .setParent(new Entity());
        locationBuilder.build(params);
    }
    
    @Test
    public void invalidType_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "Invalid location type");  
        BuildParams params = new BuildParams()
                .setType(Ld4lInstanceType.INSTANCE)
                .addSubfield(buildSubfieldFromString(NAME_SUBFIELD))
                        
                .setParent(new Entity());
        locationBuilder.build(params);
    }
    
    @Test
    public void testTypeFromBuildParam() throws Exception {
        Type type = Ld4lLocationType.LOCATION;
        BuildParams params = new BuildParams()
                .setType(type)
                .addSubfield(buildSubfieldFromString(NAME_SUBFIELD))
                        
                .setParent(new Entity());
        Entity location = locationBuilder.build(params);
        Assert.assertTrue(location.hasType(type));
    }
    
    @Test
    public void testNameFromBuildParam() throws Exception {
        String name = "Leiden";
        BuildParams params = new BuildParams()
                .setValue(name)
                .setParent(new Entity());
        Entity location = locationBuilder.build(params);
        Assert.assertEquals(name, location.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testNameFromSubfield() throws Exception {
        BuildParams params = new BuildParams()
                .addSubfield(buildSubfieldFromString(NAME_SUBFIELD))
                .setParent(new Entity());
        Entity location = locationBuilder.build(params);
        Assert.assertEquals("Leiden", 
                location.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testReuseExistingLocation() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(DUPLICATE_LOCATIONS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertEquals(activity1.getChild(Ld4lObjectProp.HAS_LOCATION), 
                activity2.getChild(Ld4lObjectProp.HAS_LOCATION));
    }
    
    @Test
    public void testBuildNewLocation() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(DIFFERENT_LOCATIONS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertNotEquals(activity1.getChild(
                Ld4lObjectProp.HAS_LOCATION), 
                    activity2.getChild(Ld4lObjectProp.HAS_LOCATION));
    }
    
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

  
    private MarcxmlSubfield buildSubfieldFromString(
            String element) throws RecordFieldException {                   
        return new MarcxmlSubfield(
                XmlTestUtils.buildElementFromString(element));
    } 
}

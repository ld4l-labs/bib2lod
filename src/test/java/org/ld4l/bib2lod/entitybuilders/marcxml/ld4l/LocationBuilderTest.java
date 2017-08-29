package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.LocationBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lLocationType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class LocationBuilder.
 */
public class LocationBuilderTest extends AbstractTestClass {

    public static final String DUPLICATE_LOCATIONS = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='a'>Leiden</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +               
                "</datafield>" +
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                "<subfield code='a'>Leiden :</subfield>" +
                "<subfield code='b'>E.J. Brill</subfield>" +                 
            "</datafield>" +
            "</record>";
    
    public static final String DIFFERENT_LOCATIONS = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='a'>Amsterdam :</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +               
                "</datafield>" +
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                "<subfield code='a'>Leiden :</subfield>" +
                "<subfield code='b'>Random House</subfield>" +                 
            "</datafield>" +
            "</record>";

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
    public void setUp() throws RecordFieldException {       
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
                .addSubfield(MarcxmlTestUtils.buildSubfieldFromString(
                        NAME_SUBFIELD))
                .setParent(new Entity());
        locationBuilder.build(params);
    }
    
    @Test
    public void testTypeFromBuildParam() throws Exception {
        Type type = Ld4lLocationType.LOCATION;
        BuildParams params = new BuildParams()
                .setType(type)
                .addSubfield(MarcxmlTestUtils.buildSubfieldFromString(
                        NAME_SUBFIELD))
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
                .addSubfield(MarcxmlTestUtils.buildSubfieldFromString(
                        NAME_SUBFIELD))
                .setParent(new Entity());
        Entity location = locationBuilder.build(params);
        Assert.assertEquals("Leiden", 
                location.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testReuseExistingLocation() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(MarcxmlTestUtils.buildRecordFromString(
                        DUPLICATE_LOCATIONS));
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
                .setRecord(MarcxmlTestUtils.buildRecordFromString(
                        DIFFERENT_LOCATIONS));
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
    
}

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
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;


/**
 * Tests class InstanceBuilder
 */
public class InstanceBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml RESPONSIBILITY_STATEMENT = MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addSubfield("c", "responsibility statement")
            .lock();
    
    public static final MockMarcxml _260_PUBLISHER = MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("260", "3", " ")
            .addSubfield("a", "New York,")
            .addSubfield("b", "Grune & Stratton,")
            .addSubfield("c", "1957.")
            .lock();
    
    public static final MockMarcxml _260_ONE_FIELD_TWO_PUBLISHERS = _260_PUBLISHER.openCopy()
            .replaceDatafield("260", "2", " ")
            .addSubfield("a", "place1")
            .addSubfield("c", "date1")
            .addSubfield("b", "name2")
            .addSubfield("c", "date2")
            .lock();
    
    public static final MockMarcxml _260_TWO_FIELDS_TWO_PUBLISHERS = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .addDatafield("260", "2", " ")
            .addSubfield("a", "place1")
            .addSubfield("c", "date1")
            .addDatafield("260", "2", " ")
            .addSubfield("a", "place1")
            .addSubfield("b", "name2")
            .addSubfield("c", "date2")
            .lock();       

    public static final MockMarcxml _260_PUBLISHER_AND_MANUFACTURER = MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("260", " ", " ")
            .addSubfield("a", "Springfield, Va. :")
            .addSubfield("b", "National Technical Information Service,")
            .addSubfield("c", "1974-")
            .addSubfield("e", "(Oak Ridge, Tenn. :")
            .addSubfield("f", "Oak Ridge National Laboratory ")
            .lock();

    public static final MockMarcxml _260_TWO_PUBLISHERS_AND_MANUFACTURER = 
            _260_TWO_FIELDS_TWO_PUBLISHERS.openCopy()
            .findDatafield("260", 0)
            .addSubfield("e", "place1")
            .addSubfield("f", "name1")
            .lock();
    
    public static final MockMarcxml _260_CURRENT_PUBLISHER = _260_PUBLISHER.openCopy()
            .findDatafield("260").setInd1("3").lock();
    
    public static final MockMarcxml MULTIPLE_035 = MINIMAL_RECORD.openCopy()
            .addDatafield("035", " ", " ").addSubfield("a", "(NIC)notisAAL3258")
            .addDatafield("035", " ", " ").addSubfield("a", "(OCoLC)1345399")
            .lock();
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder builder;   
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.builder = new InstanceBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams()
                .setRecord(null);                
        builder.build(params);
    } 
    
    @Test
    public void minimalRecord_Succeeds() throws Exception {
        buildInstance(MINIMAL_RECORD);
    }
    
    @Test
    public void testMultipleIdentifiers_035() throws Exception {
        Entity instance = 
                buildInstance(MULTIPLE_035);  
        Assert.assertEquals(2, 
                instance.getChildren(Ld4lObjectProp.IDENTIFIED_BY).size());
    }
    
    @Test 
    public void testResponsibilityStatement() throws Exception {
        Entity instance = 
                buildInstance(RESPONSIBILITY_STATEMENT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT).getValue();
        Assert.assertEquals("responsibility statement", statement);
    }
    
    @Test 
    public void testPasOnePublisher_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);             
        String statement = instance.getValue(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT);
        Assert.assertEquals("New York, Grune & Stratton, 1957.", statement);
    }
    
    @Test
    public void testPasTwoFieldsTwoPublishers_260() throws Exception {
        Entity instance = buildInstance(_260_TWO_FIELDS_TWO_PUBLISHERS); 
        Assert.assertEquals(2, instance.getAttributes(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).size());   
    }
    
    public void testTwoFieldsTwoPublishers_260() throws Exception {
        BuildParams params = new BuildParams().setRecord(
                _260_TWO_FIELDS_TWO_PUBLISHERS.toRecord());
        Entity instance = builder.build(params);  
        // Third publisher is from 008
        Assert.assertEquals(3, 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                        Ld4lActivityType.PUBLISHER_ACTIVITY).size());      
    }

    @Test
    public void testPasFirstPublisher_260() throws Exception {
        Entity instance = buildInstance(_260_TWO_FIELDS_TWO_PUBLISHERS); 
        String statement = (instance.getValues(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).get(0));
        Assert.assertEquals("place1 date1", statement);
    }  
    
    @Test
    public void testPasSecondPublisher_260() throws Exception {
        Entity instance = buildInstance(_260_TWO_FIELDS_TWO_PUBLISHERS); 
        String statement = (instance.getValues(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).get(1));
        Assert.assertEquals("place1 name2 date2", statement);
    }  
    
    @Test
    public void testPasOneFieldTwoPublishers_260() throws Exception {
        Entity instance = buildInstance(_260_ONE_FIELD_TWO_PUBLISHERS); 
        Assert.assertEquals(1, instance.getAttributes(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).size());   
    }
 
    @Test
    public void testOneFieldTwoPublishers_260() throws Exception {
        Entity instance = buildInstance(_260_ONE_FIELD_TWO_PUBLISHERS); 
        // Third publisher is from 008
        Assert.assertEquals(3, instance.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY).size());   
    }
    
    @Test
    public void testPasOneFieldTwoPublishersValue_260() throws Exception {
        Entity instance = buildInstance(_260_ONE_FIELD_TWO_PUBLISHERS); 
        String statement = instance.getValue(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT);
        Assert.assertEquals("place1 date1 name2 date2", statement);  
    }

    @Test 
    public void testPasPublisherAndManufacturer_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER);
        Assert.assertEquals(2, instance.getAttributes(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).size());
    }
    
    @Test
    public void testPublisherWithhManufacturer() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER); 
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY,
                activities.get(0).getType());     
    } 
    
    @Test
    public void testManufacturerWithPublisher() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER); 
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Assert.assertEquals(Ld4lActivityType.MANUFACTURER_ACTIVITY,
                activities.get(2).getType());     
    }    
    
    @Test 
    public void testPasTwoPublishersAndManufacturer_260() throws Exception {
        Entity instance = buildInstance(_260_TWO_PUBLISHERS_AND_MANUFACTURER);
        Assert.assertEquals(3, instance.getAttributes(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).size());
    }

    @Test 
    public void testTwoPublishersAndManufacturer_260() throws Exception {
        Entity instance = buildInstance(_260_TWO_PUBLISHERS_AND_MANUFACTURER);
        // Third publisher is from 008
        Assert.assertEquals(4, instance.getChildren(
                Ld4lObjectProp.HAS_ACTIVITY).size());   
    }

    @Test
    public void testCurrentPublishers_008_260() throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(_260_CURRENT_PUBLISHER.toRecord());
        Entity instance = builder.build(params);   
        Assert.assertEquals(2, 
              instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                      Ld4lActivityType.PUBLISHER_ACTIVITY).size());      
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance(MockMarcxml marcxml) 
            throws Exception {
        return builder.build(new BuildParams().setRecord(marcxml.toRecord()));
    }
}

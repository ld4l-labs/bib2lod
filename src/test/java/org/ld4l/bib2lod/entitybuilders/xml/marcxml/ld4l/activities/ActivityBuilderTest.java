package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

public class ActivityBuilderTest extends AbstractTestClass {
    
    public static final String _260_PUBLISHER = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='b'>Grune &amp; Stratton,</subfield>" +
                "</datafield>" +
            "</record>"; 

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
    public void setUp() throws RecordFieldException {       
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
         
    private Entity buildActivity(String input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input));
        return buildActivity(params);         
    }   
    
    private Entity buildActivity(BuildParams params) 
            throws Exception { 
        Entity instance = instanceBuilder.build(params);
        return instance.getChild(Ld4lObjectProp.HAS_ACTIVITY); 
    }
}

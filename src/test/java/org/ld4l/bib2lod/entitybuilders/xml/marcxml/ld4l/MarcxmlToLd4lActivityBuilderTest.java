package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;


/** 
 * Tests class MarcxmlToLd4lActivityBuilder.
 */
public class MarcxmlToLd4lActivityBuilderTest extends AbstractTestClass {
    
    public static final String _008 = 
            "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>";
    
    public static final String _008_NO_LOCATION = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957       a     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _008_NO_DATE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s        nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _001 = 
            "<controlfield tag='001'>102063</controlfield>";  
    
    public static final String _245 = 
            "<datafield tag='245'><subfield code='a'>text</subfield></datafield>";


    private static BaseMockBib2LodObjectFactory factory;
    private MarcxmlToLd4lActivityBuilder activityBuilder;   
    private MarcxmlToLd4lInstanceBuilder instanceBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.activityBuilder = new MarcxmlToLd4lActivityBuilder();
        this.instanceBuilder = new MarcxmlToLd4lInstanceBuilder();              
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void nullRelatedEntity_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A related entity is required");
        BuildParams params = new BuildParams()
                .setParentEntity(null);        
        activityBuilder.build(params);        
    }
    
    @Test
    public void nullRecordAndField_ThrowsException() throws Exception {     
        expectException(EntityBuilderException.class, 
                "A record or field is required");
        BuildParams params = new BuildParams()
                .setParentEntity(new Entity())
                .setRecord(null)
                .setField(null);
        activityBuilder.build(params);        
    }
    
    @Test
    public void typeParamIncluded_AddsType() throws Exception {   
        MarcxmlRecord record = MarcxmlTestUtils.getMinimalRecord();
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);
        Entity activity = instance.getChild(Ld4lObjectProp.HAS_ACTIVITY);
        Assert.assertTrue(activity.hasType(Ld4lActivityType.PUBLISHER_ACTIVITY));                    
    }
    
    @Test
    public void addLocation_Succeeds() throws Exception {       
        MarcxmlRecord record = MarcxmlTestUtils.getMinimalRecord();
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);
        Entity activity = instance.getChild(Ld4lObjectProp.HAS_ACTIVITY);
        String locationUri = 
                activity.getExternal(Ld4lObjectProp.IS_AT_LOCATION);
        Assert.assertEquals(
                Ld4lNamespace.LC_COUNTRIES.uri() + "nyu", locationUri);
     }
       
    @Test
    public void blankLocation_Succeeds() throws Exception {
        MarcxmlRecord record = 
                MarcxmlTestUtils.buildRecordFromString(_008_NO_LOCATION);
        BuildParams params = new BuildParams() 
                .setRecord(record);
        instanceBuilder.build(params);
    }
    
    @Test
    public void addDate_Succeeds() throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.getMinimalRecord();
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);
        Entity activity = instance.getChild(Ld4lObjectProp.HAS_ACTIVITY);
        String year = activity.getAttribute(Ld4lDatatypeProp.DATE).getValue();
        Assert.assertEquals("1957", year);
    }
    
    @Test
    public void blankDate_Succeeds() throws Exception {
        MarcxmlRecord record = 
                MarcxmlTestUtils.buildRecordFromString(_008_NO_DATE);
        BuildParams params = new BuildParams() 
                .setRecord(record);
        instanceBuilder.build(params);
    }

}

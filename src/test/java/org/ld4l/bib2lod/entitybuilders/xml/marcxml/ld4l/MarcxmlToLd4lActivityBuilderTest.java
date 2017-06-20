package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;


/** 
 * Tests class MarcxmlToLd4lActivityBuilder.
 */
public class MarcxmlToLd4lActivityBuilderTest extends AbstractTestClass {
    
    public static final String CONTROL_FIELD_008 = 
            "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>";
    
    public static final String INVALID_CONTROL_FIELD = 
            "<controlfield tag='008'>860506s</controlfield>";
    
    public static final String CONTROL_FIELD_WITH_BLANKS = 
            "<controlfield tag='008'>860506s                 b    000 0 eng  </controlfield>";
    
    public static final String CONTROL_FIELD_001 = 
            "<controlfield tag='001'>102063</controlfield>";  
    
    public static final String DATA_FIELD_245 = 
            "<datafield tag='245'><subfield code='a'>text</subfield></datafield>";

    
    private MarcxmlToLd4lActivityBuilder activityBuilder;   
    private InstanceEntity instance;
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.activityBuilder = new MarcxmlToLd4lActivityBuilder();
        this.instance = new InstanceEntity();                
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = EntityBuilderException.class)
    public void nullRelatedEntity_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(null);        
        activityBuilder.build(params);        
    }
    
    @Test (expected = EntityBuilderException.class)
    public void nullField_ThrowsException() throws Exception {        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(null);                
        activityBuilder.build(params);        
    }
    
    @Test
    public void nonNullInstanceAndField_Succeeds() throws Exception { 
        MarcxmlControlField field = new MarcxmlControlField(
                XmlTestUtils.buildElementFromString(CONTROL_FIELD_008));
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field);        
        activityBuilder.build(params);
    }
    
    @Test
    public void typeParamIncluded_AddsType() throws Exception {   
        MarcxmlControlField field = new MarcxmlControlField(
                XmlTestUtils.buildElementFromString(CONTROL_FIELD_008));
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)     
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
           
        Entity activity = activityBuilder.build(params);
        Assert.assertTrue(activity.hasType(Ld4lActivityType.PUBLISHER_ACTIVITY));        
    }
    
    @Test
    public void typeParamNotIncluded_AddsDefaultType() throws Exception {   
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_008);
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field);
        
        Entity activity = activityBuilder.build(params); 
        Assert.assertTrue(activity.hasType(Ld4lActivityType.ACTIVITY));
    }
    
    @Test
    public void addLocation_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_008);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        Entity activity = activityBuilder.build(params); 
        String locationUri = activity.getExternal(Ld4lObjectProp.IS_AT_LOCATION);
        Assert.assertEquals(Ld4lNamespace.LC_COUNTRIES.uri() + "nyu", locationUri);
    }
    
    @Test
    public void noLocationSubstring_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(INVALID_CONTROL_FIELD);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        activityBuilder.build(params); 
    }
    
    @Test
    public void blankLocation_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_WITH_BLANKS);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        activityBuilder.build(params);
    }
    
    @Test
    public void addDate_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_008);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        Entity activity = activityBuilder.build(params); 
        String year = activity.getAttribute(Ld4lDatatypeProp.DATE).getValue();
        Assert.assertEquals("1957", year);
    }
    
    @Test
    public void noDateSubstring_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(INVALID_CONTROL_FIELD);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        activityBuilder.build(params);
    }
    
    @Test
    public void blankDate_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_WITH_BLANKS);        
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field)
                .setType(Ld4lActivityType.PUBLISHER_ACTIVITY);
        
        activityBuilder.build(params);
    }
    
    @Test
    public void fieldNotControlField_Succeeds() throws Exception {
        MarcxmlControlField field = 
                MarcxmlTestUtils.buildControlFieldFromString(CONTROL_FIELD_008); 
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field);
        
        activityBuilder.build(params);       
    }
    
    @Test
    public void fieldNot008ControlField_Succeeds() throws Exception {
        MarcxmlDataField field = 
                MarcxmlTestUtils.buildDataFieldFromString(DATA_FIELD_245); 
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(field);
        
        activityBuilder.build(params);       
    }

}

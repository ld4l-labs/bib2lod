package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lWorkType;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;

/**
 * Tests class MarcxmlToLd4lTitleBuilder.
 */
public class MarcxmlToLd4lTitleBuilderTest extends AbstractTestClass {
    
    public static final String TWO_TITLE_FIELDS = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='240' ind1='0' ind2='0'>" +
                    "<subfield code='a'>another main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    private MarcxmlToLd4lTitleBuilder titleBuilder;   
    private InstanceEntity defaultInstance;
    private Entity defaultWork;
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.titleBuilder = new MarcxmlToLd4lTitleBuilder();
        this.defaultInstance = new InstanceEntity();  
        this.defaultWork = new Entity(Ld4lWorkType.superClass());
    }    
    
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test (expected = EntityBuilderException.class)
    public void nullRecord_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(defaultInstance)
                .setRecord(null);                
        titleBuilder.build(params); 
    }
    
    @Test (expected = EntityBuilderException.class)
    public void nullInstance_ThrowsException() throws Exception {
        buildTitle(MarcxmlTestUtils.MINIMAL_RECORD, (InstanceEntity) null);
    }
    
    @Test (expected = EntityBuilderException.class)
    public void nullWork_ThrowsException() throws Exception {
        buildTitle(MarcxmlTestUtils.MINIMAL_RECORD, (Entity) null);
    }
    
    @Test 
    public void buildInstanceTitleFromMinimalRecord_Succeeds() throws Exception {
        buildTitleFromDefaultInstance(MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    @Test 
    public void buildWorkTitleFromMinimalRecord_Succeeds() throws Exception {
        buildTitleFromDefaultWork(MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    @Test 
    public void buildInstanceTitleFromTwoTitleFields_Succeeds() throws Exception {
        buildTitleFromDefaultInstance(TWO_TITLE_FIELDS);
    }
    
    @Test 
    public void buildWorkTitleFromTwoTitleFields_Succeeds() throws Exception {
        buildTitleFromDefaultWork(TWO_TITLE_FIELDS);
    }
    

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private Entity buildTitle(String marcxml, InstanceEntity instance) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setRecord(record);        
        return titleBuilder.build(params);
    }
    
    private Entity buildTitleFromDefaultInstance(String marcxml) throws Exception { 
        return buildTitle(marcxml, defaultInstance);
    }
    
    private Entity buildTitle(String marcxml, Entity work) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        BuildParams params = new BuildParams()
                .setRelatedEntity(work)
                .setRecord(record);        
        return titleBuilder.build(params);
    }
    
    private Entity buildTitleFromDefaultWork(String marcxml) throws Exception { 
        return buildTitle(marcxml, defaultWork);
    }
}

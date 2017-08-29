package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.WorkBuilder;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;

/**
 * Tests class WorkBuilder.
 */
public class WorkBuilderTest extends AbstractTestClass {
    
    public static final String NO_TITLE_FIELD = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
            "</record>";
    
    public static final String NO_WORK_TYPE = 
            "<record>" +
                "<leader>01050</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String INVALID_WORK_TYPE = 
            "<record>" +
                "<leader>01050cxm a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
             "</record>";
    
    public static final String NO_LANGUAGE = 
            "<record>" +
                "<leader>01050cxm a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0      </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
             "</record>";   

    private WorkBuilder builder;   
    private InstanceEntity defaultInstance;
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.builder = new WorkBuilder();
        this.defaultInstance = new InstanceEntity();  
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullInstance_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "An instance is required");
        buildWork(MarcxmlTestUtils.MINIMAL_RECORD, null);
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams()
                .setParent(defaultInstance)
                .setRecord(null);                
        builder.build(params);
    } 
    
    @Test
    public void buildWorkFromMinimalRecord_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(MarcxmlTestUtils.MINIMAL_RECORD);        
    }

    @Test
    public void noWorkType_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(INVALID_WORK_TYPE);
    }
    
    @Test
    public void invalidWorkTypeChar_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(INVALID_WORK_TYPE);
    }
    
    @Test
    public void noLanguage_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(NO_LANGUAGE);
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildWork(String marcxml, InstanceEntity instance) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(record);        
        return builder.build(params);
    }
    
    private Entity buildWorkFromDefaultInstance(String marcxml) throws Exception { 
        return buildWork(marcxml, defaultInstance);
    }
    
}

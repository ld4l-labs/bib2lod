package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;


/**
 * Tests class MarcxmlToLd4lInstanceBuilder
 */
public class MarcxmlToLd4lInstanceBuilderTest extends AbstractTestClass {
    
    public static final String RECORD_WITH_RESPONSIBILITY_STATEMENT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                    "<subfield code='c'>responsibility statement</subfield>" +
                "</datafield>" + 
            "</record>";

    private static BaseMockBib2LodObjectFactory factory;
    private MarcxmlToLd4lInstanceBuilder instanceBuilder;   
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.instanceBuilder = new MarcxmlToLd4lInstanceBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = EntityBuilderException.class)
    public void nullRecord_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(null);                
        instanceBuilder.build(params);
    } 
    
    @Test
    public void noResponsibilityStatement_Succeeds() throws Exception {
        buildInstance(MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    @Test 
    public void testResponsibilityStatement() throws Exception {
        Entity instance = 
                buildInstance(RECORD_WITH_RESPONSIBILITY_STATEMENT);
        String statement = instance.getAttribute(Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT).getValue();
        Assert.assertEquals("responsibility statement", statement);
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private Entity buildInstance(String marcxml) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        BuildParams params = new BuildParams()
                .setRecord(record);        
        return instanceBuilder.build(params);
    }
}

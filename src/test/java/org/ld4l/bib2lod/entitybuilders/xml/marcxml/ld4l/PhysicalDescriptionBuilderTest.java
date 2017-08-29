package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class PhysicalDescriptionBuilder
 */
public class PhysicalDescriptionBuilderTest extends AbstractTestClass {
    
    private static final String _300_NO_$A = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='300' ind1='' ind2=''>" +
                    "<subfield code='c'>23 cm</subfield>" +          
                "</datafield>" + 
            "</record>";  
    
    private static final String _300_EXTENT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='300' ind1='' ind2=''>" +
                    "<subfield code='a'>123 p.</subfield>" +          
                "</datafield>" + 
            "</record>";   
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.instanceBuilder = new InstanceBuilder();              
    }
        
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
      
    @Test
    public void no300_Succeeds() throws Exception {
        buildInstance();
    }
    
    @Test
    public void no300$a_Succeeds() throws Exception {
        buildInstance(_300_NO_$A);
    }
    
    @Test
    public void testExtent() throws Exception {
        Entity instance = buildInstance(_300_EXTENT);
        Assert.assertNotNull(instance.getChild(Ld4lObjectProp.HAS_EXTENT));
    }
    
    @Test
    public void testExtentLabel() throws Exception {
        Entity extent = buildInstance(_300_EXTENT).getChild(
                Ld4lObjectProp.HAS_EXTENT);
        Assert.assertEquals("123 p.", 
                extent.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance() throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.getMinimalRecord());  
        return instanceBuilder.build(params);
    }
    
    private Entity buildInstance(String input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input));  
        return instanceBuilder.build(params);
    }
}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;


/**
 * Tests class AdminMetadataBuilder
 */
public class AdminMetadataBuilderTest extends AbstractTestClass {
    
    public static final String TEST_RECORD = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='005'>20130330145647.0</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='040' ind1=' ' ind2=' '>" +
                    "<subfield code='c'>NIC</subfield>" +
                    "<subfield code='d'>NIC</subfield>" +
                 "</datafield>" +
            "</record>";
    
    public static final String INVALID_005_VALUE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='005'>20130330</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    private AdminMetadataBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new AdminMetadataBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    
    @Test
    public void nullRelatedInstance_ThrowsException() throws Exception {      
        buildAndExpectException(null, MarcxmlTestUtils.MINIMAL_RECORD, 
                "without a related entity");
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(
                EntityBuilderException.class, "without an input record");        
        BuildParams params = new BuildParams()
                .setParentEntity(new Entity());              
        builder.build(params);        
    }
    
    @Test
    public void noAdminMetadataReturnsNull() throws Exception {
        Assert.assertNull(buildAdminMetadata(MarcxmlTestUtils.MINIMAL_RECORD));
    }
    
    @Test
    public void testInstanceHasAdminMetadata() throws Exception {
        Entity instance = new InstanceEntity();
        Entity adminMetadata = buildAdminMetadata(instance, TEST_RECORD);
        Assert.assertSame(instance.getChild(Ld4lObjectProp.HAS_ADMIN_METADATA), 
                adminMetadata);
    }
    
    @Test
    public void testHasSource() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertNotNull(adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE));
    }
    
    @Test
    public void testSourceIsAgent() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testSourceHasName() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test 
    public void testHasDescriptionModifier() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertNotNull(adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER));        
    }
    
    @Test
    public void testDescriptionModifierIsAgent() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);  
        Entity agent = adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testDescriptionModifierHasName() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void test005DateTimeValue() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertEquals("2013-03-30T14:56:47", adminMetadata.getValue(
                Ld4lDatatypeProp.CHANGE_DATE));
    }
    
    @Test
    public void test005DateTimeDatatype() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertSame(XsdDatatype.DATETIME, adminMetadata.getAttribute(
                Ld4lDatatypeProp.CHANGE_DATE).getDatatype());
    }
    
    @Test
    public void invalid005DateTimeValue_ThrowsException() throws Exception {
        buildAndExpectException(INVALID_005_VALUE, 
                "Invalid value for control field 005");
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private Entity buildAdminMetadata(Entity entity, String input) 
            throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input))                    
                .setParentEntity(entity);
        return builder.build(params);        
    }
    
    private Entity buildAdminMetadata(String input) 
            throws Exception {
        return buildAdminMetadata(new Entity(), input);      
    }
    
    private void buildAndExpectException(
            Entity entity, String input, String error) throws Exception {            
        expectException(EntityBuilderException.class, error);
        buildAdminMetadata(entity, input);
    }
    
    private void buildAndExpectException(String input, String error) 
            throws Exception {
        buildAndExpectException(new Entity(), input, error);
    }

}

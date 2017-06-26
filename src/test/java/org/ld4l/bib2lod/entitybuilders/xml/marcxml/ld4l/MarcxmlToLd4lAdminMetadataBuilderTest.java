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
 * Tests class MarcxmlToLd4lAdminMetadataBuilder
 */
public class MarcxmlToLd4lAdminMetadataBuilderTest extends AbstractTestClass {
    
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

    private MarcxmlToLd4lAdminMetadataBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new MarcxmlToLd4lAdminMetadataBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    
    @Test
    public void nullRelatedInstance_ThrowsException() throws Exception {
        expectException(
                EntityBuilderException.class, "without a related entity");
        BuildParams params = new BuildParams()
                .setRecord(MarcxmlTestUtils.buildRecordFromString(
                        MarcxmlTestUtils.MINIMAL_RECORD));             
        builder.build(params);        
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(
                EntityBuilderException.class, "without an input record");        
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity());              
        builder.build(params);        
    }
    
    @Test
    public void noAdminMetadataReturnsNull() throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(
                        MarcxmlTestUtils.MINIMAL_RECORD))
                .setRelatedEntity(new Entity());
        Assert.assertNull(builder.build(params));
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
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Assert.assertNotNull(adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE));
    }
    
    @Test
    public void testSourceIsAgent() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testSourceHasName() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test 
    public void testHasDescriptionModifier() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Assert.assertNotNull(adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER));        
    }
    
    @Test
    public void testDescriptionModifierIsAgent() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Entity agent = adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testDescriptionModifierHasName() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Entity agent = adminMetadata.getChild(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void test005DateTimeValue() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Assert.assertEquals("2013-03-30T14:56:47", adminMetadata.getValue(
                Ld4lDatatypeProp.CHANGE_DATE));
    }
    
    @Test
    public void test005DateTimeDatatype() throws Exception {
        Entity adminMetadata = buildAdminMetadata(
                new InstanceEntity(), TEST_RECORD);
        Assert.assertSame(XsdDatatype.DATETIME, adminMetadata.getAttribute(
                Ld4lDatatypeProp.CHANGE_DATE).getDatatype());
    }
    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private Entity buildAdminMetadata(Entity entity, String input) 
            throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input))                    
                .setRelatedEntity(entity);
        return builder.build(params);        
    }
 
}

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.XsdDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;


/**
 * Tests class AdminMetadataBuilder
 */
public class AdminMetadataBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml TEST_RECORD = MockMarcxml.parse(
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='005'>20130330145647.0</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='040' ind1=' ' ind2=' '>" +
                    "<subfield code='b'>fre</subfield>" +
                    "<subfield code='c'>NIC</subfield>" +
                    "<subfield code='d'>NIC</subfield>" +
                    "<subfield code='d'>CtY</subfield>" +
                    "<subfield code='e'>rda</subfield>" +
                    "<subfield code='e'>appm</subfield>" +
                 "</datafield>" +
            "</record>");
    
    public static final MockMarcxml SOURCE_040$a = TEST_RECORD.openCopy()
            .findDatafield("040")
            .addSubfield("a", "NIC")
            .deleteSubfield("c")
            .lock();

    
    public static final MockMarcxml SOURCE_040$a$c = TEST_RECORD.openCopy()
            .findDatafield("040")
            .addSubfield("a", "NIC")
            .lock();
    
    public static final MockMarcxml INVALID_005_VALUE =  MINIMAL_RECORD.openCopy()
            .addControlfield("005", "20130330")
            .lock();
    
    private static BaseMockBib2LodObjectFactory factory;
    private AdminMetadataBuilder builder;   
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {     
        this.builder = new AdminMetadataBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    
    @Test
    public void nullRelatedInstance_ThrowsException() throws Exception {      
        buildAndExpectException(null, MINIMAL_RECORD, 
                "without a related entity");
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(
                EntityBuilderException.class, "without an input record");        
        BuildParams params = new BuildParams()
                .setParent(new Entity());              
        builder.build(params);        
    }
    
    @Test
    public void noAdminMetadataReturnsNull() throws Exception {
        Assert.assertNull(buildAdminMetadata(MINIMAL_RECORD));
    }
    
    @Test
    public void testInstanceHasAdminMetadata() throws Exception {
        Entity instance = new InstanceEntity();
        Entity adminMetadata = buildAdminMetadata(instance, TEST_RECORD);
        Assert.assertSame(instance.getChild( 
                Ld4lObjectProp.HAS_ADMIN_METADATA), adminMetadata);
    }
    
    @Test
    public void testLanguage() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertEquals("http://id.loc.gov/vocabulary/languages/fre",
                adminMetadata.getExternal(Ld4lObjectProp.HAS_LANGUAGE));
    }
    
    @Test
    public void testSource_040$a() throws Exception {
        Entity adminMetadata = buildAdminMetadata(SOURCE_040$a);
        Assert.assertNotNull(
                adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE));
    }
    
    @Test
    public void testSource_040$c() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertNotNull(
                adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE));
    }
    
    @Test
    public void testTwoSources_040$a$c() throws Exception {
        Entity adminMetadata = buildAdminMetadata(SOURCE_040$a$c);
        Assert.assertEquals(2, adminMetadata.getChildren(
                Ld4lObjectProp.HAS_SOURCE).size());
    }
    
    @Test
    public void testSourceIsAgent_040() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testSourceHasName_040() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = adminMetadata.getChild(Ld4lObjectProp.HAS_SOURCE);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test 
    public void testDescriptionModifiers_040$d() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertEquals(2, (adminMetadata.getChildren(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER)).size());        
    }
    
    @Test
    public void testDescriptionModifierIsAgent_040$d() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);  
        Entity agent = (adminMetadata.getChildren(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER)).get(0);
        Assert.assertTrue(agent.hasType(Ld4lAgentType.superClass()));
    }
    
    @Test
    public void testDescriptionModifierHasName_040$d() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity agent = (adminMetadata.getChildren(
                Ld4lObjectProp.HAS_DESCRIPTION_MODIFIER)).get(0);
        Assert.assertEquals("NIC", agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testDescriptionConventions_040$e() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertEquals(2, (adminMetadata.getChildren(
                Ld4lObjectProp.HAS_DESCRIPTION_CONVENTIONS)).size());   
    }
    
    @Test
    public void testDescriptionConventionsValue_040$e() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Entity conventions = (adminMetadata.getChildren(
                Ld4lObjectProp.HAS_DESCRIPTION_CONVENTIONS)).get(0);
        Assert.assertEquals("rda", conventions.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    @Test
    public void testDateTimeValue_005() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertEquals("2013-03-30T14:56:47", adminMetadata.getValue(
                Ld4lDatatypeProp.CHANGE_DATE));
    }
    
    @Test
    public void testDateTimeDatatype_005() throws Exception {
        Entity adminMetadata = buildAdminMetadata(TEST_RECORD);
        Assert.assertSame(XsdDatatype.DATETIME, adminMetadata.getAttribute(
                Ld4lDatatypeProp.CHANGE_DATE).getDatatype());
    }
    
    @Test
    public void invalidDateTimeValue_005_ThrowsException() throws Exception {
        buildAndExpectException(INVALID_005_VALUE, 
                "Invalid value for control field 005");
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private Entity buildAdminMetadata(Entity entity, MockMarcxml input) 
            throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(input.toRecord())                    
                .setParent(entity);
        return builder.build(params);        
    }
    
    private Entity buildAdminMetadata(MockMarcxml input) 
            throws Exception {
        return buildAdminMetadata(new Entity(), input);      
    }
    
    private void buildAndExpectException(
            Entity entity, MockMarcxml input, String error) throws Exception {            
        expectException(EntityBuilderException.class, error);
        buildAdminMetadata(entity, input);
    }
    
    private void buildAndExpectException(MockMarcxml input, String error) 
            throws Exception {
        buildAndExpectException(new Entity(), input, error);
    }

}

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.junit.Assert.fail;
import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lAgentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class AgentBuilder.
 */
public class AgentBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml DUPLICATE_AGENTS = MINIMAL_RECORD.openCopy()
            .addDatafield("260", "3", " ")
            .addSubfield("a", "Lugduni Batavorum :")
            .addSubfield("b", "E.J. Brill")
            .addDatafield("260", " ", " ")
            .addSubfield("a", "Leiden :")
            .addSubfield("b", "E.J. Brill")
            .lock();
    
    public static final MockMarcxml DIFFERENT_AGENTS = DUPLICATE_AGENTS.openCopy()
            .findDatafield("260", 1).replaceSubfield("b", "Random House")
            .lock();
            
    private static final String NAME_SUBFIELD = 
            "<subfield code='b'>E.J. Brill</subfield>";
    
    public static final MockMarcxml AUTHOR_PERSON = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Austen, Jane")
            .addSubfield("d", "1775-1817")
            .lock();

    public static final MockMarcxml AUTHOR_FAMILY = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "3", "")
            .addSubfield("a", "Clark family")
            .lock();
    
    public static final MockMarcxml AUTHOR_COMPLEX_NAME = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Gustaf")
            .addSubfield("b", "V,")
            .addSubfield("c", "King of Sweden,")
            .addSubfield("d", "1858-1950")
            .lock();
    
//    public static final MockMarcxml AUTHOR_FULL_NAME = MINIMAL_RECORD.openCopy()
//            .addDatafield("100", "1", "")
//            .addSubfield("a", "Austen, Jane")
//            .addSubfield("d", "1775-1817")
//            .lock();
//    
//    public static final MockMarcxml AUTHOR_SURNAME = MINIMAL_RECORD.openCopy()
//            .addDatafield("100", "1", "")
//            .addSubfield("a", "Watson,")
//            .addSubfield("c", "Rev.")
//            .addSubfield("d", "1775-1817")
//            .lock();
//    
//    public static final MockMarcxml AUTHOR_FORENAME = MINIMAL_RECORD.openCopy()
//            .addDatafield("100", "1", "")
//            .addSubfield("a", "John")
//            .addSubfield("c", "the Baptist, Saint.")
//            .lock();
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    private AgentBuilder agentBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new InstanceBuilder();   
        this.agentBuilder = new AgentBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void nullParent_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A parent entity is required"); 
        agentBuilder.build(new BuildParams());
    }
    
    @Test
    public void noNameOrSubfield_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A subfield or field is required");  
        BuildParams params = new BuildParams()
                .setParent(new Entity());
        agentBuilder.build(params);
    }
    
    @Test
    public void testNameFromSubfield() throws Exception {
        BuildParams params = new BuildParams()
                .addSubfield(buildSubfieldFromString(NAME_SUBFIELD))                        
                .setParent(new Entity());
        Entity agent = agentBuilder.build(params);
        Assert.assertEquals("E.J. Brill", 
                agent.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testReuseExistingAgent() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(DUPLICATE_AGENTS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertEquals(activity1.getChild(Ld4lObjectProp.HAS_AGENT), 
                activity2.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    @Test
    public void testBuildNewAgent() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(DIFFERENT_AGENTS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertNotEquals(activity1.getChild(Ld4lObjectProp.HAS_AGENT), 
                activity2.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    @Test
    public void testAuthorIsPerson() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_PERSON, "100");
        Assert.assertTrue(author.hasType(Ld4lAgentType.PERSON));
    }
    
    @Test
    public void testAuthorIsFamily() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_FAMILY, "100");
        Assert.assertTrue(author.hasType(Ld4lAgentType.FAMILY));
    }
    
    @Test
    public void testAuthorSimplePersonName() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_PERSON, "100");
        Assert.assertEquals("Austen, Jane", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorPersonNameDatatype() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_PERSON, "100");
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA, 
                author.getAttribute(Ld4lDatatypeProp.NAME).getDatatype());
    }
    
    @Test
    public void testAuthorComplexPersonName() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_COMPLEX_NAME, "100");
        Assert.assertEquals("Gustaf V, King of Sweden", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorFamilyName() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_FAMILY, "100");
        Assert.assertEquals("Clark family", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorFamilyNameDatatype() throws Exception {
        Entity author = buildAgentFromField(AUTHOR_FAMILY, "100");
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA, 
                author.getAttribute(Ld4lDatatypeProp.NAME).getDatatype());
    }
    
    @Test
    @Ignore
    public void testAuthorDates() throws Exception {
        fail("testAuthorDates not yet implemented.");
    }
    
    @Test
    @Ignore
    public void testAuthorBirthdate() throws Exception {
        fail("testAuthorBirthdate not yet implemented.");
    }
    
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
  
    // TODO Integrate into MockMarcxml framework
    private MarcxmlSubfield buildSubfieldFromString(
            String element) throws RecordFieldException {                   
        return new MarcxmlSubfield(
                XmlTestUtils.buildElementFromString(element));
    } 
    
    private Entity buildAgentFromField(MockMarcxml marcxml, String tag) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setField(marcxml.toRecord().getTaggedField(tag));
        return agentBuilder.build(params);
    } 
}

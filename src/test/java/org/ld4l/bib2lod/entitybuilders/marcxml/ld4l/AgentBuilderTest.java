package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
    
    public static final MockMarcxml _260_DUPLICATE_AGENTS = MINIMAL_RECORD.openCopy()
            .addDatafield("260", "3", " ")
            .addSubfield("a", "Lugduni Batavorum :")
            .addSubfield("b", "E.J. Brill")
            .addDatafield("260", " ", " ")
            .addSubfield("a", "Leiden :")
            .addSubfield("b", "E.J. Brill")
            .lock();
    
    public static final MockMarcxml _260_DIFFERENT_AGENTS = _260_DUPLICATE_AGENTS.openCopy()
            .findDatafield("260", 1).replaceSubfield("b", "Random House")
            .lock();
            
    private static final String NAME_SUBFIELD = 
            "<subfield code='b'>E.J. Brill</subfield>";
    
    public static final MockMarcxml _100_AUTHOR_PERSON = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Austen, Jane")
            .addSubfield("d", "1775-1817")
            .lock();

    public static final MockMarcxml _100_AUTHOR_FAMILY = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "3", "")
            .addSubfield("a", "Clark family")
            .lock();
    
    public static final MockMarcxml _100_AUTHOR_COMPLEX_NAME = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Gustaf")
            .addSubfield("b", "V,")
            .addSubfield("c", "King of Sweden,")
            .lock();
    
    public static final MockMarcxml _100_AUTHOR_INITIALS = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Curien, P.-L.")
            .lock();
    
    public static final MockMarcxml _100_AUTHOR_FULLER_NAME_FORM = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            .addSubfield("a", "Claudius")
            .addSubfield("q", "(Claudius Ceccon)")
            .lock();
    
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
    public void testReuseExistingAgent_260() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(_260_DUPLICATE_AGENTS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertEquals(activity1.getChild(Ld4lObjectProp.HAS_AGENT), 
                activity2.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    @Test
    public void testBuildNewAgent_260() throws Exception {
        BuildParams params = new BuildParams()
                .setRecord(_260_DIFFERENT_AGENTS.toRecord());
        Entity instance = instanceBuilder.build(params);
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Entity activity1 = activities.get(1);
        Entity activity2 = activities.get(2);
        Assert.assertNotEquals(activity1.getChild(Ld4lObjectProp.HAS_AGENT), 
                activity2.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    @Test
    public void testAuthorIsPerson_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_PERSON, "100");
        Assert.assertTrue(author.hasType(Ld4lAgentType.PERSON));
    }
    
    @Test
    public void testAuthorIsFamily_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_FAMILY, "100");
        Assert.assertTrue(author.hasType(Ld4lAgentType.FAMILY));
    }
    
    @Test
    public void testAuthorPersonName_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_PERSON, "100");
        Assert.assertEquals("Austen, Jane", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorPersonNameDatatype_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_PERSON, "100");
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA, 
                author.getAttribute(Ld4lDatatypeProp.NAME).getDatatype());
    }
    
    @Test
    public void testAuthorComplexPersonName_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_COMPLEX_NAME, "100");
        Assert.assertEquals("Gustaf V, King of Sweden", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorInitials_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_INITIALS, "100");
        Assert.assertEquals("Curien, P.-L.", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorFullerPersonName_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_FULLER_NAME_FORM, "100");
        Assert.assertEquals("Claudius (Claudius Ceccon)", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorFamilyName_100() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_FAMILY, "100");
        Assert.assertEquals("Clark family", 
                author.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAuthorFamilyNameDatatype_100$a() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_FAMILY, "100");
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA, 
                author.getAttribute(Ld4lDatatypeProp.NAME).getDatatype());
    }
    
    @Test
    public void testAuthorDateValue() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_PERSON, "100");
        Assert.assertEquals("1775-1817", 
                author.getValue(Ld4lDatatypeProp.DATE));
    }
    
    @Test
    public void testAuthorDateDatatype() throws Exception {
        Entity author = buildAgent(_100_AUTHOR_PERSON, "100");
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA, 
                author.getAttribute(Ld4lDatatypeProp.DATE).getDatatype());
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
    
    private Entity buildAgent(MockMarcxml marcxml, String tag) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setField(marcxml.toRecord().getTaggedField(tag));
        return agentBuilder.build(params);
    } 
}

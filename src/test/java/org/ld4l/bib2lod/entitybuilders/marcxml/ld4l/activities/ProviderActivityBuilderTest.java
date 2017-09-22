package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class ProviderActivityBuilder.
 */
public class ProviderActivityBuilderTest extends AbstractTestClass {

    public static final MockMarcxml _260_PUBLISHER = MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("260", " ", " ")
            .addSubfield("a", "New York,")
            .addSubfield("b", "Grune & Stratton,")
            .addSubfield("c", "1957.")
            .lock();

    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new InstanceBuilder();              
    }
    
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testLocation_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Entity location = activity.getChild(Ld4lObjectProp.HAS_LOCATION);
        Assert.assertEquals("New York", 
                location.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAgent_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Entity agent = activity.getChild(Ld4lObjectProp.HAS_AGENT);
        Assert.assertEquals("Grune & Stratton", 
                agent.getValue(Ld4lDatatypeProp.NAME));        
    }
    
    @Test
    public void testDate_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Assert.assertEquals("1957", 
                activity.getValue(Ld4lDatatypeProp.DATE));        
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance(MockMarcxml input) throws Exception {
        return instanceBuilder
                .build(new BuildParams().setRecord(input.toRecord()));
    }
}

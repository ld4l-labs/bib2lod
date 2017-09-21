package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.AuthorActivityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;


public class AuthorActivityBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml _100_AUTHOR =  MINIMAL_RECORD.openCopy()
            .findDatafield("245").findSubfield("a").setValue("full title")
            .addDatafield("100", "0", " ").addSubfield("a", "Manya K'Omalowete a Djonga,")
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
        new AuthorActivityBuilder();
        this.instanceBuilder = new InstanceBuilder();              
    }   
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    @Ignore
    public void testActivityType_100() throws Exception {
        // *** TODO - don't need instance - just go directly to work
        Entity activity = buildActivity(_100_AUTHOR);
        Assert.assertEquals(Ld4lActivityType.AUTHOR_ACTIVITY, 
                activity.getType());
    }
    
    @Test
    @Ignore
    public void testActivityHasAuthor_100() throws Exception {
        Entity activity = buildActivity(_100_AUTHOR);
        Assert.assertNotNull(activity.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // --------------------------------------------------------------------- 

    // *** TODO - don't need instance - just go directly to work
    protected Entity buildActivity(MockMarcxml input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(input.toRecord());
        return buildActivity(params);         
    }
    
    // *** TODO - don't need instance - just go directly to workdraw 
    private Entity buildActivity(BuildParams params) throws Exception {             
        Entity instance = instanceBuilder.build(params);
        return instance.getChild(Ld4lObjectProp.HAS_ACTIVITY); 
    }
}

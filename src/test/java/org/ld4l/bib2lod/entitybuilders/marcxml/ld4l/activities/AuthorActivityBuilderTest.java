package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.AuthorActivityBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
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
    private ActivityBuilder builder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.builder = new AuthorActivityBuilder();          
    }   
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testActivityTypeSpecified_100() throws Exception {
        Entity activity = buildActivity(_100_AUTHOR, 
                Ld4lActivityType.AUTHOR_ACTIVITY);
        Assert.assertEquals(Ld4lActivityType.AUTHOR_ACTIVITY, 
                activity.getType());
    }
    
    @Test
    public void testActivityTypeNotSpecified_100() throws Exception {
        Entity activity = buildActivity(_100_AUTHOR);
        Assert.assertEquals(Ld4lActivityType.AUTHOR_ACTIVITY, 
                activity.getType());
    }
    
    @Test
    public void testActivityHasAuthor_100() throws Exception {
        Entity activity = buildActivity(_100_AUTHOR);
        Assert.assertNotNull(activity.getChild(Ld4lObjectProp.HAS_AGENT));
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // --------------------------------------------------------------------- 

    private Entity buildActivity(MockMarcxml record) 
            throws Exception {
        BuildParams params = new BuildParams() 
                .setField(record.toRecord().getDataField("100"))
                .setParent(new Entity());
        return builder.build(params);         
    }
    
    private Entity buildActivity(
            MockMarcxml record, Type type) throws Exception {
        BuildParams params = new BuildParams() 
                .setField(record.toRecord().getDataField("100"))
                .setType(type)
                .setParent(new Entity());
        return builder.build(params);         
    }

}

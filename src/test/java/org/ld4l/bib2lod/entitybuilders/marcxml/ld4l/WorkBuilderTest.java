package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;



/**
 * Tests class WorkBuilder.
 */
public class WorkBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml NO_TITLE_FIELD = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .deleteDatafield("245")
            .lock();
    
    public static final MockMarcxml INVALID_WORK_TYPE = MINIMAL_RECORD.openCopy()
            .replaceLeader("01050cxm a22003011  4500")
            .addControlfield("001", "102063")
            .lock();

    public static final MockMarcxml NO_WORK_TYPE = INVALID_WORK_TYPE.openCopy()
            .replaceLeader("01050")
            .lock();
    
    public static final MockMarcxml NO_LANGUAGE = INVALID_WORK_TYPE.openCopy()
            .replaceControlfield("008", "860506s1957    nyua     b    000 0      ")
            .lock();
    
    public static final MockMarcxml AUTHOR_ACTIVITY = MINIMAL_RECORD.openCopy()
            .addDatafield("100", "1", "")
            //.findDatafield("100")
            .addSubfield("a", "Austen, Jane")
            .lock();
    

    private static BaseMockBib2LodObjectFactory factory;
    // private InstanceBuilder instanceBuilder;
    private WorkBuilder workBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        // this.instanceBuilder = new InstanceBuilder();   
        this.workBuilder = new WorkBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullInstance_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "An instance is required");
        buildWork(MINIMAL_RECORD, null);
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams()
                .setParent(new InstanceEntity())
                .setRecord(null);                
        workBuilder.build(params);
    } 
    
    @Test
    public void buildWorkFromMinimalRecord_Succeeds() throws Exception {
        buildWork(MINIMAL_RECORD);        
    }

    @Test
    public void noWorkType_Succeeds() throws Exception {
        buildWork(INVALID_WORK_TYPE);
    }
    
    @Test
    public void invalidWorkTypeChar_Succeeds() throws Exception {
        buildWork(INVALID_WORK_TYPE);
    }
    
    @Test
    public void noLanguage_Succeeds() throws Exception {
        buildWork(NO_LANGUAGE);
    }
    
    @Test
    public void testAuthorActivity() throws Exception {
        Entity work = buildWork(AUTHOR_ACTIVITY);
        Assert.assertEquals(1, work.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                Ld4lActivityType.AUTHOR_ACTIVITY).size());
    }
    


    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildWork(MockMarcxml marcxml, InstanceEntity instance) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(marcxml.toRecord());        
        return workBuilder.build(params);
    }
    
    private Entity buildWork(MockMarcxml marcxml) 
            throws Exception {
        return buildWork(marcxml, new InstanceEntity());
    }
    
    
}

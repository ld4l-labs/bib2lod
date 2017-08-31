package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
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

    private WorkBuilder builder;   
    private InstanceEntity defaultInstance;
    
    @Before
    public void setUp() {       
        this.builder = new WorkBuilder();
        this.defaultInstance = new InstanceEntity();  
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
                .setParent(defaultInstance)
                .setRecord(null);                
        builder.build(params);
    } 
    
    @Test
    public void buildWorkFromMinimalRecord_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(MINIMAL_RECORD);        
    }

    @Test
    public void noWorkType_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(INVALID_WORK_TYPE);
    }
    
    @Test
    public void invalidWorkTypeChar_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(INVALID_WORK_TYPE);
    }
    
    @Test
    public void noLanguage_Succeeds() throws Exception {
        buildWorkFromDefaultInstance(NO_LANGUAGE);
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildWork(MockMarcxml marcxml, InstanceEntity instance) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(instance)
                .setRecord(marcxml.toRecord());        
        return builder.build(params);
    }
    
    private Entity buildWorkFromDefaultInstance(MockMarcxml marcxml) throws Exception { 
        return buildWork(marcxml, defaultInstance);
    }
    
}

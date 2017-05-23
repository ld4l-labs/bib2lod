package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lItemBuilder
 */
public class MarcxmlToLd4lItemBuilderTest extends AbstractTestClass {
    
    // TODO Jim: OK to just instantiate with constructor rather than building
    // entire factory infrastructure?
    private MarcxmlToLd4lInstanceBuilder instanceBuilder;
    private MarcxmlToLd4lItemBuilder itemBuilder;   
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new MarcxmlToLd4lInstanceBuilder();
        this.itemBuilder = new MarcxmlToLd4lItemBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    // TODO Jim: does it make any sense/is there any need to test this, given 
    // that the ItemBuilder is only called from the InstanceBuilder?
    public void nullRelatedInstance_Succeeds() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(null);        
        itemBuilder.build(params);        
    }
    

}

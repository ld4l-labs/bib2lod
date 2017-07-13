package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lItemBuilder.
 */
public class MarcxmlToLd4lItemBuilderTest extends AbstractTestClass {
    
    private MarcxmlToLd4lItemBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new MarcxmlToLd4lItemBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = EntityBuilderException.class)
    public void nullRelatedInstance_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setParentEntity(null);        
        builder.build(params);        
    }
    
}

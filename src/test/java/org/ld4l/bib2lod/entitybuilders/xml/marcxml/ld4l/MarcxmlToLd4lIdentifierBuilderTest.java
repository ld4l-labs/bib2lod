package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lIdentifierBuilder
 */
public class MarcxmlToLd4lIdentifierBuilderTest extends AbstractTestClass {
    
    private MarcxmlToLd4lIdentifierBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new MarcxmlToLd4lIdentifierBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = EntityBuilderException.class)
    public void nullRelatedInstance_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(null);             
        builder.build(params);        
    }
    
    @Test (expected = EntityBuilderException.class)
    public void nullField_ThrowsException() throws Exception {
        
        InstanceEntity instance = 
                new InstanceEntity();
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(null);                
        builder.build(params);        
    }
}

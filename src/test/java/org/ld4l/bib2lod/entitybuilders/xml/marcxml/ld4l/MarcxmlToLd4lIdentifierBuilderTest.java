package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lIdentifierBuilder
 */
public class MarcxmlToLd4lIdentifierBuilderTest extends AbstractTestClass {
    
    private MarcxmlToLd4lIdentifierBuilder identifierBuilder;   
    
    @Before
    public void setUp() {       
        this.identifierBuilder = new MarcxmlToLd4lIdentifierBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test (expected = EntityBuilderException.class)
    public void nullRelatedInstance_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(null);             
        identifierBuilder.build(params);        
    }
    
    @Test (expected = EntityBuilderException.class)
    public void nullField_ThrowsException() throws Exception {
        
        InstanceEntity instance = 
                new InstanceEntity(Ld4lInstanceType.superClass());
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(null);                
        identifierBuilder.build(params);        
    }
}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lInstanceType;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lIdentifierBuilder
 */
public class MarcxmlToLd4lIdentifierBuilderTest extends AbstractTestClass {
    
    // TODO Jim: OK to just instantiate with constructor rather than building
    // entire factory infrastructure?
    private MarcxmlToLd4lInstanceBuilder instanceBuilder;
    private MarcxmlToLd4lIdentifierBuilder identifierBuilder;   
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new MarcxmlToLd4lInstanceBuilder();
        this.identifierBuilder = new MarcxmlToLd4lIdentifierBuilder();
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
        identifierBuilder.build(params);        
    }
    
    @Test
    public void nullField_Succeeds() throws Exception {
        
        InstanceEntity instance = 
                new InstanceEntity(Ld4lInstanceType.superClass());
        BuildParams params = new BuildParams()
                .setRelatedEntity(instance)
                .setField(null);                
        identifierBuilder.build(params);        
    }
}

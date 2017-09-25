package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lExtentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class PhysicalDescriptionBuilder
 */
public class PhysicalDescriptionBuilderTest extends AbstractTestClass {
    
    private static final MockMarcxml _300_NO_$a = MINIMAL_RECORD.openCopy()
            .addDatafield("300", "", "").addSubfield("c", "23 cm")
            .lock();
    
    private static final MockMarcxml _300_EXTENT = _300_NO_$a.openCopy()
            .findDatafield("300")
            .deleteSubfield("c")
            .addSubfield("a", "123 p.")
            .lock();
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    private PhysicalDescriptionBuilder physDescrBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() {       
        this.instanceBuilder = new InstanceBuilder();    
        this.physDescrBuilder = new PhysicalDescriptionBuilder();
    }
        
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
      
    @Test
    public void no300_Succeeds() throws Exception {
        buildInstance(MockMarcxml.MINIMAL_RECORD);
    }
    
    @Test
    public void no300$a_Succeeds() throws Exception {
        buildInstance(_300_NO_$a);
    }
    
    @Test
    public void testInstanceHasExtent() throws Exception {
        Entity instance = buildInstance(_300_EXTENT);
        Assert.assertNotNull(instance.getChild(Ld4lObjectProp.HAS_EXTENT));
    }
    
    @Test
    public void testExtentType() throws Exception {
        Entity extent = buildPhysicalDescription(_300_EXTENT, 
                Ld4lExtentType.EXTENT, "300", 'a');
        Assert.assertTrue(extent.hasType(Ld4lExtentType.EXTENT)); 
    }
    
    @Test
    public void testExtentLabel() throws Exception {
        Entity extent = buildPhysicalDescription(_300_EXTENT, 
                Ld4lExtentType.EXTENT, "300", 'a');
        Assert.assertEquals("123 p.", 
                extent.getValue(Ld4lDatatypeProp.LABEL));
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance(MockMarcxml input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(input.toRecord());  
        return instanceBuilder.build(params);
    }
    
    private Entity buildPhysicalDescription(MockMarcxml input, Type type, 
            String tag, char code) throws Exception {
        MarcxmlDataField field = input.toRecord().getDataField(tag);
        BuildParams params = new BuildParams()
                .setParent(new InstanceEntity())
                .setField(field)
                .setSubfield(field.getSubfield(code));
        return physDescrBuilder.build(params);
    }
}

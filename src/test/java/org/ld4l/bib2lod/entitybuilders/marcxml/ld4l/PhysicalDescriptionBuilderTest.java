package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lExtentType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
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

    private PhysicalDescriptionBuilder builder;
    
    @Before
    public void setUp() {         
        this.builder = new PhysicalDescriptionBuilder();
    }
        
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
      
    @Test
    public void no300$a_Succeeds() throws Exception {
        buildPhysicalDescription(_300_NO_$a, Ld4lExtentType.EXTENT, "300", 'c');
    }
    
    @Test
    public void testInstanceHasExtent() throws Exception {
        Entity instance = new Entity();
        Entity extent = buildPhysicalDescription(_300_EXTENT, 
                Ld4lExtentType.EXTENT, "300", 'a', instance);
        Assert.assertTrue(instance.hasChild(Ld4lObjectProp.HAS_EXTENT, extent));
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

    private Entity buildPhysicalDescription(MockMarcxml input, Type type, 
            String tag, char code) throws Exception {
        return buildPhysicalDescription(input, type, tag, code, new Entity());
    }
    
    private Entity buildPhysicalDescription(MockMarcxml input, Type type, 
            String tag, char code, Entity parent) throws Exception {
        MarcxmlDataField field = input.toRecord().getDataField(tag);
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setField(field)
                .setSubfield(field.getSubfield(code));
        return builder.build(params);
    }
}

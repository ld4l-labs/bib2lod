package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlControlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;



/**
 * Tests class AnnotationBuilder.
 */
public class AnnotationBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml _500_ANNOTATION = MINIMAL_RECORD.openCopy()
            .addDatafield("500", " ", " ")
            .addSubfield("a", "Many items are stamped 'impounded.'")      
            .lock();
    
    private AnnotationBuilder builder;
    
    @Before
    public void setUp() {       
        this.builder = new AnnotationBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void nullParent_ThrowsExcepton() throws Exception {
        expectException(EntityBuilderException.class, 
                "A related instance is required");
        MarcxmlDataField field = 
                _500_ANNOTATION.toRecord().getDataField("500");
        BuildParams params = new BuildParams()
                .setField(field);
        builder.build(params); 
    }
    
    @Test
    public void nullField_ThrowsExcepton() throws Exception {
        expectException(EntityBuilderException.class, 
                "A field is required");
        BuildParams params = new BuildParams()
                .setParent(new Entity());
        builder.build(params); 
    }
    
    @Test
    public void invalidFieldType_ThrowsExcepton() throws Exception {
        expectException(EntityBuilderException.class, 
                "A data field is required");
        MarcxmlControlField field = 
                _500_ANNOTATION.toRecord().getControlField("008");
        BuildParams params = new BuildParams()
                .setField(field)
                .setParent(new Entity());
        builder.build(params); 
    }
    
    @Test
    public void testGenericAnnotationMotivation() throws Exception {
        Entity annotation = buildAnnotation(_500_ANNOTATION, "500");
        Assert.assertEquals(Ld4lNamedIndividual.DESCRIBING.uri(), 
                annotation.getExternal(Ld4lObjectProp.HAS_MOTIVATION));
    }
    
    @Test
    public void testGenericAnnotationBodyValue() throws Exception {
        Entity annotation = buildAnnotation(_500_ANNOTATION, "500");       
        Assert.assertEquals("Many items are stamped 'impounded.'", 
                annotation.getValue(Ld4lDatatypeProp.BODY_VALUE));        
    }
    
    @Test
    public void testGenericAnnotationBodyDatatype() throws Exception {
        Entity annotation = buildAnnotation(_500_ANNOTATION, "500");       
        Assert.assertEquals(BibDatatype.LEGACY_SOURCE_DATA,  
                annotation.getAttribute(
                        Ld4lDatatypeProp.BODY_VALUE).getDatatype());           
    }
    
    @Test
    public void testAddRelationshipToParent() throws Exception {
        Entity instance = new Entity();
        Entity annotation = 
                buildAnnotation(_500_ANNOTATION, instance, "500");
        Assert.assertTrue(instance.hasChild(
                Ld4lObjectProp.HAS_ANNOTATION, annotation));
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildAnnotation(MockMarcxml input, Entity parent, 
            String tag) throws Exception {     
        BuildParams params = new BuildParams()
                .setParent(parent)
                .setField(input.toRecord().getDataField(tag));
        return builder.build(params);      
    }
    
    private Entity buildAnnotation(MockMarcxml input, String tag) 
            throws Exception {
        return buildAnnotation(input, new Entity(), tag);      
    }

}

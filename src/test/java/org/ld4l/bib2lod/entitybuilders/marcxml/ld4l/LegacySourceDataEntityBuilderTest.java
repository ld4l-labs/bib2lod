package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.LegacySourceDataType;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;


/**
 * Tests class LegacySourceDataEntityBuilder.
 */
public class LegacySourceDataEntityBuilderTest extends AbstractTestClass {

    private LegacySourceDataEntityBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new LegacySourceDataEntityBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void nullLabel_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "Cannot build legacy source data entity without a value");
        builder.build(new BuildParams());
    }
    
    @Test
    public void testLabelDatatype() throws Exception {
        BuildParams params = new BuildParams()
                .setValue("Legacy data");
        Entity entity = builder.build(params);
        Literal literal = ResourceFactory.createTypedLiteral("Legacy data",
                LegacySourceDataType.getRdfDatatype());  
        Assert.assertEquals(literal, entity.getAttribute(
                Ld4lDatatypeProp.LABEL).toLiteral());
    }
    
    @Test
    public void testType() throws Exception {
        Type type = Ld4lActivityType.ACTIVITY;
        BuildParams params = new BuildParams()
                .setType(type)
                .setValue("Legacy data entity");
        Entity entity = builder.build(params);
        Assert.assertTrue(entity.hasType(type));
    }

}

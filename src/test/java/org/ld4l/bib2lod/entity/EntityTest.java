package org.ld4l.bib2lod.entity;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class Entity
 */
public class EntityTest extends AbstractTestClass {
    
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------    
    private enum MockDatatypeProp implements DatatypeProp {
        TEST_PROP("http://my.namespace.org/", "test");

        private String uri;
        private Property property;
   
        MockDatatypeProp(String namespace, String localName) {
            this.uri = namespace + localName;
            this.property = ResourceFactory.createProperty(uri);
        }

        @Override
        public String uri() {
            return uri;
        }

        @Override
        public Property property() {
            return property;
        }
        
    }
    
    private Entity entity;
    
    @Before
    public void setUp() {
        entity = new Entity();
    }

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test
    public void testEntityIsEmpty()  {
        Assert.assertTrue(entity.isEmpty());
    }
    
    @Test
    public void testEntityIsNotEmpty()  {
        entity.addAttribute(MockDatatypeProp.TEST_PROP, "value");
        Assert.assertFalse(entity.isEmpty());
    }

}

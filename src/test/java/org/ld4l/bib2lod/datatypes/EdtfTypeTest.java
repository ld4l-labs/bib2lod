package org.ld4l.bib2lod.datatypes;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.RDF;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.ontology.DatatypeProp;
import org.ld4l.bib2lod.ontology.Namespace;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class EdtfType
 */
public class EdtfTypeTest extends AbstractTestClass {


    // ---------------------------------------------------------------------
    // Mocking infrastructure
    // ---------------------------------------------------------------------
    
    public static enum MockNamespace implements Namespace {
        TEST_NAMESPACE("http://test.com/");

        private final String uri;
        
        MockNamespace(String uri) {
            this.uri = uri;
        }
        
        @Override
        public String uri() {
            return this.uri;
        }

        @Override
        public String prefix() {
            throw new RuntimeException("Method not implemented.");
        }
        
    }
    
    public static enum MockType implements Type {       
        TEST_TYPE(MockNamespace.TEST_NAMESPACE, "Test");
        
        private final String uri;
        private final Resource ontClass;
        
        /**
         * Constructor
         */
        MockType(Namespace namespace, String name) {
            this.uri = namespace.uri() + name;
            this.ontClass = ResourceFactory.createResource(uri);
        }

        @Override
        public String uri() {
            throw new RuntimeException("Method not implemented.");
        }

        @Override
        public Resource ontClass() {
            return ontClass;
        }        
    }
    
    public static enum MockDatatypeProp implements DatatypeProp {
        TEST_DATAPROP(MockNamespace.TEST_NAMESPACE, "testProp");

        private String uri;
        private Property property;
        
        /**
         * Constructor
         */
        MockDatatypeProp(MockNamespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
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
        entity = new Entity(MockType.TEST_TYPE);
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void valueHasDatatype() {
        
        String value = "value";
        Datatype datatype = BibDatatype.EDTF;
        DatatypeProp prop = MockDatatypeProp.TEST_DATAPROP;
        
        Attribute attribute = new Attribute(value, datatype);
        entity.addAttribute(prop, attribute);
        String uri = "http://test.com/my_uri";
        entity.buildResource(uri);
        Model actual = entity.getModel();
        
        Model expected = ModelFactory.createDefaultModel();
        Resource resource = expected.createResource(uri);
        resource.addProperty(RDF.type, MockType.TEST_TYPE.ontClass());
        resource.addProperty(prop.property(), value, datatype.rdfType());
        
        Assert.assertTrue(expected.isIsomorphicWith(actual));
    }
}

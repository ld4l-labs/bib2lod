package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import java.util.List;
import java.util.Map;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.IdentifierBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.BaseMarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlTaggedField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;



/**
 * Tests class IdentifierBuilder
 */
public class IdentifierBuilderTest extends AbstractTestClass {

    public static final String _001 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";

    public static final String _035_INVALID_VALUE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" + 
                    "<subfield code='a'>(test)test(test)</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_DUPLICATES_001 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" + 
                    "<subfield code='a'>102063</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_NEW_ORG_CODE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" + 
                    "<subfield code='a'>(ABC)12345</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_INVALID_SUBFIELD_CODE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" + 
                    "<subfield code='b'>102063</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_CANCELLED = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" + 
                    "<subfield code='z'>xyz</subfield>" + 
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_NIC = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>(NIC)notisAAL3258</subfield>" +
                    "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    // Not used. Keep if we want to make this an OclcIdentifier instead of just
    // a Local identifier.
    public static final String _035_OCLC = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>(OCoLC)1345399</subfield>" +
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_NO_ORG_CODE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>1345399</subfield>" +
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";

    
    private static BaseMockBib2LodObjectFactory factory;
    private IdentifierBuilder builder;  
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.builder = new IdentifierBuilder();
    }    

    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRelatedInstance_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "without a related entity");        
        BuildParams params = new BuildParams()
                .setField(null);                
        builder.build(params);  
    }
    
    @Test 
    public void nullField_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "without an input field");        
        BuildParams params = new BuildParams()
                .setParent(new Entity());             
        builder.build(params);        
    }
    
    @Test
    public void invalidSubfieldCode_ThrowsException() throws Exception {
        buildAndExpectException(
                _035_INVALID_SUBFIELD_CODE, 35, 'b', "Invalid subfield");
    }
    
    @Test
    public void invalidValue_035_ThrowsException() throws Exception {
        buildAndExpectException(
                _035_INVALID_VALUE, 35, 'a', "Invalid value for field");
    }
    
    @Test
    public void testStatusCancelled_035() throws Exception {
        Entity identifier = buildIdentifier(_035_CANCELLED, 35, 'z');
        Assert.assertEquals(Ld4lNamedIndividual.CANCELLED.uri(), 
                identifier.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    
    @Test
    public void test_001() throws Exception {   
        Entity identifier = buildIdentifier(_001, 1);
        Assert.assertEquals("102063", 
                identifier.getValue(Ld4lDatatypeProp.VALUE));
    }
    
    @Test
    public void testOrgCode_035() throws Exception {       
        Entity identifier = buildIdentifier(_035_NIC, 35, 'a');
        Entity source = identifier.getChild(Ld4lObjectProp.HAS_SOURCE);
        Attribute attribute = source.getAttribute(Ld4lDatatypeProp.LABEL);
        Literal literal = ResourceFactory.createTypedLiteral(
                "NIC", BibDatatype.LEGACY_SOURCE_DATA.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral());        
    }
    
    @Test 
    public void testDuplicateIdentifiers_001_035() throws Exception {    
        BuildParams params = new BuildParams()
                .setRecord(MarcxmlTestUtils.buildRecordFromString(_035_DUPLICATES_001));
        InstanceBuilder builder = new InstanceBuilder();
        Entity instance = builder.build(params);
        List<Entity> identifiers = instance.getChildren(Ld4lObjectProp.IDENTIFIED_BY);
        boolean found = false;
        for (Entity identifier : identifiers) {
            String value = identifier.getValue(Ld4lDatatypeProp.VALUE);
            if (value.equals("102063")) {
                found = true;
                break;
            }
        }
        Assert.assertFalse(found);
    }
    
    @Test
    public void testNewOrgCode_035() throws Exception {
        Entity identifier = buildIdentifier(_035_NEW_ORG_CODE, 35, 'a'); 
        Entity source = identifier.getChild(Ld4lObjectProp.HAS_SOURCE);
        Attribute attribute = source.getAttribute(Ld4lDatatypeProp.LABEL);
        Literal literal = ResourceFactory.createTypedLiteral(
                "ABC", BibDatatype.LEGACY_SOURCE_DATA.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral());  
    }
    
    @Test
    public void testNewKeyAddedToSources_035() throws Exception {
        buildIdentifier(_035_NEW_ORG_CODE, 35, 'a');
        Map<String, Entity> sources = 
                IdentifierBuilder.getSources();
        Assert.assertTrue(sources.containsKey("ABC"));       
    }
    
    @Test
    public void testNewSourceAddedToSources_035() throws Exception {
        buildIdentifier(_035_NEW_ORG_CODE, 35, 'a');
        Map<String, Entity> sources = 
                IdentifierBuilder.getSources();
        Entity source = sources.get("ABC");
        Attribute attribute = source.getAttribute(Ld4lDatatypeProp.LABEL);
        Literal literal = ResourceFactory.createTypedLiteral(
                "ABC", BibDatatype.LEGACY_SOURCE_DATA.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral()); 
    }
    
    @Test
    public void testNoSource_035() throws Exception {
        Entity identifier = buildIdentifier(_035_NO_ORG_CODE, 35, 'a');
        Assert.assertNull(identifier.getChild(Ld4lObjectProp.HAS_SOURCE));        
    }
    
    @Test
    public void testValueWithNoSource_035() throws Exception {
        Entity identifier = buildIdentifier(_035_NO_ORG_CODE, 35, 'a');
        Assert.assertEquals("1345399", identifier.getValue(Ld4lDatatypeProp.VALUE));      
    }

    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildIdentifier( 
            Entity entity, String input, int tag) throws Exception {

        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(input);
        MarcxmlTaggedField field = record.getTaggedField(tag);
        BuildParams params = new BuildParams() 
                .setParent(entity)
                .setRecord(record)
                .setField((BaseMarcxmlField) field);
        return builder.build(params);   
    }

    private Entity buildIdentifier(String input, int tag) 
            throws Exception {
        return buildIdentifier(new Entity(), input, tag);     
    }
    
    private Entity buildIdentifier(Entity entity, String input,  
            int tag, char code) throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(input);  
        MarcxmlDataField field = record.getDataField(tag);
        MarcxmlSubfield subfield = field.getSubfield(code);
        BuildParams params = new BuildParams() 
                .setParent(entity)
                .setRecord(record)
                .setField(field)
                .addSubfield(subfield);
        return builder.build(params);
    }
    
    private Entity buildIdentifier(String input,  
            int tag, char code) throws Exception {
        return buildIdentifier(new Entity(), input, tag, code);
    }
    
    private void buildAndExpectException(
            Entity entity, String input, int tag, String error) 
                    throws Exception {            
        expectException(EntityBuilderException.class, error);
        buildIdentifier(entity, input, tag);
    }
    
    private void buildAndExpectException(String input, int tag, String error) 
            throws Exception {
        buildAndExpectException(new Entity(), input, tag, error);
    }
    
    private void buildAndExpectException(
            Entity entity, String input, int tag, char code, String error) 
                    throws Exception {            
        expectException(EntityBuilderException.class, error);
        buildIdentifier(entity, input, tag, code);
    }
    
    private void buildAndExpectException(
            String input, int tag, char code, String error) throws Exception {        
        buildAndExpectException(new Entity(), input, tag, code, error);
    }
    
}

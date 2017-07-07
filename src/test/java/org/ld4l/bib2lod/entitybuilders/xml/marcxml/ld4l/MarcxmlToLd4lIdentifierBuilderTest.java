package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlDataField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlField;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlSubfield;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class MarcxmlToLd4lIdentifierBuilder
 */
public class MarcxmlToLd4lIdentifierBuilderTest extends AbstractTestClass {

    public static final String _001 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _035_SAME_AS_001 = 
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
    
    public static final String INVALID_SUBFIELD_CODE = 
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
    
    public static final String _035_OCLC = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='035' ind1=' ' ind2=' '>" +
                "<datafield tag='035' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>(OCoLC)1345399</subfield>" +
                "</datafield>" +
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";

    
    private MarcxmlToLd4lIdentifierBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new MarcxmlToLd4lIdentifierBuilder();
    }
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
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
                .setRelatedEntity(new Entity());             
        builder.build(params);        
    }
    
    @Test
    public void invalidSubfieldCode_ThrowsException() throws Exception {
        buildAndExpectException(
                INVALID_SUBFIELD_CODE, 35, 'b', "Invalid subfield");
    }
    
    @Test
    @Ignore
    public void invalid035Value_ThrowsException() throws Exception {
        fail("Method invalid035Value_ThrowsException not implemented.");
    }
    
    @Test
    @Ignore
    public void testStatusCurrent() throws Exception {
        fail("Method testStatusCurrent not implemented.");
    }
    
    @Test
    @Ignore
    public void testStatusCancelled() throws Exception {
        fail("Method testStatusCancelled not implemented.");
    }
    
    @Test
    @Ignore
    public void test001() throws Exception {       
        fail("Method test001 not implemented.");
    }
    
    @Test 
    @Ignore
    public void test035SameAs001() throws Exception {       
        fail("Method test035SameAs001 not implemented.");
    }
    
    @Test
    @Ignore
    public void test035WithSource() throws Exception {       
        fail("Method test035Nic not implemented.");
    }
    
    @Test 
    @Ignore
    public void test035Oclc() throws Exception {       
        fail("Method test035Oclc not implemented.");
    }

    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
    
    private Entity buildIdentifier( 
            Entity entity, String input, int tag) throws Exception {

        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(input);
        MarcxmlField field = record.getField(tag);
        BuildParams params = new BuildParams() 
                .setRelatedEntity(entity)
                .setRecord(record)
                .setField(field);
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
                .setRelatedEntity(entity)
                .setRecord(record)
                .setField(field)
                .setSubfield(subfield);
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

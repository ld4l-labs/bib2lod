package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.records.RecordField;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class MarcxmlToLd4lTitleElementBuilder
 */
public class MarcxmlToLd4lTitleElementBuilderTest extends AbstractTestClass {
    
    // ----------------------------------------------------------------------
    // Mocking infrastructure
    // ----------------------------------------------------------------------
    
    public static class MockRecordField implements RecordField {}

   
    public static final String WRONG_CODE_FOR_MAIN_TITLE_ELEMENT = 
            "<subfield code='b'>subfield b</subfield>";
 
    public static final String MAIN_TITLE_ELEMENT_WITH_WHITESPACE = 
            "<subfield code='a'> main title </subfield>";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON = 
            "<subfield code='a'>main title :</subfield>";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_COLON =
            "<subfield code='a'>main title:</subfield>";
    

    public static final String SUBTITLE = "subtitle";
    
    public static final String SUBTITLE_WITH_WHITESPACE = " subtitle ";
    
    
    private MarcxmlToLd4lTitleElementBuilder titleElementBuilder;
      
    @Before
    public void setUp() throws Exception {    
        this.titleElementBuilder = new MarcxmlToLd4lTitleElementBuilder();
    }  
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test (expected = EntityBuilderException.class)
    public void nullTitleElementType_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setValue("title element");
        titleElementBuilder.build(params);
    }
       
    @Test (expected = EntityBuilderException.class)
    public void invalidTitleElementType_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleType.TITLE);
        titleElementBuilder.build(params);
    }
    
    @Test (expected = EntityBuilderException.class)
    public void unimplementedTitleElementType_ThrowsException() 
            throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.PART_NAME_ELEMENT);
        titleElementBuilder.build(params);
    }    
    
    @Test (expected = EntityBuilderException.class)
    public void noSubfieldForMainTitleElement_ThrowsException() 
            throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setValue("title element");
        titleElementBuilder.build(params);
    }
    
    @Test (expected = EntityBuilderException.class)
    public void wrongSubfieldType_ThrowsException() throws Exception {
        RecordField field = new MockRecordField();
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setSubfield(field);
        titleElementBuilder.build(params);
    }
    
    @Test (expected = EntityBuilderException.class)
    public void wrongSubfieldCodeForMainTitleElement_ThrowsException() 
            throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setSubfield(MarcxmlTestUtils.buildSubfieldFromString(
                        WRONG_CODE_FOR_MAIN_TITLE_ELEMENT))                             
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT);
        titleElementBuilder.build(params);
    }
    
    @Test
    public void testTrimMainTitleElement() throws Exception {
        buildMainTitleElementAndExpectValue(
                MAIN_TITLE_ELEMENT_WITH_WHITESPACE, "main title");       
    }
    
    @Test
    public void testRemoveFinalSpaceColonFromMainTitleElement() 
            throws Exception {
        buildMainTitleElementAndExpectValue(
                MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON, "main title");       
    }
    
    @Test
    public void testRemoveFinalColonFromMainTitleElement() throws Exception {
        buildMainTitleElementAndExpectValue(
                MAIN_TITLE_ELEMENT_WITH_FINAL_COLON, "main title");       
    }
    
    @Test (expected = EntityBuilderException.class)
    public void noValueForSubtitleElement_ThrowsException() throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.SUBTITLE_ELEMENT);
        titleElementBuilder.build(params);
    }
    
    @Test (expected = EntityBuilderException.class)
    public void emptyValueForSubtitleElement_ThrowsException() 
            throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.SUBTITLE_ELEMENT)
                .setValue("");
        titleElementBuilder.build(params);
    }
    
    @Test 
    public void testSubtitleElementValue() throws Exception {
        buildSubtitleElementAndExpectValue(SUBTITLE, "subtitle");
    }
    
    @Test 
    public void testTrimSubtitleValue() throws Exception {
        buildSubtitleElementAndExpectValue(
                SUBTITLE_WITH_WHITESPACE, "subtitle");
    }

    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
       
    private void buildTitleElementAndExpectValue(
            BuildParams params, String expectedValue) 
                    throws Exception {
        Entity title = titleElementBuilder.build(params);
        Assert.assertEquals(expectedValue,
                title.getAttribute(Ld4lDatatypeProp.VALUE).getValue());        
    }  
    
    private void buildMainTitleElementAndExpectValue(
            String subfield, String expected) throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity()) 
                .setSubfield(MarcxmlTestUtils.buildSubfieldFromString(subfield))
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT);   
        buildTitleElementAndExpectValue(params, expected);          
    }
 
    private void buildSubtitleElementAndExpectValue(
            String input, String expected) throws Exception {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity()) 
                .setValue(input)
                .setType(Ld4lTitleElementType.SUBTITLE_ELEMENT);  
        buildTitleElementAndExpectValue(params, expected);           
    }
    
}

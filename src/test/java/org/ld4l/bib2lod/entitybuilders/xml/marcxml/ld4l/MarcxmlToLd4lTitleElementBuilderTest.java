package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleElementType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lTitleType;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MarcxmlToLd4lTitleElementBuilder
 */
public class MarcxmlToLd4lTitleElementBuilderTest extends AbstractTestClass {
    
       
    public static final String MAIN_TITLE_ELEMENT_WITH_WHITESPACE = " main title ";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON = "main title :";
    
    public static final String MAIN_TITLE_ELEMENT_WITH_FINAL_COLON = "main title:";

    public static final String SUBTITLE = "subtitle";
    
    public static final String SUBTITLE_WITH_WHITESPACE = " subtitle ";
    
    
    private MarcxmlToLd4lTitleElementBuilder builder;
      
    @Before
    public void setUp() throws Exception {    
        this.builder = new MarcxmlToLd4lTitleElementBuilder();
    }  
    
    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------
    
    @Test 
    public void nullTitle_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, "title is required");
        BuildParams params = new BuildParams()
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setValue("title element");
        builder.build(params);
    }    

    @Test 
    public void nullTitleElementType_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, "type is required");
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setValue("title element");
        builder.build(params);
    }
       
    @Test 
    public void invalidTitleElementType_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "invalid title element type");
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleType.TITLE);
        builder.build(params);
    } 
    
    @Test 
    public void nullValue_ThrowsException() throws Exception {         
        expectException(EntityBuilderException.class, 
                "Non-empty string value required");
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT);
        builder.build(params);
    }
    
    @Test
    public void emptyValue_ThrowsException() throws Exception {       
        expectException(EntityBuilderException.class, 
                "Non-empty string value required");
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity())
                .setType(Ld4lTitleElementType.MAIN_TITLE_ELEMENT)
                .setValue("");
        builder.build(params);
    }

    @Test
    public void testTrimMainTitleElement() throws Exception {        
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_WHITESPACE, "main title");             
    }
    
    @Test
    public void testRemoveFinalSpaceColonFromMainTitleElement() 
            throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_SPACE_COLON, "main title");                 
    }
    
    @Test
    public void testRemoveFinalColonFromMainTitleElement() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.MAIN_TITLE_ELEMENT, 
                MAIN_TITLE_ELEMENT_WITH_FINAL_COLON, "main title");       
    }
    
    @Test 
    public void testTrimSubtitleValue() throws Exception {
        buildAndExpectValue(Ld4lTitleElementType.SUBTITLE_ELEMENT, 
                SUBTITLE_WITH_WHITESPACE, "subtitle");             
    }

    
    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------
       
    
    private void buildAndExpectValue(Ld4lTitleElementType type, 
            String inputValue, String expectedValue) 
                    throws EntityBuilderException {
        BuildParams params = new BuildParams()
                .setRelatedEntity(new Entity()) 
                .setValue(inputValue)
                .setType(type); 
        Entity titleElement = builder.build(params);
        Assert.assertEquals(expectedValue,
                titleElement.getAttribute(Ld4lDatatypeProp.VALUE).getValue());  
    }
    
}

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import static org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml.MINIMAL_RECORD;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.testrecord.MockMarcxml;

/**
 * Tests class TitleBuilder.
 */
public class TitleBuilderTest extends AbstractTestClass {
    
    public static final MockMarcxml TITLE_WITH_WHITESPACE = MINIMAL_RECORD.openCopy()
            .addControlfield("001", "102063")
            .findDatafield("245").findSubfield("a").setValue(" main title ")
            .lock();

    public static final MockMarcxml TITLE_WITH_FINAL_SPACE_COLON = TITLE_WITH_WHITESPACE.openCopy()
            .findDatafield("245").findSubfield("a").setValue("main title :")
            .lock();
    
    public static final MockMarcxml TITLE_WITH_FINAL_COLON = TITLE_WITH_WHITESPACE.openCopy()
            .findDatafield("245").findSubfield("a").setValue("main title:")
            .lock();
    
    public static final MockMarcxml TITLE_WITH_SUBTITLE = TITLE_WITH_FINAL_SPACE_COLON.openCopy()
            .findDatafield("245").addSubfield("b", "subtitle")
            .lock();
    
    public static final MockMarcxml TITLE_WITH_TWO_SUBTITLES = TITLE_WITH_FINAL_SPACE_COLON.openCopy()
            .findDatafield("245").addSubfield("b", "subtitle one : subtitle two")
            .lock();
    

    private static BaseMockBib2LodObjectFactory factory;
    private TitleBuilder builder; 
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws Exception {    
        this.builder = new TitleBuilder();
    }  
    
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        builder.build(new BuildParams());
    }
    
    @Test 
    public void nullBibEntity_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A parent entity is required");
        BuildParams params = new BuildParams()
                .setRecord(MINIMAL_RECORD.toRecord());
        builder.build(params);
    }
    
    @Test
    public void testTitleElementCount() throws Exception {
       Entity title = buildTitle(TITLE_WITH_TWO_SUBTITLES);
       List<Entity> titleElements = title.getChildren(Ld4lObjectProp.HAS_PART);
       Assert.assertEquals(3, titleElements.size());
    }
    
    @Test
    public void testTitleElementRank() throws Exception {
        Entity title = buildTitle(TITLE_WITH_TWO_SUBTITLES);  
        List<Entity> titleElements = title.getChildren(Ld4lObjectProp.HAS_PART);
        
        List<String> expected = new ArrayList<>(
                Arrays.asList("1", "2", "3"));
        
        List<String> actual = new ArrayList<>();
        for (Entity titleElement : titleElements) {
            String rank = titleElement.getValue(Ld4lDatatypeProp.RANK);   
            actual.add(rank);
        }
        
        Assert.assertTrue(expected.equals(actual));      
    }

    @Test 
    public void testTitleValueFromMainTitleElement() throws Exception {
        buildTitleAndExpectValue(MINIMAL_RECORD, "main title");
    }
    
    @Test 
    public void testTitleValueTrimWhitespace() throws Exception {
        buildTitleAndExpectValue(TITLE_WITH_WHITESPACE, "main title");
    }
    
    @Test 
    public void testTitleValueWithFinalSpaceColon() throws Exception {
        buildTitleAndExpectValue(TITLE_WITH_FINAL_SPACE_COLON, "main title");   
    }
    
    @Test 
    public void testTitleValueWithFinalColon() throws Exception {
        buildTitleAndExpectValue(TITLE_WITH_FINAL_COLON, "main title");  
    }
    
    @Test
    public void testTitleValueWithSubtitle() throws Exception {
        buildTitleAndExpectValue(TITLE_WITH_SUBTITLE, "main title : subtitle");  
    }
     
    @Test
    public void testTitleValueWithTwoSubtitles() throws Exception {
        buildTitleAndExpectValue(TITLE_WITH_TWO_SUBTITLES, 
                "main title : subtitle one : subtitle two");     
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildTitle(MockMarcxml marcxml) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(new Entity())
                .setRecord(marcxml.toRecord());        
        return builder.build(params);   
    }
    
    private void buildTitleAndExpectValue(
            MockMarcxml marcxml, String value) throws Exception {
        Entity title = buildTitle(marcxml);
        Assert.assertEquals(value,
                title.getAttribute(Ld4lDatatypeProp.VALUE).getValue());        
    }
      
}

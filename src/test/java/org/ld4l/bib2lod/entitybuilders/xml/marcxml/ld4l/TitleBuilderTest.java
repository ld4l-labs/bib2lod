package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

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
import org.ld4l.bib2lod.records.Record;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;

/**
 * Tests class TitleBuilder.
 */
public class TitleBuilderTest extends AbstractTestClass {
    
    public static final String TITLE_WITH_WHITESPACE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'> main title </subfield>" +     
                "</datafield>" + 
            "</record>";
    
    public static final String TITLE_WITH_FINAL_SPACE_COLON = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title :</subfield>" +     
                "</datafield>" + 
            "</record>";
    
    public static final String TITLE_WITH_FINAL_COLON = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title:</subfield>" +     
                "</datafield>" + 
            "</record>";
    
    public static final String TITLE_WITH_SUBTITLE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title :</subfield>" +     
                    "<subfield code='b'>subtitle</subfield>" + 
                "</datafield>" + 
            "</record>";
    

    public static final String TITLE_WITH_TWO_SUBTITLES = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title :</subfield>" +     
                    "<subfield code='b'>subtitle one : subtitle two</subfield>" + 
                "</datafield>" + 
            "</record>"; 
    
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
        buildTitle(new Entity(), (Record) null);
    }
    
    @Test 
    public void nullBibEntity_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A parent entity is required");
        buildTitle((Entity) null, MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    @Test
    public void testTitleElementCount() throws Exception {
       Entity title = buildTitle(new Entity(), TITLE_WITH_TWO_SUBTITLES);
       List<Entity> titleElements = title.getChildren(Ld4lObjectProp.HAS_PART);
       Assert.assertEquals(3, titleElements.size());
    }
    
    @Test
    public void testTitleElementRank() throws Exception {
        Entity title = buildTitle(new Entity(), TITLE_WITH_TWO_SUBTITLES);  
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
        buildTitleAndExpectValue(
                new Entity(), MarcxmlTestUtils.MINIMAL_RECORD, "main title");
    }
    
    @Test 
    public void testTitleValueTrimWhitespace() throws Exception {
        buildTitleAndExpectValue(
                new Entity(), TITLE_WITH_WHITESPACE, "main title");
    }
    
    @Test 
    public void testTitleValueWithFinalSpaceColon() throws Exception {
        buildTitleAndExpectValue(
                new Entity(), TITLE_WITH_FINAL_SPACE_COLON, "main title");   
    }
    
    @Test 
    public void testTitleValueWithFinalColon() throws Exception {
        buildTitleAndExpectValue(
                new Entity(), TITLE_WITH_FINAL_COLON, "main title");  
    }
    
    @Test
    public void testTitleValueWithSubtitle() throws Exception {
        buildTitleAndExpectValue(
                new Entity(), TITLE_WITH_SUBTITLE, "main title : subtitle");  
    }
     
    @Test
    public void testTitleValueWithTwoSubtitles() throws Exception {
        buildTitleAndExpectValue(new Entity(), TITLE_WITH_TWO_SUBTITLES, 
                "main title : subtitle one : subtitle two");     
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildTitle(Entity bibEntity, String marcxml) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        return buildTitle(bibEntity, record);
    }
    
    private Entity buildTitle(Entity bibEntity, Record record) 
            throws Exception {
        BuildParams params = new BuildParams()
                .setParent(bibEntity)
                .setRecord(record);        
        return builder.build(params);        
    }
    
    private void buildTitleAndExpectValue(
            Entity bibEntity, String marcxml, String value) throws Exception {
        Entity title = buildTitle(bibEntity, marcxml);
        Assert.assertEquals(value,
                title.getAttribute(Ld4lDatatypeProp.VALUE).getValue());        
    }
      
}

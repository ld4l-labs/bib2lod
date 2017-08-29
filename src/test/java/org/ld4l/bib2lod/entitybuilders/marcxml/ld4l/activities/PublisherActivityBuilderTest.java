package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.BibDatatype;
import org.ld4l.bib2lod.datatypes.Ld4lCustomDatatypes.EdtfType;
import org.ld4l.bib2lod.entity.Attribute;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamedIndividual;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lNamespace;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;


/** 
 * Tests class PublisherActivityBuilder.
 */
public class PublisherActivityBuilderTest extends AbstractTestClass {

    public static final String _008_NO_LOCATION = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s1957       a     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _008_NO_DATE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>860506s        nyua     b    000 0 eng  </controlfield>" + 
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
            "</record>";
    
    public static final String _008_TWO_CHAR_PUB_LOCATION = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='008'>750226c18529999ne bx p       0   b0eng  </controlfield>" + 
                "<datafield tag='245' ind1='3' ind2='0'>" +
                    "<subfield code='a'>main title</subfield>" +          
                "</datafield>" + 
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='c'>1957.</subfield>" +
            "</datafield>" +
            "</record>";
    
    public static final String _260_PUBLISHER = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton,</subfield>" +
                    "<subfield code='c'>1957.</subfield>" +
                "</datafield>" +
            "</record>"; 
 
    public static final String _260_PUBLISHER_NO_DATE = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton,</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String _260_PUBLISHER_NO_LOCATION = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='b'>Grune &amp; Stratton,</subfield>" +
                    "<subfield code='c'>1957.</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String _260_CURRENT_PUBLISHER = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton,</subfield>" +
                    "<subfield code='c'>1957.</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String TWO_260 = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='a'>Lugduni Batavorum :</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +               
                "</datafield>" +
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>Leiden :</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +                 
            "</datafield>" +
            "</record>"; 
    
    public static final String _008_260$c_DATES = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='c'>1957.</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String _001 = 
            "<controlfield tag='001'>102063</controlfield>";  
    
    public static final String _245 = 
            "<datafield tag='245'><subfield code='a'>text</subfield></datafield>";


    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder instanceBuilder;
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.instanceBuilder = new InstanceBuilder();              
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testPublisherStatus_008() throws Exception {
        Entity activity = buildActivity();
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));        
    }
    
    @Test
    public void testPublisherDate_008() throws Exception {
        Entity activity = buildActivity();
        Attribute date = activity.getAttribute(Ld4lDatatypeProp.DATE);
        Literal literal = ResourceFactory.createTypedLiteral("1957",
                EdtfType.getRdfDatatype());
        Assert.assertEquals(literal, date.toLiteral());  
    }
    
    @Test
    public void testTwoCharPubLocation_008() throws Exception {
        Entity activity = buildActivity(_008_TWO_CHAR_PUB_LOCATION); 
        Assert.assertEquals("http://id.loc.gov/vocabulary/countries/ne", 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION));
    }
    
    @Test
    public void testPublisherLocation_008() throws Exception {
        Entity activity = buildActivity();
        Assert.assertEquals("http://id.loc.gov/vocabulary/countries/nyu", 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION));
    }

    @Test
    public void testLocation_008() throws Exception {       
        Entity activity = buildActivity(); 
        String locationUri = 
                activity.getExternal(Ld4lObjectProp.HAS_LOCATION);
        Assert.assertEquals(
                Ld4lNamespace.LC_COUNTRIES.uri() + "nyu", locationUri);
     }

    @Test
    public void noLocation_008_Succeeds() throws Exception {
        buildActivity(_008_NO_LOCATION); 
    }
    
    @Test
    public void testActivityDate_008() throws Exception {
        Entity activity = buildActivity(); 
        Attribute attribute = activity.getAttribute(Ld4lDatatypeProp.DATE);
        Literal literal = ResourceFactory.createTypedLiteral(
                "1957", BibDatatype.EDTF.rdfType());
        Assert.assertEquals(literal, attribute.toLiteral());  
    }
    
    @Test
    public void blankDate_008_Succeeds() throws Exception {
        buildActivity(_008_NO_DATE); 
    }
    
    @Test
    public void testCurrentPublisher_008() throws Exception {
       Entity activity = buildActivity();
       Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
               activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    
    @Test
    public void testThreePublishers() throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(
                TWO_260);
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);   
        Assert.assertEquals(3, 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                        Ld4lActivityType.PUBLISHER_ACTIVITY).size());      
    }
    
    @Test
    public void testCurrentPublishers_008_260() throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(
                _260_CURRENT_PUBLISHER);
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);   
        Assert.assertEquals(2, 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                        Ld4lActivityType.PUBLISHER_ACTIVITY).size());      
    }
    
    public void testCurrentPublisher_260_ind1Value3() 
            throws Exception {
        Entity instance = buildInstance(_260_CURRENT_PUBLISHER); 
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY))
                .get(1);
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    
    @Test
    public void testCurrentPublisherStatus_ind1ValueEmpty() 
            throws Exception {
        Entity instance = buildInstance(_260_CURRENT_PUBLISHER); 
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY))
                .get(1);        
        Assert.assertEquals(Ld4lNamedIndividual.CURRENT.uri(), 
                activity.getExternal(Ld4lObjectProp.HAS_STATUS));
    }
    

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------

    private Entity buildActivity() throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.getMinimalRecord());  
        return buildActivity(params);
    }

    private Entity buildActivity(String input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input));
        return buildActivity(params);         
    }   
    
    private Entity buildActivity(BuildParams params) 
            throws Exception { 
        Entity instance = instanceBuilder.build(params);
        return instance.getChild(Ld4lObjectProp.HAS_ACTIVITY); 
    }
    
    private Entity buildInstance(String input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input));  
        return instanceBuilder.build(params);
    }
    
}

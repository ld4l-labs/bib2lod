package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;


/**
 * Tests class InstanceBuilder
 */
public class InstanceBuilderTest extends AbstractTestClass {
    
    public static final String RESPONSIBILITY_STATEMENT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                    "<subfield code='c'>responsibility statement</subfield>" +
                "</datafield>" + 
            "</record>";
    
    /* Provision activity statements (260 $a$b$c) */
    public static final String PAS_ABC_PUNCT = 
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
    
    public static final String PAS_ABC_NO_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton</subfield>" +
                    "<subfield code='c'>1957</subfield>" +
                "</datafield>" +
            "</record>"; 

    public static final String PAS_AB_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton.</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String PAS_AB_NO_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York</subfield>" +
                    "<subfield code='b'>Grune &amp; Stratton</subfield>" +
                "</datafield>" +
            "</record>"; 

    public static final String PAS_AC_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                    "<subfield code='c'>1957.</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String PAS_AC_NO_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York</subfield>" +
                    "<subfield code='c'>1957</subfield>" +
                "</datafield>" +
            "</record>"; 

    public static final String PAS_BC_PUNCT = 
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
    
    public static final String PAS_BC_NO_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='b'>Grune &amp; Stratton</subfield>" +
                    "<subfield code='c'>1957</subfield>" +
                "</datafield>" +
            "</record>"; 

    
    public static final String PAS_A_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York,</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String PAS_A_NO_PUNCT = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>New York</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String MULTIPLE_PAS = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='a'>Lugduni Batavorum :</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +
                "</datafield>" +
                "<datafield tag='260' ind1='3' ind2=' '>" +
                    "<subfield code='a'>Leiden :</subfield>" +
                    "<subfield code='b'>E.J. Brill</subfield>" +
                "</datafield>" +
                // 264 not yet converted
                //"<datafield tag='264' ind1='3' ind2=' '>" +
                  //"<subfield code='a'>New York :</subfield>" +
                  //"<subfield code='b'>Random House</subfield>" +
                //"</datafield>" +
            "</record>"; 
    
    public static final String _260_PUBLISHER_AND_MANUFACTURER = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='b'>National Technical Information Service,</subfield>" +
                    "<subfield code='f'>Oak Ridge National Laboratory </subfield>" +
                "</datafield>" +
            "</record>"; 
    
    public static final String _260_ONE_FIELD_TWO_PUBLISHERS = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1='2' ind2=' '>" +
                    "<subfield code='a'>place1</subfield>" +
                    "<subfield code='c'>date1</subfield>" +
                    "<subfield code='b'>name2</subfield>" +
                    "<subfield code='c'>date2</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    private static BaseMockBib2LodObjectFactory factory;
    private InstanceBuilder builder;   
    
    @BeforeClass
    public static void setUpOnce() throws Exception {
        factory = new BaseMockBib2LodObjectFactory();  
        factory.addInstance(EntityBuilderFactory.class, 
                new MarcxmlToLd4lEntityBuilderFactory());
    }
    
    @Before
    public void setUp() throws RecordFieldException {       
        this.builder = new InstanceBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams()
                .setRecord(null);                
        builder.build(params);
    } 
    
    @Test
    public void minimalRecord_Succeeds() throws Exception {
        buildInstance(MarcxmlTestUtils.MINIMAL_RECORD);
    }
    
    @Test 
    public void testResponsibilityStatement() throws Exception {
        Entity instance = 
                buildInstance(RESPONSIBILITY_STATEMENT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.RESPONSIBILITY_STATEMENT).getValue();
        Assert.assertEquals("responsibility statement", statement);
    }
    
    @Test
    public void testPasAbcPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_ABC_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York, Grune & Stratton, 1957", statement);        
    }
    
    @Test
    public void testPasAbcNoPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_ABC_NO_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York: Grune & Stratton, 1957", statement);        
    }
    
    @Test
    public void testPasAbPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_AB_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York, Grune & Stratton", statement);        
    }
    
    @Test
    public void testPasAbNoPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_AB_NO_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York: Grune & Stratton", statement);        
    }
    
    @Test
    public void testPasAcPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_AC_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York, 1957", statement);        
    }
    
    @Test
    public void testPasAcNoPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_AC_NO_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York, 1957", statement);        
    }
    
    @Test
    public void testPasBcPunct() throws Exception { 
        Entity instance = 
                buildInstance(PAS_BC_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("Grune & Stratton, 1957", statement);        
    }
    
    @Test
    public void testPasBcNoPunct() throws Exception { 
        Entity instance = 
                buildInstance(PAS_BC_NO_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("Grune & Stratton, 1957", statement);        
    }
    
    @Test
    public void testPasAPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_A_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York", statement);        
    }
    
    @Test
    public void testPasANoPunct() throws Exception {
        Entity instance = 
                buildInstance(PAS_A_NO_PUNCT);
        String statement = instance.getAttribute(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).getValue();
        Assert.assertEquals("New York", statement);        
    }
    
    @Test
    public void testMultiplePas() throws Exception {
        Entity instance = 
                buildInstance(MULTIPLE_PAS);
        Assert.assertEquals(2, instance.getAttributes(
                Ld4lDatatypeProp.PROVISION_ACTIVITY_STATEMENT).size());        
    }

    @Test
    public void testPublisherAndManufacturer() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER); 
        Assert.assertEquals(2,
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY).size());     
    }   
    
    @Test
    public void testManufacturerWithPublisher() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER); 
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Assert.assertEquals(Ld4lActivityType.MANUFACTURER_ACTIVITY,
                activities.get(1).getType());     
    }    
    
    @Test
    public void testPublisherWithManufacturer() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER_AND_MANUFACTURER); 
        List<Entity> activities = 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY);
        Assert.assertEquals(Ld4lActivityType.PUBLISHER_ACTIVITY,
                activities.get(0).getType());     
    }   
    
    @Test
    public void testTwoPublishers_260() throws Exception {
        Entity instance = buildInstance(_260_ONE_FIELD_TWO_PUBLISHERS); 
        Assert.assertEquals(3,
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY).size());    
    }     
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance(String marcxml) 
            throws Exception {
        MarcxmlRecord record = new MarcxmlRecord(
                XmlTestUtils.buildElementFromString(marcxml));
        BuildParams params = new BuildParams()
                .setRecord(record);        
        return builder.build(params);
    }
}

package org.ld4l.bib2lod.entitybuilders.xml.marcxml.ld4l.activities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lDatatypeProp;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class ProviderActivityBuilder.
 */
public class ProviderActivityBuilderTest extends AbstractTestClass {

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
    public void testLocation_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Entity location = activity.getChild(Ld4lObjectProp.HAS_LOCATION);
        Assert.assertEquals("New York", 
                location.getValue(Ld4lDatatypeProp.NAME));
    }
    
    @Test
    public void testAgent_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Entity agent = activity.getChild(Ld4lObjectProp.HAS_AGENT);
        Assert.assertEquals("Grune & Stratton", 
                agent.getValue(Ld4lDatatypeProp.NAME));        
    }
    
    @Test
    public void testDate_260() throws Exception {
        Entity instance = buildInstance(_260_PUBLISHER);
        Entity activity = (instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY)
                .get(1));
        Assert.assertEquals("1957", 
                activity.getValue(Ld4lDatatypeProp.DATE));        
    }
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private Entity buildInstance(String input) throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.buildRecordFromString(input));  
        return instanceBuilder.build(params);
    }
}

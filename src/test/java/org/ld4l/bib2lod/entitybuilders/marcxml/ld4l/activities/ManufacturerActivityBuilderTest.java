package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.activities;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilderFactory;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.InstanceBuilder;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.MarcxmlToLd4lEntityBuilderFactory;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lActivityType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.records.RecordField.RecordFieldException;
import org.ld4l.bib2lod.records.xml.marcxml.MarcxmlRecord;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.BaseMockBib2LodObjectFactory;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

public class ManufacturerActivityBuilderTest extends AbstractTestClass {
    
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
 
    public static final String _260_MANUFACTURER = 
            "<record>" +
                "<leader>01050cam a22003011  4500</leader>" +
                "<controlfield tag='001'>102063</controlfield>" + 
                "<controlfield tag='008'>860506s1957    nyua     b    000 0 eng  </controlfield>" +  
                "<datafield tag='245' ind1='0' ind2='0'>" +
                    "<subfield code='a'>full title</subfield>" +  
                "</datafield>" +   
                "<datafield tag='260' ind1=' ' ind2=' '>" +
                    "<subfield code='e'>Oak Ridge, Tenn. :</subfield>" +
                    "<subfield code='f'>Oak Ridge National Laboratory </subfield>" +
                    "<subfield code='g'>1974</subfield>" +
                "</datafield>" +
            "</record>"; 
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testManufacturer_260() throws Exception {
        MarcxmlRecord record = MarcxmlTestUtils.buildRecordFromString(
                _260_MANUFACTURER);
        BuildParams params = new BuildParams() 
                .setRecord(record);
        Entity instance = instanceBuilder.build(params);       
        Assert.assertEquals(1, 
                instance.getChildren(Ld4lObjectProp.HAS_ACTIVITY, 
                        Ld4lActivityType.MANUFACTURER_ACTIVITY).size());      
    }  
    
    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------


}

package org.ld4l.bib2lod.entitybuilders.marcxml.ld4l;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.entity.Entity;
import org.ld4l.bib2lod.entity.InstanceEntity;
import org.ld4l.bib2lod.entitybuilders.BuildParams;
import org.ld4l.bib2lod.entitybuilders.EntityBuilder.EntityBuilderException;
import org.ld4l.bib2lod.entitybuilders.marcxml.ld4l.ItemBuilder;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lItemType;
import org.ld4l.bib2lod.ontology.ld4l.Ld4lObjectProp;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.MarcxmlTestUtils;

/**
 * Tests class ItemBuilder.
 */
public class ItemBuilderTest extends AbstractTestClass {
    
    private ItemBuilder builder;   
    
    @Before
    public void setUp() {       
        this.builder = new ItemBuilder();
    }
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test 
    public void nullRelatedInstance_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A related instance is required");
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.getMinimalRecord());  
        builder.build(params);        
    }
    
    @Test 
    public void nullRecord_ThrowsException() throws Exception {
        expectException(EntityBuilderException.class, 
                "A record is required");
        BuildParams params = new BuildParams() 
                .setParent(new Entity()); 
        builder.build(params);        
    }
    
    @Test 
    public void testItemType() throws Exception {
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.getMinimalRecord())
                .setParent(new Entity()); 
        Entity item = builder.build(params);     
        Assert.assertTrue(item.hasType(Ld4lItemType.ITEM));
    }
    
    @Test 
    public void testInstanceHasItem() throws Exception {
        Entity instance = new InstanceEntity();
        BuildParams params = new BuildParams() 
                .setRecord(MarcxmlTestUtils.getMinimalRecord())
                .setParent(instance); 
        Entity item = builder.build(params);   
        Assert.assertTrue(instance.hasChild(Ld4lObjectProp.HAS_ITEM, item));
    }    
    
}

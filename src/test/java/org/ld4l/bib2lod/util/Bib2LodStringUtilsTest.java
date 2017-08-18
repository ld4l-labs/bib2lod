package org.ld4l.bib2lod.util;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class Bib2LodStringUtils.
 */
public class Bib2LodStringUtilsTest extends AbstractTestClass {
    
    @Test
    public void testPunctFinal() {
        Assert.assertTrue(Bib2LodStringUtils.endsWithPunct("test."));
    }
    
    @Test
    public void testNonPunctFinal() {
        Assert.assertFalse(Bib2LodStringUtils.endsWithPunct("test"));        
    }
    
    @Test 
    public void testRemoveFinalPunct() {
        Assert.assertEquals(
                "test", Bib2LodStringUtils.removeFinalPunct("test."));
    }
    
    @Test 
    public void testRemoveFinalPunctAndWhitespace() {
        Assert.assertEquals("test", 
                Bib2LodStringUtils.removeFinalPunctAndWhitespace("test : "));
    }
    
    @Test 
    public void testTrim() {
        Assert.assertEquals(
                "test", Bib2LodStringUtils.trim(" test : "));        
    }

}

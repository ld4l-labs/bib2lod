package org.ld4l.bib2lod.util.collections;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Tests class MapOfLists
 */
public class MapOfListsTest extends AbstractTestClass {

    @Test
    public void testRemoveValue() {
        MapOfUniqueLists<Integer, String> map = new MapOfUniqueLists<>(); 
        List<String> list = Arrays.asList("one", "eins", "un", "eins");              
        map.addValues(1,  list);
        map.removeValue(1, "eins");
        Assert.assertEquals(2,  map.getValues(1).size());        
    }
    
    @Test
    public void testRemoveAbsentValue() {
        MapOfUniqueLists<Integer, String> map = new MapOfUniqueLists<>(); 
        List<String> list = Arrays.asList("one", "eins");
        map.addValues(1,  list);
        map.removeValue(1, "un");
        Assert.assertEquals(2,  map.getValues(1).size());        
    }  
}

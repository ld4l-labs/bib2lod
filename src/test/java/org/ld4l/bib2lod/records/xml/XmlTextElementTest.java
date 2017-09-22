package org.ld4l.bib2lod.records.xml;

import org.junit.Assert;
import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;
import org.ld4l.bib2lod.testing.xml.XmlTestUtils;
import org.w3c.dom.Element;

/**
 * Tests default methods of interface XmlTextElement.
 */
public class XmlTextElementTest extends AbstractTestClass {
    
    // ---------------------------------------------------------------------
    // Mocking infrastructure
    // ---------------------------------------------------------------------
    
    /*
     * A concrete implementation for testing the interface.
     */
    public static class MockXmlTextElement implements XmlTextElement {

        private String textValue;
        
        public MockXmlTextElement(Element domElement) {  
            this.textValue = retrieveTextValue(domElement);
        }
        
        @Override
        public String getTextValue() {
            return textValue;
        }       
    }
    
    MockXmlTextElement element;
    
    // ---------------------------------------------------------------------
    // The tests
    // ---------------------------------------------------------------------
    
    @Test
    public void testGetTrimmedTextValue() throws Exception {
        Assert.assertEquals("test", 
                build("<field> test : </field>").getTrimmedTextValue());
    }
    
    @Test
    public void testRetrieveTextValue() throws Exception {
        Assert.assertEquals("test", 
                build("<field>test</field>").getTextValue());
    }
    
    @Test
    public void testNonTextElement() throws Exception {
        Element domElement = XmlTestUtils.buildElementFromString(
                "<field><subfield>test</subfield></field>");
        element = new MockXmlTextElement(domElement);
        Assert.assertNull(element.retrieveTextValue(domElement));
    }
    
    @Test
    public void testEmptyElement() throws Exception {
        Element domElement = XmlTestUtils.buildElementFromString(
                "<field></field>");
        element = new MockXmlTextElement(domElement);
        Assert.assertNull(element.retrieveTextValue(domElement));
    }
    
    @Test
    public void testGetTextSubstring() throws Exception {
        element = build("<field>elephant</field>");
        Assert.assertEquals("pha", element.getTextSubstring(3, 6));
    }
    
    @Test
    public void testGetCharAt() throws Exception {
        element = build("<field>elephant</field>");
        Assert.assertEquals('h', element.getCharAt(4));
    }
    
    @Test
    public void testPunctFinal() {
        Assert.assertTrue(XmlTextElement.endsWithPunct("test."));
    }
    
    @Test
    public void testNonPunctFinal() {
        Assert.assertFalse(XmlTextElement.endsWithPunct("test"));        
    }
    
    @Test 
    public void testRemoveFinalPunct() {
        Assert.assertEquals(
                "test", XmlTextElement.removeFinalPunct("test."));
    }
    
    @Test 
    public void testRemoveFinalPunctAndWhitespace() {
        Assert.assertEquals("test", 
                XmlTextElement.removeFinalPunctAndWhitespace("test : "));
    }
    
    @Test 
    public void testTrim() {
        Assert.assertEquals(
                "test", XmlTextElement.trim(" test : "));        
    }

    // ---------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------
    
    private MockXmlTextElement build(String s) throws Exception {
        Element element = XmlTestUtils.buildElementFromString(s);
        return new MockXmlTextElement(element);
    }
    
}

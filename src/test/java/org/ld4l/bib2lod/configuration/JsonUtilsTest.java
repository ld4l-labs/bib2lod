package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.JsonUtils.InvalidNodeTypeException;
import org.ld4l.bib2lod.configuration.JsonUtils.RequiredNodeEmptyException;
import org.ld4l.bib2lod.configuration.JsonUtils.RequiredNodeMissingException;
import org.ld4l.bib2lod.configuration.JsonUtils.RequiredNodeNullException;
import org.ld4l.bib2lod.test.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Tests of org.ld4l.bib2lod.configuration.JsonUtils
 */
public class JsonUtilsTest extends AbstractTestClass {
    
    private enum Key {
        
        MISSING("key_missing"),
        TO_NULL("key_to_null"),
        TO_BOOLEAN("key_to_boolean"),
        TO_STRING_EMPTY("key_to_string_empty"),
        TO_ARRAY_EMPTY("key_to_array_empty"),
        TO_OBJECT_EMPTY("key_to_object_empty"),
        TO_STRING_NOT_EMPTY("key_to_string_not_empty"),
        TO_ARRAY_NOT_EMPTY("key_to_array_not_empty"),
        TO_OBJECT_NOT_EMPTY("key_to_object_not_empty");
      
        final private String string;
        
        Key(String string) {
            this.string = string;
        }
    }
    
    private final String STRING_VALUE = "text";
     
    private ObjectNode node;
    private ArrayNode arrayNode;
    private ObjectNode objectNode;
    private TextNode textNode;
    private TextNode emptyTextNode;

    @Before
    public void setup() {
        
        // Build the JsonNode to test
        node = jsonObject();
        
        // Add a null value
        node.set(Key.TO_NULL.string, null);
        
        // Add a boolean value
        node.put(Key.TO_BOOLEAN.string, true);

        // Add text values (one empty, one not empty)
        emptyTextNode = emptyJsonText();
        node.set(Key.TO_STRING_EMPTY.string, emptyTextNode);
        
        textNode = jsonText(STRING_VALUE);
        node.set(Key.TO_STRING_NOT_EMPTY.string, textNode);
        
        // Add array values (one empty, one not empty) 
        node.set(Key.TO_ARRAY_EMPTY.string, jsonArray());

        arrayNode = jsonArray()
                    .add("one")
                    .add("two")
                    .add("three");
        node.set(Key.TO_ARRAY_NOT_EMPTY.string, arrayNode);
        
        // Add object values (one empty, one not empty)
        node.set(Key.TO_OBJECT_EMPTY.string, jsonObject());
        
        objectNode = jsonObject()
                     .put("one", "eins")
                     .put("two", "zwei")
                     .put("three", "drei");
        node.set(Key.TO_OBJECT_NOT_EMPTY.string, objectNode);
        
    }

    
    /*
     * Text node
     */
    
    /*
     * Optional text node
     */

    @Test
    public void optionalKeyForTextNodeMissing_Succeeds() {
        assertSame(null, JsonUtils.getOptionalTextNode(
                node, Key.MISSING.string));
    }
    
    @Test
    public void optionalTextNodeNull_Succeeds() {
        assertSame(null, JsonUtils.getOptionalTextNode(
                node, Key.TO_NULL.string));
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalTextNodeInvalidType_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_BOOLEAN.string);
    }
   
    @Test
    public void optionalTextNodeEmptyString_Succeeds() {
        assertSame(emptyTextNode, JsonUtils.getOptionalTextNode(
                node, Key.TO_STRING_EMPTY.string));
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalTextNodeEmptyArray_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_ARRAY_EMPTY.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalTextNodeEmptyObject_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_OBJECT_EMPTY.string);
    }

    @Test
    public void optionalTextNodeValid_Succeeds() {
        assertSame(textNode, JsonUtils.getOptionalTextNode(
                node, Key.TO_STRING_NOT_EMPTY.string));
    }
    
    @Test
    public void optionalStringValueValid_Succeeds() {
        assertSame(STRING_VALUE, JsonUtils.getOptionalStringValue(
                node, Key.TO_STRING_NOT_EMPTY.string));
    }
    
    /*
     * Required text node
     */

    @Test (expected = RequiredNodeMissingException.class)
    public void requiredKeyForTextNodeMissing_ThrowsException() {
        JsonUtils.getRequiredTextNode(node, Key.MISSING.string);               
    }
    
    @Test (expected = RequiredNodeNullException.class)
    public void requiredTextNodeNull_ThrowsException() {
        JsonUtils.getRequiredTextNode(node, Key.TO_NULL.string);
    }
  
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredTextNodeInvalidType_ThrowsException() {
        JsonUtils.getRequiredTextNode(node, Key.TO_BOOLEAN.string);
    }

    @Test (expected = RequiredNodeEmptyException.class)
    public void requiredTextNodeEmptyString_ThrowsException() {
        JsonUtils.getRequiredTextNode(node, Key.TO_STRING_EMPTY.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredTextNodeEmptyArray_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_ARRAY_EMPTY.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredTextNodeEmptyObject_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_OBJECT_EMPTY.string);
    }
    
    @Test
    public void requiredTextNodeValid_Succeeds() {
        assertSame(textNode, JsonUtils.getRequiredTextNode(
                node, Key.TO_STRING_NOT_EMPTY.string));
    }

    @Test
    public void requiredStringValueValid_Succeeds() {
        assertSame(STRING_VALUE, JsonUtils.getRequiredStringValue(
                node, Key.TO_STRING_NOT_EMPTY.string));
    }
    

    /*
     * Array value
     */
    
    /*
     * Optional array value
     */

    @Test
    public void optionalKeyForArrayNodeMissing_Succeeds() {
        assertSame(null, JsonUtils.getOptionalArrayNode(
                node, Key.MISSING.string));
    }
   
    @Test
    public void optionalArrayNodeNull_Succeeds() {
        assertSame(null, JsonUtils.getOptionalArrayNode(
                node, Key.TO_NULL.string));
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalArrayNodeInvalidType_ThrowsException() {
        JsonUtils.getOptionalArrayNode(node, Key.TO_BOOLEAN.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalArrayNodeEmptyString_ThrowsException() {
        JsonUtils.getOptionalArrayNode(node, Key.TO_STRING_EMPTY.string);
    }

    @Test
    public void optionalArrayNodeEmptyArray_Succeeds() {
        assertEquals(jsonArray(), JsonUtils.getOptionalArrayNode(
                node, Key.TO_ARRAY_EMPTY.string));
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalArrayNodeEmptyObject_ThrowsException() {
        JsonUtils.getOptionalTextNode(node, Key.TO_OBJECT_EMPTY.string);
    }                     

    @Test
    public void optionalArrayNodeValid_Succeeds() {
        assertSame(arrayNode, JsonUtils.getRequiredArrayNode(
                node, Key.TO_ARRAY_NOT_EMPTY.string));  
                
    }
    
    /* 
     * Required array value
     */
    
    @Test (expected = RequiredNodeMissingException.class)
    public void requiredKeyForArrayNodeMissing_ThrowsException() {
        JsonUtils.getRequiredArrayNode(node, Key.MISSING.string);
    }
    
    @Test (expected = RequiredNodeNullException.class)
    public void requiredArrayNodeNull_ThrowsException() {
        JsonUtils.getRequiredArrayNode(node, Key.TO_NULL.string);
    }
  
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredArrayNodeInvalidType_ThrowsException() {
        JsonUtils.getRequiredArrayNode(node, Key.TO_BOOLEAN.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredArrayNodeEmptyString_ThrowsException() {
        JsonUtils.getOptionalArrayNode(node, Key.TO_STRING_EMPTY.string);
    }
    
    @Test (expected = RequiredNodeEmptyException.class)
    public void requiredArrayNodeEmptyArray_ThrowsException() {
        JsonUtils.getRequiredArrayNode(node, Key.TO_ARRAY_EMPTY.string);
    }
        
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredArrayNodeEmptyObject_ThrowsException() {
        JsonUtils.getRequiredArrayNode(node, Key.TO_OBJECT_EMPTY.string);
    }
  
    @Test
    public void requiredArrayNodeValid_Succeeds() {
        assertSame(arrayNode, JsonUtils.getRequiredArrayNode(
                node, Key.TO_ARRAY_NOT_EMPTY.string));  
                
    }
    
    
    /*
     * Object value
     */
    
    /*
     * Optional object value
     */

    @Test
    public void optionalKeyForObjectNodeMissing_Succeeds() {
        assertSame(null, JsonUtils.getOptionalObjectNode(
                node, Key.MISSING.string));
    }
    
    @Test
    public void optionalObjectNodeNull_Succeeds() {
        assertSame(null, JsonUtils.getOptionalObjectNode(
                node, Key.TO_NULL.string));
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalObjectNodeInvalidType_ThrowsException() {
        JsonUtils.getOptionalObjectNode(node, Key.TO_BOOLEAN.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalObjectNodeEmptyString_ThrowsException() {
        JsonUtils.getOptionalObjectNode(node, Key.TO_STRING_EMPTY.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void optionalObjectNodeEmptyArray_ThrowsException() {
        JsonUtils.getOptionalObjectNode(node, Key.TO_ARRAY_EMPTY.string);
    }
    
    @Test
    public void optionalObjectNodeEmptyObject_Succeeds() {
        assertEquals(jsonObject(), JsonUtils.getOptionalObjectNode(
                node, Key.TO_OBJECT_EMPTY.string));
    }
   
    @Test
    public void optionalObjectNodeValid_Succeeds() {
        String key = Key.TO_OBJECT_NOT_EMPTY.string;
        assertSame(objectNode.get(key), 
                JsonUtils.getRequiredObjectNode(node, key)); 
    }
    
    /*
     * Required object value
     */
 
    @Test (expected = RequiredNodeMissingException.class)
    public void requiredKeyForObjectNodeMissing_ThrowsException() {
        JsonUtils.getRequiredObjectNode(node, Key.MISSING.string);               
    }
    
    @Test (expected = RequiredNodeNullException.class)
    public void requiredObjectNodeNull_ThrowsException() {
        JsonUtils.getRequiredObjectNode(node, Key.TO_NULL.string);
    }
  
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredObjectNodeInvalidType_ThrowsException() {
        JsonUtils.getRequiredObjectNode(node, Key.TO_BOOLEAN.string);
    }

    @Test (expected = InvalidNodeTypeException.class)
    public void requiredObjectNodeEmptyString_ThrowsException() {
        JsonUtils.getOptionalObjectNode(node, Key.TO_STRING_EMPTY.string);
    }
    
    @Test (expected = InvalidNodeTypeException.class)
    public void requiredObjectNodeEmptyArray_ThrowsException() {
        JsonUtils.getOptionalObjectNode(node, Key.TO_ARRAY_EMPTY.string);
    }
    
    @Test (expected = RequiredNodeEmptyException.class)
    public void requiredObjectNodeEmptyObject_ThrowsException() {
        JsonUtils.getRequiredObjectNode(node, Key.TO_OBJECT_EMPTY.string);
    }
  
    @Test
    public void requiredObjectNodeValid_Succeeds() {
        String key = Key.TO_OBJECT_NOT_EMPTY.string;
        assertSame(objectNode.get(key), 
                JsonUtils.getRequiredObjectNode(node, key));               
    }
    
}

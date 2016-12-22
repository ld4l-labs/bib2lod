package org.ld4l.bib2lod.configuration.options;

import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;
import org.ld4l.bib2lod.configuration.options.JsonUtils.InvalidTypeException;
import org.ld4l.bib2lod.configuration.options.JsonUtils.RequiredKeyMissingException;
import org.ld4l.bib2lod.configuration.options.JsonUtils.RequiredValueEmptyException;
import org.ld4l.bib2lod.configuration.options.JsonUtils.RequiredValueNullException;
import org.ld4l.bib2lod.testing.AbstractTestClass;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonUtilsTest extends AbstractTestClass {
    
    private enum Key {
        
        MISSING("key_missing"),
        TO_NULL("key_to_null"),
        TO_EMPTY_STRING("key_to_empty_string"),
        TO_TEXT("key_to_text"),
        TO_EMPTY_ARRAY("key_to_empty_array"),
        TO_EMPTY_OBJECT("key_to_empty_object"),
        TO_BOOLEAN("key_to_boolean");
        
        final private String string;
        
        Key(String string) {
            this.string = string;
        }
    }
    
    private final String TEXT_VALUE = "text";
     
    private ObjectNode node;
    
    @Before
    public void setup() {
        node = jsonObject();
        node.put(Key.TO_TEXT.string, TEXT_VALUE);
        node.set(Key.TO_NULL.string, null);
        node.put(Key.TO_BOOLEAN.string, true);
        node.putObject(Key.TO_EMPTY_ARRAY.string);
        node.putArray(Key.TO_EMPTY_OBJECT.string);
        node.put(Key.TO_EMPTY_STRING.string, "");
    }
    
    
    /*
     * Optional value tests
     */

    @Test
    public void optionalKeyMissing_Succeeds() {
        assertSame(null, JsonUtils.getOptionalJsonStringValue(
                node, Key.MISSING.string));
    }
    
    @Test
    public void optionalStringValueNull_Succeeds() {
        assertSame(null, JsonUtils.getOptionalJsonStringValue(
                node, Key.TO_NULL.string));
    }
    
    @Test (expected = InvalidTypeException.class)
    public void optionalStringValueInvalidType_ThrowsException() {
        JsonUtils.getOptionalJsonStringValue(node, Key.TO_BOOLEAN.string);
    }
    
    @Test (expected = InvalidTypeException.class)
    public void optionalStringValueEmptyArray_ThrowsException() {
        JsonUtils.getOptionalJsonStringValue(node, Key.TO_EMPTY_ARRAY.string);
    }
    
    @Test (expected = InvalidTypeException.class)
    public void optionalStringValueEmptyObject_ThrowsException() {
        JsonUtils.getOptionalJsonStringValue(node, Key.TO_EMPTY_OBJECT.string);
    }
    
    @Test
    public void optionalStringValueEmpty_Succeeds() {
        assertSame("", JsonUtils.getOptionalJsonStringValue(
                node, Key.TO_EMPTY_STRING.string));
    }
   
    @Test
    public void optionalStringValueValid_Succeeds() {
        assertSame(TEXT_VALUE, JsonUtils.getOptionalJsonStringValue(
                node, Key.TO_TEXT.string));
    }
    
    /*
     * Required value tests
     */
    
    @Test (expected = RequiredKeyMissingException.class)
    public void requiredKeyMissing_ThrowsException() {
        JsonUtils.getRequiredJsonStringValue(node, Key.MISSING.string);
    }
    
    @Test (expected = RequiredValueNullException.class)
    public void requiredStringValueNull_ThrowsException() {
        JsonUtils.getRequiredJsonStringValue(node, Key.TO_NULL.string);
    }
  
    @Test (expected = InvalidTypeException.class)
    public void requiredStringValueInvalidType_ThrowsException() {
        JsonUtils.getRequiredJsonStringValue(node, Key.TO_BOOLEAN.string);
    }

    @Test (expected = InvalidTypeException.class)
    public void requiredStringValueEmptyArray_ThrowsException() {
        JsonUtils.getOptionalJsonStringValue(node, Key.TO_EMPTY_ARRAY.string);
    }
    
    @Test (expected = InvalidTypeException.class)
    public void requiredStringValueEmptyObject_ThrowsException() {
        JsonUtils.getOptionalJsonStringValue(node, Key.TO_EMPTY_OBJECT.string);
    }
    
    @Test (expected = RequiredValueEmptyException.class)
    public void requiredStringValueEmpty_ThrowsException() {
        JsonUtils.getRequiredJsonStringValue(node, Key.TO_EMPTY_STRING.string);
    }
  
    @Test
    public void requiredStringValueValid_Succeeds() {
        assertSame(TEXT_VALUE, JsonUtils.getRequiredJsonStringValue(
                node, Key.TO_TEXT.string));
    }

}

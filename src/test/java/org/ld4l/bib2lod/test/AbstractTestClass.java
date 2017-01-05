package org.ld4l.bib2lod.test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public abstract class AbstractTestClass {


    // ----------------------------------------------------------------------
    // JSON utility methods
    // ----------------------------------------------------------------------

    protected TextNode jsonText(String text) {
        return JsonNodeFactory.instance.textNode(text);
    }
    
    protected TextNode emptyJsonText() {
        return JsonNodeFactory.instance.textNode("");
    }
    
    protected ArrayNode jsonArray() {
        return JsonNodeFactory.instance.arrayNode();
    }

    protected ObjectNode jsonObject() {
        return JsonNodeFactory.instance.objectNode();
    }

    
    // TODO Not sure if needed
    private enum SpecialJsonValue {
        REMOVE
    }

    // TODO Not sure if needed
    protected static final SpecialJsonValue JSON_REMOVE = 
            SpecialJsonValue.REMOVE;

    // TODO Not sure if needed
    protected void setFieldValue(ObjectNode node, String fieldName,
            Object newValue) {
        if (newValue == null) {
            node.putNull(fieldName);
        } else if (newValue == SpecialJsonValue.REMOVE) {
            node.remove(fieldName);
        } else if (newValue instanceof JsonNode) {
            node.set(fieldName, (JsonNode) newValue);
        } else if (newValue instanceof Integer) {
            node.put(fieldName, (Integer) newValue);
        } else {
            node.put(fieldName, String.valueOf(newValue));
        }
    }    
      
}

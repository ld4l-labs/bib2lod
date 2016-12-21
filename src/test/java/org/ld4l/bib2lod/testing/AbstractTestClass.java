package org.ld4l.bib2lod.testing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

public abstract class AbstractTestClass {

    public AbstractTestClass() {
        // TODO Auto-generated constructor stub
    }

    // ----------------------------------------------------------------------
    // JSON utility methods
    // ----------------------------------------------------------------------

    protected TextNode jsonText(String text) {
        return JsonNodeFactory.instance.textNode(text);
    }
    
    protected ArrayNode jsonArray() {
        return JsonNodeFactory.instance.arrayNode();
    }

    protected ObjectNode jsonObject() {
        return JsonNodeFactory.instance.objectNode();
    }


    private enum SpecialJsonValue {
        REMOVE
    }

    protected static final SpecialJsonValue JSON_REMOVE = SpecialJsonValue.REMOVE;

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

package org.ld4l.bib2lod.options;

import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.Configuration.Key;

import com.fasterxml.jackson.databind.JsonNode;

public final class JsonUtils {
    
    /**
     * Utility method to return a required string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value if non-null and non-empty
     */
    public static String getRequiredJsonStringValue(JsonNode node, Key key) {
        
        // Seems too much of a mess to try to combine
        // return getJsonStringValue(node, key, true);
        
        String keyString = key.string();
        
        // Key is missing
        if (! node.has(key.string())) {
            throw new RequiredKeyMissingException(key);
        }
        
        // Value is null - "key": null
        if (! node.hasNonNull(keyString)) {
            throw new RequiredValueNullException(key);
        }
        
        JsonNode valueNode = node.get(keyString);
        
        // Value is not a string
        if (! valueNode.isTextual()) {
            throw new InvalidTypeException(key);
        }

        String value = valueNode.textValue();
        
        // Value is empty - "key": ""   
        if (value.equals("")) {
            throw new RequiredValueEmptyException(key);
        }
        
        return value;            
    }
    
    /**
     * Utility method to return an optional string value in a JsonNode. Missing,
     * null, and empty values succeed; only non-empty non-string values throw
     * an error. Generally a defined value is expected to work; this provides
     * an alert that it does not.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     */
    public static String getOptionalJsonStringValue(JsonNode node, Key key) {
        
        // Seems too much of a mess to try to combine
        // return getJsonStringValue(node, key, true);
        
        String keyString = key.string();
        
        String value = null;
        
        // Value is present and non-null - i.e., not "key": null
        if (node.hasNonNull(keyString)) {
            JsonNode valueNode = node.get(keyString);
            // Value is a string
            if (valueNode.isTextual()) {
                value = valueNode.textValue();
            } else {
                throw new InvalidTypeException(key);
            }
        }

        return value;          
    }

}

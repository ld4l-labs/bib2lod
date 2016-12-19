package org.ld4l.bib2lod.configuration;

import org.ld4l.bib2lod.configuration.Configuration.Key;

import com.fasterxml.jackson.databind.JsonNode;

final class JsonUtils {
    
    /**
     * Utility method to return a required string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value if non-null and non-empty
     */
    static String getRequiredJsonStringValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, true);
    }
    
    /**
     * Utility method to return an optional string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     */
    static String getOptionalJsonStringValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, false);
    }

    /**
     * Utility method to return the string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * 
     */
     static String getJsonStringValue(
             JsonNode node, Key key, boolean required) {
        
        String keyString = key.string;
        
        // Key is missing
        if (! node.has(key.string)) {
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
     * Utility method to return a required string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value if non-null and non-empty
     */
    static String getRequiredJsonBooleanValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, true);
    }
     
    /**
     * Utility method to return an optional string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     */
    static String getOptionalJsonBooleanValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, false);
    }

    /**
     * Utility method to return the string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * 
     */
    static String getJsonBooleanValue(
              JsonNode node, Key key, boolean required) {
         
        String keyString = key.string;
         
        // Key is missing
        if (! node.has(key.string)) {
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
     * Utility method to return a required string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value if non-null and non-empty
     */
    static String getRequiredJsonNumericValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, true);
    }
      
   /**
    * Utility method to return an optional string value in a JsonNode.
    * @param node - the enclosing JsonNode
    * @param key - the key in the JsonNode
    * @return stringValue - the string value 
    */
    static String getOptionalJsonNumericValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, false);
    }

    /**
     * Utility method to return the string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * 
     */
     static String getJsonNumericValue(
           JsonNode node, Key key, boolean required) {
      
        String keyString = key.string;
      
        // Key is missing
        if (! node.has(key.string)) {
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
    * Utility method to return a required string value in a JsonNode.
    * @param node - the enclosing JsonNode
    * @param key - the key in the JsonNode
    * @return stringValue - the string value if non-null and non-empty
    */
    static String getRequiredJsonArrayValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, true);
    }
   
    /**
     * Utility method to return an optional string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     */
    static String getOptionalJsonArrayValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, false);
    }

    /**
     * Utility method to return the string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * 
     */
     static String getJsonArrayValue(
            JsonNode node, Key key, boolean required) {
       
        String keyString = key.string;
       
        // Key is missing
        if (! node.has(key.string)) {
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
     * Utility method to return a required string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value if non-null and non-empty
     */
    static String getRequiredJsonObjectValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, true);
    }
    
    /**
     * Utility method to return an optional string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     */
    static String getOptionalJsonObjectValue(JsonNode node, Key key) {
        return getJsonStringValue(node, key, false);
    }

    /**
     * Utility method to return the string value in a JsonNode.
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * 
     */
     static String getJsonObjectValue(
             JsonNode node, Key key, boolean required) {
        
        String keyString = key.string;
        
        // Key is missing
        if (! node.has(key.string)) {
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
     
     
 
}

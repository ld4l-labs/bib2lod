package org.ld4l.bib2lod.configuration.options;

import com.fasterxml.jackson.databind.JsonNode;

public final class JsonUtils {
    
    public static class RequiredKeyMissingException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredKeyMissingException(String key) {
            super("Configuration is missing required key '" + key  + ".'");              
        }
    }
   
    public static class RequiredValueNullException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredValueNullException(String key) {
            super("Value of required configuration key '" + key + " is null.'");                  
        }
    }
    
    public static class InvalidTypeException extends RuntimeException {  
        
        private static final long serialVersionUID = 1L;
        
        protected InvalidTypeException(String key) {
            super("Value of configuration key '" + key + " is of invalid type.'");                
        }
    }
    
    public static class RequiredValueEmptyException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredValueEmptyException(String key) {
            super("Value of required configuration key '" + key + " is empty.'");                   
        }
    }

    
    /**
     * Utility method to return a required string value in a JsonNode. Throws
     * an error if value is null or empty. 
     * @param node - the enclosing JsonNode
     * @param key - the key in the JsonNode
     * @return value - the string value if non-null and non-empty
     * @throw RequiredKeyMissingException 
     * @throw RequiredValueNullException
     * @throw RequiredValueEmptyException
     */
    public static String getRequiredJsonStringValue(JsonNode node, String key) {

        // Key is missing
        if (! node.has(key)) {
            throw new RequiredKeyMissingException(key);
        }
        
        String value = getOptionalJsonStringValue(node, key);
        
        // Key is missing or value is null - i.e., "key": null
        if (value == null) {
            throw new RequiredValueNullException(key);
        }
        
        // Value is empty - "key": ""   
        if (value.equals("")) {
            throw new RequiredValueEmptyException(key);
        }
        
        return value;                
    }
    
    /**
     * Utility method to return a string value in a JsonNode. Missing,
     * null, and empty values succeed; only non-empty non-string values throw
     * an error. Generally a defined value is expected to work; this provides
     * an alert that it does not.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return stringValue - the string value 
     * @throw InvalidTypeException
     */
    public static String getOptionalJsonStringValue(JsonNode node, String key) {

        String value = null;
        
        // Value is present and non-null - i.e., not "key": null
        if (node.hasNonNull(key)) {
            JsonNode valueNode = node.get(key);
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

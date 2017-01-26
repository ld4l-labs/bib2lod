/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

/**
 * Utility methods for handling JSON objects.
 */
public final class JsonUtils {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Signals that a required node is absent in the JSON object.
     */
    public static class RequiredNodeMissingException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredNodeMissingException(String key) {
            super("ConfigurationFromJson is missing required key '" + key  + ".'");              
        }
    }
   
    /**
     * Signals that a required node in the JSON object is null.
     */
    public static class RequiredNodeNullException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredNodeNullException(String key) {
            super("Value of required configuration key '" + key + " is null.'");                  
        }
    }
    
    /**
     * Signals that a required value in the JSON object is of the wrong type.
     */
    public static class InvalidNodeTypeException extends RuntimeException {  
        
        private static final long serialVersionUID = 1L;
        
        protected InvalidNodeTypeException(String key) {
            super("Value of configuration key '" + key + " is of invalid type.'");                
        }
    }
    
    /**
     * Signals that a required value in the JSON object is empty.
     */
    public static class RequiredNodeEmptyException extends RuntimeException {
        
        private static final long serialVersionUID = 1L;
        
        protected RequiredNodeEmptyException(String key) {
            super("Value of required configuration key '" + key + " is empty.'");                   
        }
    }
    

    /*
     * TextNode
     */

    /**
     * Utility method to return an optional TextNode in a JsonNode. Missing,
     * null, and empty string values succeed; non-string values (including
     * empty objects and arrays) throw a RuntimeException. The premise is that  
     * if the user has defined a value it is expected to work, and an alert  
     * should be issued when the value is bad.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the TextNode
     */
    public static TextNode getOptionalTextNode(JsonNode node, String key) {

        JsonNode value = null;
        
        // Value is present and non-null - i.e., not "key": null
        if (node.hasNonNull(key)) {
            value = node.get(key);
            // Value is a string
            if (! value.isTextual()) {
                throw new InvalidNodeTypeException(key);
            } 
        }
        
        return (TextNode) value;          
    }
    
    /**
     * Utility method to return a required text node in a JsonNode. Throws
     * a RuntimeException if value is null, empty, or of an invalid type.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the TextNode 
     */
    public static TextNode getRequiredTextNode(JsonNode node, String key) {

        // Key is missing
        // Or: if (node.path(key).isMissingNode()
        if (! node.has(key)) {
            throw new RequiredNodeMissingException(key);
        }
        
        JsonNode value = getOptionalTextNode(node, key);
        
        // Explicit null - i.e., "key": null
        if (value == null) {
            throw new RequiredNodeNullException(key);
        }
        
        // Value is empty - "key": ""   
        if (value.textValue().equals("")) {
            throw new RequiredNodeEmptyException(key);
        }
        
        return (TextNode) value;                
    }
    
    /**
     * Utility method to return an optional string value in a JsonNode. Missing,
     * null, and empty values succeed; non-string values (including
     * empty container nodes) throw a RuntimeException. The premise is that  
     * if the user has defined a value it is expected to work, and an alert  
     * should be issued when the value is bad.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return the string value of the TextNode
     */
    public static String getOptionalStringValue(JsonNode node, String key) {
        
        TextNode textNode = getOptionalTextNode(node, key);
        if (textNode == null) {
            return null;
        }
        return textNode.textValue();
    }

    /**
     * Utility method to return a required string value in a JsonNode. Throws
     * a RuntimeException if value is null, empty, or of an invalid type.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return the string value of the TextNode
     */
    public static String getRequiredStringValue(JsonNode node, String key) {
        return getRequiredTextNode(node, key).textValue();
    }
    
    /*
     * ArrayNode
     */
        
    /**
     * Utility method to return an optional array node in a JsonNode. Missing,
     * null, and empty arrays succeed; non-string values (including
     * empty container nodes) throw a RuntimeException. The premise is that  
     * if the user has defined a value it is expected to work, and an alert  
     * should be issued when the value is bad.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the ArrayNode
     */
    public static ArrayNode getOptionalArrayNode(
            JsonNode node, String key) {

        JsonNode value = null;
        
        // Value is present and non-null - i.e., not "key": null
        if (node.hasNonNull(key)) {
            value = node.get(key);
            // Value is not an array 
            if (! value.isArray()) {
                throw new InvalidNodeTypeException(key);
            } 
        }

        return (ArrayNode) value;          
    }
    
    /**
     * Utility method to return a required array node in a JsonNode. Throws
     * a RuntimeException if value is null, empty, or of an invalid type.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the ArrayNode 
     */
    public static ArrayNode getRequiredArrayNode(
            JsonNode node, String key) {

        // Key is missing
        // Or: if (node.path(key).isMissingNode()
        if (! node.has(key)) {
            throw new RequiredNodeMissingException(key);
        }
        
        JsonNode value = getOptionalArrayNode(node, key);
        
        // Explicit null value - i.e., "key": null
        if (value == null) {
            throw new RequiredNodeNullException(key);
        }
        
        // Value is empty - "key": []   
        if (value.size() == 0) {
            throw new RequiredNodeEmptyException(key);
        }
        
        return (ArrayNode) value;                
    }
    
    /*
     * ObjectNode
     */
    
    /**
     * Utility method to return an optional object node in a JsonNode. Missing,
     * null, and empty string values succeed; non-string values (including
     * empty objects and arrays) throw a RuntimeException. The premise is that  
     * if the user has defined a value it is expected to work, and an alert  
     * should be issued when the value is bad.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the ObjectNode
     */
    public static ObjectNode getOptionalObjectNode(
            JsonNode node, String key) {
        
        JsonNode value = null;
        
        // Value is present and non-null - i.e., not "key": null
        if (node.hasNonNull(key)) {
            value = node.get(key);
            // Value is not an object
            if (! value.isObject()) {
                throw new InvalidNodeTypeException(key);
            } 
        }

        return (ObjectNode) value;            
    }
    
    /**
     * Utility method to return a required object node in a JsonNode. Throws
     * a RuntimeException if value is null, empty, or of an invalid type.
     * @param node - the JsonNode
     * @param key - the key in the JsonNode
     * @return value - the ObjectNode
     */
    public static ObjectNode getRequiredObjectNode(
            JsonNode node, String key) {

        // Key is missing
        // Or: if (node.path(key).isMissingNode()
        if (! node.has(key)) {
            throw new RequiredNodeMissingException(key);
        }
        
        JsonNode value = getOptionalObjectNode(node, key);
        
        // Explicit null value - i.e., "key": null
        if (value == null) {
            throw new RequiredNodeNullException(key);
        }
        
        // Value is empty - "key": {}  
        if (value.size() == 0) {
            throw new RequiredNodeEmptyException(key);
        }
        
        return null;                
    }


}

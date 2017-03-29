/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;

import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * <pre>
 * If no file, complain.
 * Parse the file into a Jackson ObjectNode. If error, give up
 * Recursively parse -- top is an object
 * 
 * Object: create new node 
 * attributes are either String, Object, or Array
 * 
 * IF name is className, value must be a single string.
 * If String, set as an attribute on the node
 * If Object, recurse and set as a child node.
 * If Array of Srings, set as multiple values of an attribute
 * If Array of Objects, recurse and set as multiple child nodes
 * If mixed array, or array of arrays, booleans, numbers, etc. complain.
 * </pre>
 */
public class JsonConfigurationFileParser implements Configurator {
    private final Configuration configuration;

    public JsonConfigurationFileParser(CommandLineOptions options) {
        this(getJsonInputStream(options.getConfigFile()));
    }

    /** This constructor is particularly useful for unit tests. */
    public JsonConfigurationFileParser(InputStream jsonInput) {
        ObjectNode json = parseInputToJsonObject(jsonInput);
        configuration = new Field("", json, new FieldPath())
                .convertToConfiguration();
    }

    private static InputStream getJsonInputStream(String filePath) {
        try {
            if (filePath == null) {
                throw new ConfigurationException(
                        "No configuration file specified.");
            }

            File file = new File(filePath).getAbsoluteFile();
            if (!file.isFile()) {
                throw new ConfigurationException(
                        "Configuration file does not exist: " + file);
            }
            if (!file.canRead()) {
                throw new ConfigurationException(
                        "No permission to read configuration file: " + file);
            }

            return new FileInputStream(file);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Failed to open the configuration file: " + filePath, e);
        }
    }

    private ObjectNode parseInputToJsonObject(InputStream jsonInput) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(jsonInput);
            if (json.isObject()) {
                return (ObjectNode) json;
            } else {
                throw new ConfigurationException("The configuration file "
                        + "must contain a single JSON object.");
            }
        } catch (JsonProcessingException e) {
            throw new ConfigurationException(
                    "The configuration file contains invalid JSON.");
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Failed to read the configuration file.", e);
        } finally {
            try {
                jsonInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Configuration getTopLevelConfiguration() {
        return this.configuration;
    }

    public static class ParsingException extends ConfigurationException {
        public ParsingException(FieldPath path, String message) {
            super("Problem parsing JSON at " + path + " -- " + message);
        }
    }

    /**
     * A list of names, very helpful in Exception messages. Immutable so it
     * doesn't get corrupted during the recursion.
     */
    protected static class FieldPath {
        private final String[] names;

        public FieldPath(String... names) {
            this.names = names;
        }

        public FieldPath add(String name) {
            int newLength = names.length + 1;
            String[] newNames = Arrays.copyOf(names, newLength);
            newNames[newLength - 1] = name;
            return new FieldPath(newNames);
        }

        @Override
        public String toString() {
            return Arrays.asList(names).toString();
        }
    }

    /**
     * How to distinguish types of fields, and how to handle them.
     */
    protected static class Field {
        private static final Object CLASS_NODE_NAME = "class";

        protected final String name;
        protected final JsonNode node;
        protected final FieldPath path;

        Field(String name, JsonNode node, FieldPath path) {
            this.name = name;
            this.node = node;
            this.path = path;
        }

        Configuration convertToConfiguration() {
            Builder builder = new Builder();
            for (Iterator<String> fieldNames = node.fieldNames(); fieldNames
                    .hasNext();) {
                String fieldName = fieldNames.next();
                JsonNode field = node.get(fieldName);
                new Field(fieldName, field, path.add(fieldName)).apply(builder);
            }
            return builder.build();
        }

        void apply(Builder builder) {
            if (isClassName()) {
                applyClassName(builder);
            } else if (isAttribute()) {
                applyAttribute(builder);
            } else if (isChild()) {
                applyChildNode(builder);
            } else if (isArrayNode()) {
                applyArray(builder);
            } else {
                throw new ParsingException(path, "Not a valid node type.");
            }
        }

        private void applyArray(Builder builder) {
            if (isEmpty()) {
                throw new ParsingException(path,
                        "Array nodes may not be empty.");
            } else if (isStringArray()) {
                if (isClassNameArray()) {
                    throw new ParsingException(path,
                            "You may supply only one value for class name.");
                }
                applyStringArray(builder);
            } else if (isChildArray()) {
                applyChildArray(builder);
            } else {
                throw new ParsingException(path, "Arrays must contain "
                        + "attributes or child nodes; not both.");
            }
        }

        private boolean isClassName() {
            return node.isTextual() && name.equals(CLASS_NODE_NAME);
        }

        private void applyClassName(Builder builder) {
            builder.setClassName(node.textValue());
        }

        private boolean isAttribute() {
            return node.isTextual();
        }

        private void applyAttribute(Builder builder) {
            builder.addAttribute(name, node.textValue());
        }

        private boolean isChild() {
            return node.isObject();
        }

        private void applyChildNode(Builder builder) {
            builder.addChild(name,
                    new Field(name, node, path).convertToConfiguration());
        }

        private boolean isArrayNode() {
            return node.isArray();
        }

        private boolean isEmpty() {
            return !node.elements().hasNext();
        }

        private boolean isStringArray() {
            for (JsonNode element : node) {
                if (!element.isTextual()) {
                    return false;
                }
            }
            return true;
        }

        private boolean isClassNameArray() {
            return name.equals(CLASS_NODE_NAME);
        }

        private void applyStringArray(Builder builder) {
            for (JsonNode element : node) {
                builder.addAttribute(name, element.textValue());
            }
        }

        private boolean isChildArray() {
            for (JsonNode element : node) {
                if (!element.isObject()) {
                    return false;
                }
            }
            return true;
        }

        private void applyChildArray(Builder builder) {
            for (JsonNode element : node) {
                builder.addChild(name, new Field(name, element, path)
                        .convertToConfiguration());
            }
        }

    }
}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.jena.ext.com.google.common.base.Objects;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * An immutable tree of Configuration nodes.
 */
public class ConfigurationNode implements Configuration {
    private final MapOfLists<String, String> attributesMap;
    private final MapOfLists<String, Configuration> childrenMap;
    private final String className;

    public ConfigurationNode(MapOfLists<String, String> attributesMap,
            MapOfLists<String, Configuration> childrenMap, String className) {
        this.attributesMap = attributesMap;
        this.childrenMap = childrenMap;
        this.className = className;
    }

    @Override
    public Set<String> getAttributeKeys() {
        return attributesMap.keys();
    }

    @Override
    public String getAttribute(String key) {
        return attributesMap.getValue(key);
    }

    @Override
    public List<String> getAttributes(String key) {
        return Collections.unmodifiableList(attributesMap.getValues(key));
    }

    @Override
    public MapOfLists<String, String> getAttributesMap() {
        return attributesMap.duplicate();
    }

    @Override
    public Set<String> getChildNodeKeys() {
        return childrenMap.keys();
    }

    @Override
    public Configuration getChildNode(String key) {
        return childrenMap.getValue(key);
    }

    @Override
    public List<Configuration> getChildNodes(String key) {
        return Collections.unmodifiableList(childrenMap.getValues(key));
    }

    @Override
    public MapOfLists<String, Configuration> getChildNodesMap() {
        return childrenMap.duplicate();
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attributesMap, childrenMap);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        } else {
            ConfigurationNode that = (ConfigurationNode) obj;
            return this.attributesMap.equals(that.attributesMap)
                    && this.childrenMap.equals(that.childrenMap);
        }
    }

    @Override
    public String toString() {
        return "ConfigurationNode[className=" + className + ", attributesMap="
                + attributesMap + ", childrenMap=" + childrenMap + "]";
    }

    /**
     * The way to build an immutable node.
     *
     * <pre>
     * Configuration config = new ConfigurationNode.Builder()
     *                                .attribute("this", "that")
     *                                .attribute("some", "other")
     *                                .child("son", configNode)
     *                                .setClassName("my.excellent.Class")
     *                                .build();
     * </pre>
     */
    public static class Builder {
        private final MapOfLists<String, String> attributesMap;
        private final MapOfLists<String, Configuration> childrenMap;
        private String className;

        public Builder() {
            attributesMap = new MapOfLists<>();
            childrenMap = new MapOfLists<>();
        }

        public Builder(Configuration c) {
            className = c.getClassName();
            attributesMap = c.getAttributesMap();
            childrenMap = c.getChildNodesMap();
        }

        public Builder(String clazz, MapOfLists<String, String> attributes,
                MapOfLists<String, Configuration> children) {
            this.className = clazz;
            attributesMap = attributes;
            childrenMap = children;
        }

        public Builder addAttribute(String key, String value) {
            attributesMap.addValue(key, value);
            return this;
        }

        public Builder removeAttributes(String key) {
            attributesMap.removeValues(key);
            return this;
        }

        public Builder addChild(String key, Configuration value) {
            childrenMap.addValue(key, value);
            return this;
        }

        public Builder removeChildNodes(String key) {
            childrenMap.removeValues(key);
            return this;
        }

        public Builder setClassName(String name) {
            className = name;
            return this;
        }

        public ConfigurationNode build() {
            return new ConfigurationNode(attributesMap, childrenMap, className);
        }

    }

}

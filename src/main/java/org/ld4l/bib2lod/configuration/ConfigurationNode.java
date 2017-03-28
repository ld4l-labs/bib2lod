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

    public ConfigurationNode(MapOfLists<String, String> attributesMap,
            MapOfLists<String, Configuration> childrenMap) {
        this.attributesMap = attributesMap;
        this.childrenMap = childrenMap;
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

    /**
     * The way to build an immutable node.
     *
     * <pre>
     * Configuration config = new ConfigurationNode.Builder()
     *                                .attribute("this", "that")
     *                                .attribute("some", "other")
     *                                .child("son", configNode)
     *                                .build();
     * </pre>
     */
    public static class Builder {
        private final MapOfLists<String, String> attributesMap = new MapOfLists<>();
        private final MapOfLists<String, Configuration> childrenMap = new MapOfLists<>();

        public Builder() {
            // Nothing to do
        }

        public Builder(Configuration c) {
            for (String key : c.getAttributeKeys()) {
                attributesMap.getValues(key).addAll(c.getAttributes(key));
            }
            for (String key : c.getChildNodeKeys()) {
                childrenMap.getValues(key).addAll(c.getChildNodes(key));
            }
        }

        public Builder addAttribute(String key, String value) {
            attributesMap.addValue(key, value);
            return this;
        }

        public Builder removeAttributes(String key) {
            attributesMap.getValues(key).clear();
            return this;
        }

        public Builder addChild(String key, Configuration value) {
            childrenMap.addValue(key, value);
            return this;
        }

        public Builder removeChildNodes(String key) {
            childrenMap.getValues(key).clear();
            return this;
        }

        public ConfigurationNode build() {
            return new ConfigurationNode(attributesMap, childrenMap);
        }

    }

}

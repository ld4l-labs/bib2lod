/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.AttributeOverride;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.ConfigurationFieldPath;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * Accept and apply the list of overrides.
 * 
 * If an override has a non-null value, add that value to the specified node and
 * key. If the specified node does not exist, create it and any parents as
 * necessary.
 * 
 * If an override has a null value, remove all values from the specified key of
 * the specified node. If the specified node does not exist, just ignore this.
 */
public class ConfigurationOverrider {
    private final List<AttributeOverride> overrides;

    public ConfigurationOverrider(ConfigurationOptions options) {
        overrides = options.getOverrides();
    }

    public Configuration override(Configuration config) {
        Configuration newConfig = config;
        for (AttributeOverride override : overrides) {
            if (override.value == null) {
                newConfig = doRemoval(newConfig, override.keys);
            } else {
                newConfig = doAddition(newConfig, override.keys,
                        override.value);
            }
        }
        return newConfig;
    }

    private Configuration doRemoval(Configuration c,
            ConfigurationFieldPath keys) {
        int pathLength = keys.names.length;
        if (pathLength == 0) {
            return c;
        }

        String key = keys.names[0];
        if (pathLength == 1) {
            return new Builder(c).removeAttributes(key).build();
        } else if (!c.getChildNodeKeys().contains(key)) {
            return c;
        } else {
            MapOfLists<String, Configuration> newChildren = c
                    .getChildNodesMap();
            newChildren.removeValues(key);
            for (Configuration child : c.getChildNodes(key)) {
                newChildren.addValue(key, doRemoval(child, keys.pop()));
            }
            return new Builder(c.getClassName(), c.getAttributesMap(),
                    newChildren).build();
        }
    }

    private Configuration doAddition(Configuration c,
            ConfigurationFieldPath keys, String value) {
        int pathLength = keys.names.length;
        if (pathLength == 0 || value == null) {
            return c;
        }

        String key = keys.names[0];
        if (pathLength == 1) {
            return new Builder(c).addAttribute(key, value).build();
        } else if (!c.getChildNodeKeys().contains(key)) {
            Configuration newChild = doAddition(newNode(), keys.pop(), value);
            return new Builder(c).addChild(key, newChild).build();
        } else {
            MapOfLists<String, Configuration> newChildren = c
                    .getChildNodesMap();
            newChildren.removeValues(key);
            for (Configuration child : c.getChildNodes(key)) {
                newChildren.addValue(key, doAddition(child, keys.pop(), value));
            }
            return new Builder(c.getClassName(), c.getAttributesMap(),
                    newChildren).build();
        }
    }

    private ConfigurationNode newNode() {
        return new Builder().build();
    }

}

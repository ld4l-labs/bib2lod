/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;
import java.util.Set;

/**
 * A piece of the configuration tree.
 * 
 * Each node may have attributes, identified by keys. A key may be associated
 * with more than one attribute value.
 *
 * Each node may have children, identified by keys. A key may be associated with
 * more than one child node.
 */
public interface Configuration {
    /**
     * An empty instance of the configuration.
     */
    static Configuration EMPTY_CONFIGURATION = new ConfigurationNode.Builder()
            .build();

    /**
     * Get the set of keys for attributes, or an empty Set. Never null.
     */
    Set<String> getAttributeKeys();

    /**
     * Get the first value associated with this key, or null.
     */
    String getAttribute(String key);

    /**
     * Get all values associated with this key, or an empty list. Never null.
     */
    List<String> getAttributes(String key);

    /**
     * Get the set of keys for child nodes, or an empty Set. Never null.
     */
    Set<String> getChildNodeKeys();

    /**
     * Get the first child node associated with this key, or null.
     */
    Configuration getChildNode(String key);

    /**
     * Get all child nodes associated with this key, or an empty list. Never
     * null.
     */
    List<Configuration> getChildNodes(String key);

    /**
     * Indicates a problem when applying the onfiguration.
     */
    public static class ConfigurationException extends RuntimeException {

        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }

        public ConfigurationException(String message) {
            super(message);
        }

        public ConfigurationException(Throwable cause) {
            super(cause);
        }
    }
}

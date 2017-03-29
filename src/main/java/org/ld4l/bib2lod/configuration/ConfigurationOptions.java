/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Where is the config file? What attributes in the config file are to be
 * overridden?
 */
public interface ConfigurationOptions {
    /**
     * A list of names, very helpful in Exception messages. Immutable so it
     * doesn't get corrupted during the recursion.
     */
    public static class ConfigurationFieldPath {
        private final String[] names;

        public ConfigurationFieldPath(String... names) {
            this.names = names;
        }

        public ConfigurationFieldPath add(String name) {
            int newLength = names.length + 1;
            String[] newNames = Arrays.copyOf(names, newLength);
            newNames[newLength - 1] = name;
            return new ConfigurationFieldPath(newNames);
        }

        @Override
        public String toString() {
            return Arrays.asList(names).toString();
        }
    }

    public static class AttributeOverride {
        final ConfigurationFieldPath keys;
        final String value;

        public AttributeOverride(String... keys) {
            this.keys = new ConfigurationFieldPath(keys);
            this.value = null;
        }

        public AttributeOverride(String value, String... keys) {
            this.keys = new ConfigurationFieldPath(keys);
            this.value = value;
        }
    }

    public String getConfigFile();

    public List<AttributeOverride> getOverrides();

}

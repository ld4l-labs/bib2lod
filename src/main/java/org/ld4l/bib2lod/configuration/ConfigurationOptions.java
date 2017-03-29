/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        public final String[] names;

        public ConfigurationFieldPath(String... names) {
            this.names = names;
        }

        public ConfigurationFieldPath add(String name) {
            int newLength = names.length + 1;
            String[] newNames = Arrays.copyOf(names, newLength);
            newNames[newLength - 1] = name;
            return new ConfigurationFieldPath(newNames);
        }

        public ConfigurationFieldPath pop() {
            return new ConfigurationFieldPath(
                    Arrays.copyOfRange(names, 1, names.length));
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(names);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } else if (obj == null) {
                return false;
            } else if (getClass() != obj.getClass()) {
                return false;
            }
            ConfigurationFieldPath that = (ConfigurationFieldPath) obj;
            return Arrays.equals(names, that.names);
        }

        @Override
        public String toString() {
            return Arrays.asList(names).toString();
        }
    }

    public static class AttributeOverride {
        final ConfigurationFieldPath keys;
        final String value;

        public AttributeOverride(String value, String... keys) {
            this.keys = new ConfigurationFieldPath(keys);
            this.value = value;
        }
    }

    public String getConfigFile();

    public List<AttributeOverride> getOverrides();

}

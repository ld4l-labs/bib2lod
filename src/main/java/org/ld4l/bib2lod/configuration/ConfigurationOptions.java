/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

/**
 * Where is the config file? What attributes in the config file are to be
 * overridden?
 */
public interface ConfigurationOptions {
    public static class AttributeOverride {
        final List<String> keys;
        final String value;

        public AttributeOverride(List<String> keys) {
            this.keys = keys;
            this.value = null;
        }

        public AttributeOverride(List<String> keys, String value) {
            this.keys = keys;
            this.value = value;
        }
    }

    public String getConfigFile();

    public List<AttributeOverride> getOverrides();

}

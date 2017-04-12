/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.configuration.ConfigurationOptions.AttributeOverride;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 */
public class ConfigurationOverriderTest extends AbstractTestClass {
    private static final String VALUE = "value";
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";

    private ConfigurationOptions options;
    private Configuration original;
    private Configuration expected;

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void addition_pathIsEmpty_noChange() {
        options = options(override(VALUE));
        original = new Builder().build();
        expected = new Builder().build();
        runIt();
    }

    @Test
    public void addition_pathIsSingleton_performs() {
        options = options(override(VALUE, KEY1));
        original = new Builder().build();
        expected = new Builder().addAttribute(KEY1, VALUE).build();
        runIt();
    }

    @Test
    public void addition_nodeDoesntExist_createsTheNode() {
        options = options(override(VALUE, KEY1, KEY2));
        original = new Builder().build();
        expected = new Builder()
                .addChild(KEY1, new Builder().addAttribute(KEY2, VALUE).build())
                .build();
        runIt();
    }

    @Test
    public void addition_appliesToAllChildren() {
        options = options(override(VALUE, KEY1, KEY2));
        original = new Builder() //
                .addChild(KEY1, new Builder().build())
                .addChild(KEY1, new Builder().build()).build();
        expected = new Builder()
                .addChild(KEY1, new Builder().addAttribute(KEY2, VALUE).build())
                .addChild(KEY1, new Builder().addAttribute(KEY2, VALUE).build())
                .build();
        runIt();
    }

    @Test
    public void removal_pathIsEmpty_noChange() {
        options = options(override(null));
        original = new Builder().build();
        expected = new Builder().build();
        runIt();
    }

    @Test
    public void removal_pathIsSingleton_performs() {
        options = options(override(null, KEY1));
        original = new Builder().addAttribute(KEY1, VALUE).build();
        expected = new Builder().build();
        runIt();
    }

    @Test
    public void removal_nodeDoesntExist_noChange() {
        options = options(override(null, KEY1, KEY2));
        original = new Builder().build();
        expected = new Builder().build();
        runIt();
    }

    @Test
    public void removal_appliesToAllChildren() {
        options = options(override(null, KEY1, KEY2));
        original = new Builder()
                .addChild(KEY1, new Builder().addAttribute(KEY2, VALUE).build())
                .addChild(KEY1, new Builder().addAttribute(KEY2, VALUE).build())
                .build();
        expected = new Builder() //
                .addChild(KEY1, new Builder().build())
                .addChild(KEY1, new Builder().build()).build();
        runIt();
    }

    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private ConfigurationOptions options(AttributeOverride... overrides) {
        return new ConfigurationOptions() {
            @Override
            public String getConfigFile() {
                return null;
            }

            @Override
            public List<AttributeOverride> getOverrides() {
                return Arrays.asList(overrides);
            }
        };
    }

    private AttributeOverride override(String value, String... keys) {
        return new AttributeOverride(value, keys);
    }

    private void runIt() {
        assertEquals(expected,
                new ConfigurationOverrider(options).override(original));
    }

}

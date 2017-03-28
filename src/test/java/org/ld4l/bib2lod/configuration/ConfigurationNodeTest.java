/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;

/**
 * Test the functionality for attributes. The functionality for child nodes is
 * almost totally the same code.
 */
public class ConfigurationNodeTest {
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private ConfigurationNode config;
    private ConfigurationNode config2;
    private ConfigurationNode child1;

    // ----------------------------------------------------------------------
    // The tests
    // ----------------------------------------------------------------------

    @Test
    public void getValueFromNone_returnsNull() {
        config = new Builder().build();
        assertNull(config.getAttribute(KEY1));
    }

    @Test
    public void getValueFromOne_returnsValue() {
        config = new Builder().addAttribute(KEY1, VALUE1).build();
        assertEquals(VALUE1, config.getAttribute(KEY1));
    }

    @Test
    public void getValueFromTwo_returnsFirst() {
        config = new Builder().addAttribute(KEY1, VALUE1)
                .addAttribute(KEY1, VALUE2).build();
        assertEquals(VALUE1, config.getAttribute(KEY1));
    }

    @Test
    public void getValuesFromNone_returnsEmptyList() {
        config = new Builder().build();
        assertEquals(new ArrayList<>(), config.getAttributes(KEY1));
    }

    @Test
    public void getValuesFromTwo_returnsValuesInOrder() {
        config = new Builder().addAttribute(KEY1, VALUE1)
                .addAttribute(KEY1, VALUE2).build();
        assertEquals(Arrays.asList(VALUE1, VALUE2), config.getAttributes(KEY1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tryToAddValue_throwsException() {
        config = new Builder().addAttribute(KEY1, VALUE1).build();
        config.getAttributes(KEY1).remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tryToRemoveValue_throwsException() {
        config = new Builder().addAttribute(KEY1, VALUE1).build();
        config.getAttributes(KEY1).add(VALUE2);
    }

    @Test
    public void copyConstructorWorks() {
        config = new Builder().addAttribute(KEY1, VALUE1)
                .addAttribute(KEY2, VALUE2).addChild(KEY1, child1).build();
        assertEquals(config, new Builder(config).build());
    }

    @Test
    public void removeAttributesWorks() {
        config = new Builder().addAttribute(KEY1, VALUE1).removeAttributes(KEY1)
                .build();
        assertEquals(config, new Builder(config).build());
    }

}

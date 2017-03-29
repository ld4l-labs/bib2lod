/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * Test the functionality for attributes. The functionality for child nodes is
 * almost totally the same code.
 * 
 * Test the functionality for className.
 */
public class ConfigurationNodeTest {
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private static final String CLASSNAME1 = "classname1";
    private static final String CLASSNAME2 = "classname2";
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
        child1 = new Builder().setClassName("child1Class").build();
        config = new Builder().setClassName("name").addAttribute(KEY1, VALUE1)
                .addAttribute(KEY2, VALUE2).addChild(KEY1, child1).build();
        assertEquals(config, new Builder(config).build());
    }

    @Test
    public void shortcutConstructorWorks() {
        MapOfLists<String, String> attributes = new MapOfLists<>();
        attributes.addValue(KEY1, VALUE1);

        MapOfLists<String, Configuration> children = new MapOfLists<>();
        child1 = new Builder().setClassName("child1Class").build();
        children.addValue(KEY1, child1);

        config = new Builder("name", attributes, children).build();

        assertEquals("name", config.getClassName());
        assertEquals(attributes, config.getAttributesMap());
        assertEquals(children, config.getChildNodesMap());
    }

    @Test
    public void removeAttributesWorks() {
        config = new Builder().addAttribute(KEY1, VALUE1).removeAttributes(KEY1)
                .build();
        assertEquals(config, new Builder(config).build());
    }

    @Test
    public void getClassNameFromNone_returnsNull() {
        config = new Builder().build();
        assertNull(config.getClassName());
    }

    @Test
    public void getClassNameFromOne_returnsValue() {
        config = new Builder().setClassName(CLASSNAME1).build();
        assertEquals(CLASSNAME1, config.getClassName());
    }

    @Test
    public void getClassNameFromTwo_returnsLast() {
        config = new Builder().setClassName(CLASSNAME1).setClassName(CLASSNAME2)
                .build();
        assertEquals(CLASSNAME2, config.getClassName());
    }

}

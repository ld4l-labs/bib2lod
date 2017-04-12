/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * TODO
 */
public class AttributeCascaderTest extends AbstractTestClass {

    private static final String KEY = "key";
    private static final String VALUE1 = "value1";
    private static final String VALUE2 = "value2";
    private static final String CLASS1 = "class1";
    private static final String CLASS2 = "class2";
    private static final String CLASS3A = "class3a";
    private static final String CLASS3B = "class3b";

    /**
     * Value1 cascades from level 0 to level 1.
     * 
     * Value2 cascades from level 2 to both nodes in level 3.
     */
    @Test
    public void omnibusSuccess() {
        Configuration original = new Builder().addAttribute(KEY, VALUE1)
                .addChild(KEY, new Builder().setClassName(CLASS1)
                        .addChild(KEY, new Builder().setClassName(CLASS2)
                                .addAttribute(KEY, VALUE2)
                                .addChild(KEY,
                                        new Builder().setClassName(CLASS3A)
                                                .build())
                                .addChild(KEY,
                                        new Builder().setClassName(CLASS3B)
                                                .build())
                                .build())
                        .build())
                .build();

        Configuration expected = new Builder().addAttribute(KEY, VALUE1)
                .addChild(KEY, new Builder().setClassName(CLASS1)
                        .addAttribute(KEY, VALUE1).addChild(KEY, new Builder()
                                .setClassName(CLASS2).addAttribute(KEY, VALUE2)
                                .addChild(KEY,
                                        new Builder().setClassName(CLASS3A)
                                                .addAttribute(KEY, VALUE2)
                                                .build())
                                .addChild(KEY,
                                        new Builder().setClassName(CLASS3B)
                                                .addAttribute(KEY, VALUE2)
                                                .build())
                                .build())
                        .build())
                .build();

        assertEquals(expected, new AttributeCascader().cascade(original));
    }

}

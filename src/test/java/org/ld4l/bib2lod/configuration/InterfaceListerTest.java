/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * Should be able to find the interface on the class, the interface on the
 * superclass, and the super-interface of the interface.
 */
public class InterfaceListerTest extends AbstractTestClass {
    @Test
    public void omnibusSuccess() {
        Set<Class<?>> actual = new InterfaceLister(Class1.class)
                .allInterfaces();
        Set<Class<?>> expected = new HashSet<>(Arrays.asList(Interface1.class,
                Interface2.class, Interface3.class));
        assertEquals(expected, actual);
    }

    private static interface Interface1 {
        // Nothing
    }

    private static interface Interface2 extends Interface3 {
        // Nothing
    }

    private static interface Interface3 {
        // Nothing
    }

    private static class Class1 extends Class2 implements Interface1 {
        // Nothing
    }

    private static class Class2 implements Interface2 {
        // Nothing
    }
}

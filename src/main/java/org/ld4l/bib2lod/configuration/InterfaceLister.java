/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Find out what interfaces are implemented by a given class, directly or
 * indirectly.
 */
public class InterfaceLister {
    private static final Logger LOGGER = LogManager.getLogger();

    private final Class<?> instanceClass;
    private final Set<Class<?>> interfaces = new HashSet<>();

    public InterfaceLister(Class<?> instanceClass) {
        this.instanceClass = instanceClass;
        explore(instanceClass);
    }

    private void explore(Class<?> clazz) {
        if (!clazz.isInterface()) {
            Class<?> s = clazz.getSuperclass();
            if (s != null) {
                explore(s);
            }
        }
        for (Class<?> s : getInterfaces(clazz)) {
            if (interfaces.add(s)) {
                explore(s);
            }
        }
    }

    private List<Class<?>> getInterfaces(Class<?> clazz) {
        Class<?>[] ifaces = clazz.getInterfaces();
        if (ifaces == null) {
            return Collections.emptyList();
        } else {
            return Arrays.asList(ifaces);
        }
    }

    public List<Class<?>> getBySimpleName(String nodeName) {
        List<Class<?>> list = new ArrayList<>();
        for (Class<?> i : interfaces) {
            if (i.getSimpleName().equals(nodeName)) {
                list.add(i);
            }
        }
        return list;
    }

    public Set<Class<?>> allInterfaces() {
        return new HashSet<>(interfaces);
    }

}

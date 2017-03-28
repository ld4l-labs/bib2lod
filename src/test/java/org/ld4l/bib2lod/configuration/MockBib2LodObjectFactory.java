/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * TODO
 */
public class MockBib2LodObjectFactory extends Bib2LodObjectFactory {
    // ----------------------------------------------------------------------
    // Stub infrastructure
    // ----------------------------------------------------------------------

    MapOfLists<Class<? extends Configurable>, Configurable> instances = new MapOfLists<>();
    
    public <T extends Configurable> void addInstance(Class<T> interfaze, T instance) {
        addInstance(interfaze, instance, Configuration.EMPTY_CONFIGURATION);
    }
    
    public <T extends Configurable> void addInstance(Class<T> interfaze, T instance, Configuration config) {
        instance.configure(config);
        instances.addValue(interfaze, instance);
    }
    
    // ----------------------------------------------------------------------
    // Stub methods
    // ----------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Configurable> T instanceForClass(Class<T> class1) {
        return (T) instances.getValue(class1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends Configurable> List<T> instancesForClass(Class<T> class1) {
        return (List<T>) instances.getValues(class1);
    }

    // ----------------------------------------------------------------------
    // Un-implemented methods
    // ----------------------------------------------------------------------

}

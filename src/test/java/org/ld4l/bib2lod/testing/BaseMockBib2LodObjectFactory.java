package org.ld4l.bib2lod.testing;

import java.lang.reflect.Field;
import java.util.List;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * A base mock implementation that test classes can use or extend.
 */
public class BaseMockBib2LodObjectFactory extends Bib2LodObjectFactory {

    MapOfLists<Class<?>, Object> instances = new MapOfLists<>();
    
    public BaseMockBib2LodObjectFactory() throws Exception {
        Field field = Bib2LodObjectFactory.class.getDeclaredField("instance");
        field.setAccessible(true);
        field = null;
        Bib2LodObjectFactory.setFactoryInstance(this); 
    }
    
    public <T> void addInstance(Class<T> interfaze, T instance) {
        addInstance(interfaze, instance, Configuration.EMPTY_CONFIGURATION);
    }
    
    public <T> void addInstance(Class<T> interfaze, T instance, Configuration config) {
        if (instance instanceof Configurable) {
            ((Configurable) instance).configure(config);
        }
        instances.addValue(interfaze, instance);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T> T instanceForInterface(Class<T> class1) {
        return (T) instances.getValue(class1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> instancesForInterface(Class<T> class1) {
        return (List<T>) instances.getValues(class1);
    }
    
    public void unsetInstances() {
        instances = new MapOfLists<>();
    }       

}

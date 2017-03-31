/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.SimpleEntity;
import org.ld4l.bib2lod.ontology.Type;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * Create instances of various classes, as directed by the configuration.
 * 
 * Pass configuration objects to these instance if appropriate.
 * 
 * Serve these instances on request.
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {
    private static final Logger LOGGER = LogManager.getLogger();

    private MapOfLists<Class<?>, Object> configuredInstances = new MapOfLists<>();

    /**
     * Create the instances, configure them, and store them for later.
     * 
     * @param configuration
     *            Must be fully processed: command-line overrides have been
     *            applied and attributes have cascaded.
     */
    public DefaultBib2LodObjectFactory(Configuration configuration) {
        checkThatTopNodeIsValid(configuration);
        populateTheMap(configuration, "TOP_NODE");
    }

    private void checkThatTopNodeIsValid(Configuration configuration) {
        Objects.requireNonNull(configuration,
                "You must provide a Configuration object.");
        if (configuration.getClassName() != null) {
            throw new ConfigurationException(
                    "The top configuration node is not permitted to have a class name.");
        }
    }

    /**
     * Recursively go through the configuration tree, creating instances and
     * storing them in the map.
     */
    private void populateTheMap(Configuration configNode, String nodeName) {
        createAndConfigure(configNode, nodeName);

        for (String key : configNode.getChildNodeKeys()) {
            for (Configuration childNode : configNode.getChildNodes(key)) {
                populateTheMap(childNode, key);
            }
        }
    }

    private void createAndConfigure(Configuration configNode, String nodeName) {
        String className = configNode.getClassName();
        if (className == null) {
            return;
        }

        try {
            Class<?> instanceClass = Class.forName(className);
            Class<?> interfaceClass = inferInterfaceClass(instanceClass,
                    nodeName);
            Object instance = instanceClass.newInstance();
            if (instance instanceof Configurable) {
                ((Configurable) instance).configure(configNode);
            }
            configuredInstances.addValue(interfaceClass, instance);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new ConfigurationException(
                    "Failed to create an instance of " + className, e);
        }
    }

    /**
     * Does the instance class implement an interface whose simple name is the
     * same as the node name?
     */
    private Class<?> inferInterfaceClass(Class<?> instanceClass,
            String nodeName) {
        List<Class<?>> candidates = new InterfaceLister(instanceClass)
                .getBySimpleName(nodeName);
        if (candidates.isEmpty()) {
            throw new ConfigurationException("Class '" + instanceClass.getName()
                    + "' doesn't implement any interfaces named '" + nodeName
                    + "'.");
        } else if (candidates.size() > 1) {
            throw new ConfigurationException("Class '" + instanceClass.getName()
                    + "' implements more than one interface named '" + nodeName
                    + "': " + candidates);
        } else {
            return candidates.get(0);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T instanceForInterface(Class<T> interfaze) {
        T instance = (T) configuredInstances.getValue(interfaze);
        if (instance != null) {
            return instance;
        } else {
            throw new ConfigurationException(
                    "The config file describes no instances for '" + interfaze
                            + "'");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> instancesForInterface(Class<T> interfaze) {
        List<T> instances = (List<T>) configuredInstances.getValues(interfaze);
        if (!instances.isEmpty()) {
            return instances;
        } else {
            throw new ConfigurationException(
                    "The config file describes no instances for '" + interfaze
                            + "'");
        }
    }

    // ----------------------------------------------------------------------
    // Vestigial methods
    // ----------------------------------------------------------------------

    @Override
    public Entity createEntity(Type type) {
        return new SimpleEntity(type);
    }

//    @Override
//    public Entity createEntity(Resource ontClass) {
//        // TODO Auto-generated method stub
//        throw new RuntimeException("Bib2LodObjectFactory.createEntity() not implemented.");
//    }

    @Override
    public Entity createEntity(Entity entity) {
      return new SimpleEntity(entity);
    }

//    @Override
//    public Type createType(Resource ontClass) {
//        return new SimpleType(ontClass);
//    }
//
//    @Override
//    public Link createLink(Property property) {
//        return new SimpleLink(property);
//    }
//
//    @Override
//    public Link createLink(OntologyProperty ontProperty) {
//        return new SimpleLink(ontProperty);
//    }
//
}

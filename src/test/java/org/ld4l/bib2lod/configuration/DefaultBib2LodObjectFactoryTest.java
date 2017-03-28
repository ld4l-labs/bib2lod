/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.ld4l.bib2lod.configuration.Configuration.ConfigurationException;
import org.ld4l.bib2lod.configuration.ConfigurationNode.Builder;
import org.ld4l.bib2lod.testing.AbstractTestClass;

/**
 * TODO
 */
public class DefaultBib2LodObjectFactoryTest extends AbstractTestClass {

    private DefaultBib2LodObjectFactory factory;
    private Configuration config;
    private Object instance;
    private List<? extends Object> instances;

    // ----------------------------------------------------------------------
    // the tests
    // ----------------------------------------------------------------------

    @Test
    public void nullConfiguration_throwsException() {
        expectException(NullPointerException.class, "You must");

        factory = new DefaultBib2LodObjectFactory(null);
    }

    @Test
    public void topNodeHasClassName_throwsException() {
        expectException(ConfigurationException.class,
                "not permitted to have a class name");

        config = new Builder().setClassName("bogus").build();
        factory = new DefaultBib2LodObjectFactory(config);
    }

    @Test
    public void failToInstantiate_throwsException() {
        expectException(ConfigurationException.class,
                "Failed to create an instance");

        config = new Builder().addChild("MyInterface",
                new Builder().setClassName("bogus").build()).build();
        factory = new DefaultBib2LodObjectFactory(config);
    }

    @Test
    public void classImplementsNoInterfaces_throwsException() {
        expectException(ConfigurationException.class,
                "doesn't implement any interfaces");

        config = new Builder().addChild("MultipleInterface", new Builder()
                .setClassName(NoInterfacesClass.class.getName()).build())
                .build();
        factory = new DefaultBib2LodObjectFactory(config);
        fail("implementsNoInterfaces_throwsException not implemented");
    }

    @Test
    public void classImplementsMultipleInterfaces_throwsException() {
        expectException(ConfigurationException.class,
                "more than one interface");

        config = new Builder().addChild("MultipleInterface", new Builder()
                .setClassName(MultipleInterfaceClass.class.getName()).build())
                .build();
        factory = new DefaultBib2LodObjectFactory(config);
    }

    @Test
    public void zeroInstancesPerInterfaceWorks() {
        config = new Builder().build();
        factory = new DefaultBib2LodObjectFactory(config);
        instance = factory.instanceForInterface(AnInterface.class);
        instances = factory.instancesForInterface(AnInterface.class);
        assertNull(instance);
        assertEquals(0, instances.size());
    }

    @Test
    public void oneInstancePerInterfaceWorks() {
        config = new Builder().addChild("AnInterface", new Builder()
                .setClassName(AnInterfaceClass1.class.getName()).build())
                .build();
        factory = new DefaultBib2LodObjectFactory(config);
        instance = factory.instanceForInterface(AnInterface.class);
        instances = factory.instancesForInterface(AnInterface.class);
        assertNotNull(instance);
        assertEquals(1, instances.size());
    }

    @Test
    public void twoInstancesPerInterfaceWorks() {
        config = new Builder().addChild("AnInterface",
                new Builder().setClassName(AnInterfaceClass1.class.getName())
                        .build())
                .addChild("AnInterface",
                        new Builder()
                                .setClassName(AnInterfaceClass2.class.getName())
                                .build())
                .build();
        factory = new DefaultBib2LodObjectFactory(config);
        instance = factory.instanceForInterface(AnInterface.class);
        instances = factory.instancesForInterface(AnInterface.class);
        assertNotNull(instance);
        assertEquals(2, instances.size());
        assertEquals(instance, instances.get(0));
    }

    @Test
    public void configuresIfConfigurable() {
        Configuration childNode = new Builder()
                .setClassName(AConfigurableClass.class.getName())
                .addAttribute("format", "xml").build();
        config = new Builder().addChild("AConfigurableInterface", childNode)
                .build();
        factory = new DefaultBib2LodObjectFactory(config);
        instance = factory.instanceForInterface(AConfigurableInterface.class);
        assertEquals(childNode,
                ((AConfigurableClass) instance).getConfiguration());
    }

    @Test
    public void omnibusSuccess() {
        // Layer 4
        ConfigurationNode config2A = new ConfigurationNode.Builder()
                .setClassName(OmnibusClass2A.class.getName()).build();
        ConfigurationNode config2B = new ConfigurationNode.Builder()
                .setClassName(OmnibusClass2B.class.getName()).build();

        // Layer 3
        ConfigurationNode noInstance = new ConfigurationNode.Builder()
                .addChild("Omnibus2A", config2A).addChild("Omnibus2B", config2B)
                .build();

        // Layer 2
        ConfigurationNode config1A1 = new ConfigurationNode.Builder()
                .setClassName(OmnibusClass1A1.class.getName())
                .addAttribute("extension", "xml")
                .addChild("Irrelevant", noInstance).build();
        ConfigurationNode config1A2 = new ConfigurationNode.Builder()
                .setClassName(OmnibusClass1A2.class.getName())
                .addAttribute("format", "xml").build();
        ConfigurationNode config1B = new ConfigurationNode.Builder()
                .setClassName(OmnibusClass1B.class.getName())
                .addAttribute("content", "rdf")
                .addAttribute("content", "good stuff").build();

        // Layer 1
        config = new ConfigurationNode.Builder()
                .addChild("Omnibus1A", config1A1)
                .addChild("Omnibus1A", config1A2)
                .addChild("Omnibus1B", config1B).build();

        factory = new DefaultBib2LodObjectFactory(config);
        assertExpectedInstances(Omnibus1A.class, config1A1, config1A2);
        assertExpectedInstances(Omnibus1B.class, config1B);
        assertExpectedInstances(Omnibus2A.class, config2A);
        assertEquals(1, factory.instancesForInterface(Omnibus2B.class).size());
    }


    // ----------------------------------------------------------------------
    // Helper methods
    // ----------------------------------------------------------------------

    private void assertExpectedInstances(Class<?> interfaze,
            Configuration... nodes) {
        instances = factory.instancesForInterface(interfaze);
        assertEquals(nodes.length, instances.size());
        for (int i = 0; i < nodes.length; i++) {
            assertExpectedConfiguration(nodes[i], instances.get(i));
        }
    }
    
    private void assertExpectedConfiguration(Configuration expected,
            Object configurable) {
        assertEquals(expected,
                ((OmnibusConfigurable) configurable).getConfiguration());
    }

    // ----------------------------------------------------------------------
    // Helper Classes
    // ----------------------------------------------------------------------

    interface MultipleInterface {
        // Nothing
    }

    static class MultipleInterfaceHelper {
        interface MultipleInterface {
            // Nothing
        }
    }

    static class MultipleInterfaceClass implements MultipleInterface,
            MultipleInterfaceHelper.MultipleInterface {
        // Nothing
    }

    static class NoInterfacesClass {
        // Nothing
    }

    // ----------------------------------------------------------------------

    interface AnInterface {
        // Nothing
    }

    static class AnInterfaceClass1 implements AnInterface {
        // Nothing
    }

    static class AnInterfaceClass2 implements AnInterface {
        // Nothing
    }

    // ----------------------------------------------------------------------

    interface AConfigurableInterface extends Configurable {
        // Nothing
    }

    static class AConfigurableClass implements AConfigurableInterface {
        private Configuration configuration;

        @Override
        public void configure(Configuration c) {
            configuration = c;
        }

        public Configuration getConfiguration() {
            return configuration;
        }
    }

    // ----------------------------------------------------------------------

    interface Omnibus1A extends Configurable {
        // Nothing
    }

    interface Omnibus1B extends Configurable {
        // Nothing
    }

    interface Omnibus2A extends Configurable {
        // Nothing
    }

    interface Omnibus2B {
        // Nothing
    }

    static class OmnibusConfigurable implements Configurable {
        private Configuration configuration;

        @Override
        public void configure(Configuration c) {
            configuration = c;
        }

        public Configuration getConfiguration() {
            return configuration;
        }
    }

    static class OmnibusClass1A1 extends OmnibusConfigurable
            implements Omnibus1A {
        // Nothing
    }

    static class OmnibusClass1A2 extends OmnibusConfigurable
            implements Omnibus1A {
        // Nothing
    }

    static class OmnibusClass1B extends OmnibusConfigurable
            implements Omnibus1B {
        // Nothing
    }

    static class OmnibusClass2A extends OmnibusConfigurable
            implements Omnibus2A {
        // Nothing
    }

    static class OmnibusClass2B implements Omnibus2B {
        // Nothing
    }
}

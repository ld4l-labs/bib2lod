/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.configuration;

import java.util.List;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Link;
import org.ld4l.bib2lod.entities.Type;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;
import org.ld4l.bib2lod.util.collections.MapOfLists;

/**
 * TODO
 */
public class MockBib2LodObjectFactory extends Bib2LodObjectFactory {
    // ----------------------------------------------------------------------
    // Stub infrastructure
    // ----------------------------------------------------------------------

    MapOfLists<Class<?>, Object> instances = new MapOfLists<>();
    
    public <T> void addInstance(Class<T> interfaze, T instance) {
        addInstance(interfaze, instance, Configuration.EMPTY_CONFIGURATION);
    }
    
    public <T> void addInstance(Class<T> interfaze, T instance, Configuration config) {
        if (instance instanceof Configurable) {
            ((Configurable) instance).configure(config);
        }
        instances.addValue(interfaze, instance);
    }
    
    // ----------------------------------------------------------------------
    // Stub methods
    // ----------------------------------------------------------------------

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


    // ----------------------------------------------------------------------
    // Un-implemented methods
    // ----------------------------------------------------------------------

    @Override
    public Entity createEntity(Type type) {
        throw new RuntimeException("Bib2LodObjectFactory.createEntity() not implemented.");
    }

    @Override
    public Entity createEntity(Resource ontClass) {
        throw new RuntimeException("Bib2LodObjectFactory.createEntity() not implemented.");
    }

    @Override
    public Type createType(OntologyClass ontClass) {
        throw new RuntimeException("Bib2LodObjectFactory.createType() not implemented.");
    }

    @Override
    public Type createType(Resource ontClass) {
        throw new RuntimeException("Bib2LodObjectFactory.createType() not implemented.");
    }

    @Override
    public Link createLink(Property property) {
        throw new RuntimeException("Bib2LodObjectFactory.createLink() not implemented.");
    }

    @Override
    public Link createLink(OntologyProperty ontProperty) {
        throw new RuntimeException("Bib2LodObjectFactory.createLink() not implemented.");
    }
}

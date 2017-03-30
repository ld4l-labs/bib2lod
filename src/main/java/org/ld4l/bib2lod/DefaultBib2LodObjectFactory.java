/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.cli.ParseException;
import org.apache.jena.rdf.model.Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.cleaning.Cleaner;
import org.ld4l.bib2lod.cleaning.MarcxmlCleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.configuration.JsonOptionsReader;
import org.ld4l.bib2lod.configuration.OptionsReader;
import org.ld4l.bib2lod.configuration.StubConfiguration;
import org.ld4l.bib2lod.conversion.Converter;
import org.ld4l.bib2lod.conversion.xml.marcxml.MarcxmlConverter;
import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.entities.Link;
import org.ld4l.bib2lod.entities.SimpleEntity;
import org.ld4l.bib2lod.entities.SimpleLink;
import org.ld4l.bib2lod.entities.SimpleType;
import org.ld4l.bib2lod.entities.Type;
import org.ld4l.bib2lod.ontology.OntologyClass;
import org.ld4l.bib2lod.ontology.OntologyProperty;

/**
 * A simple implementation.
 */
public class DefaultBib2LodObjectFactory extends Bib2LodObjectFactory {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createOptionsReader(java.lang.String[])
     */
    @Override
    public OptionsReader createOptionsReader(String[] args) {
        return new JsonOptionsReader(args);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConfiguration(java.lang.String[])
     */
    @Override
    public Configuration createConfiguration(String[] args) throws Bib2LodObjectFactoryException {
        try {
            return new StubConfiguration();
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException | NoSuchMethodException
                | SecurityException | IOException | ParseException e) {
            throw new Bib2LodObjectFactoryException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createConverter()
     */
    @Override
    public Converter createConverter() {
        return new MarcxmlConverter();
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.Bib2LodObjectFactory#createCleaner(org.ld4l.bib2lod.cleaning.Cleaner)
     */
    @Override
    public Cleaner createCleaner() {
        return new MarcxmlCleaner();
    }
   
    @Override
    public Entity createEntity(Type type) {
        return new SimpleEntity(type);
    }

    @Override
    public Entity createEntity(Resource ontClass) {
        return new SimpleEntity(ontClass);
    }
    
    @Override
    public Entity createEntity(OntologyClass ontClass) {
        return new SimpleEntity(ontClass);
    }
    
    @Override
    public Type createType(OntologyClass ontClass) {
        return new SimpleType(ontClass);
    }
    
    @Override
    public Type createType(Resource ontClass) {
        return new SimpleType(ontClass);
    }

    @Override
    public Link createLink(OntologyProperty ontProperty) {
        return new SimpleLink(ontProperty);
    }



}

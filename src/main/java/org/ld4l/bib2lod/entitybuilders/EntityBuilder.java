/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entitybuilders;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.entities.Entity;
import org.w3c.dom.Element;

/**
 *
 */
public interface EntityBuilder {
    
    /**
     * Factory method
     * @param type - the type of Entity to instantiate
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     */
    static EntityBuilder instance(Class<?> type, Configuration configuration) 
                throws InstantiationException, 
                        IllegalAccessException, ClassNotFoundException, 
                            IllegalArgumentException, InvocationTargetException, 
                                NoSuchMethodException, SecurityException {
        
        return Bib2LodObjectFactory.instance().createEntityBuilder(
                type, configuration);
    }
    
   
    /**
     * Parses a record into a list of Entities.
     * @param <T> - the type of the record (varies between implementations)
     * @param element - the element from which to build the resources
     * @return a List of Entities
     */
    //public <T> Entity build(T element);
    // TEMPORARY!! Can't specify Element type here - not common to all builders
    public List<Entity> build(Element element);
    

}

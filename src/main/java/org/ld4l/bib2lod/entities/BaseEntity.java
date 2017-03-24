/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

/**
 * An abstract implementation.                      
 */
public abstract class BaseEntity implements Entity {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    // TODO Instead of a list of strings, do we want a list of Types (see
    // Identifier.Type)? But then there needs to be one enum Type for all types
    // of resources. Seems messy, and nicer for each type of resource to 
    // define its own types.
    protected List<String> types; 
   
    
    /**
     * Constructor
     */
    public BaseEntity() {
        types = new ArrayList<String>();
        String superType = getSuperType();
        if (superType != null) {
            types.add(superType);
        }
    }
    
    @Override
    public void addType(String type) {
        types.add(type);
    }
     
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entities.Entity#getTypes()
     */
    @Override
    public List<String> getTypes() {
        return types;
    }
   
    public void setTypes(Map<Namespace, String> types) {
        for (Map.Entry<Namespace, String> entry : types.entrySet()) {
            this.types.add(entry.getKey().uri() + entry.getValue());           
        }
    }

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * An abstract implementation.                     
 */
public abstract class BaseEntity implements EntityInterface {
    
    private static final Logger LOGGER = LogManager.getLogger(); 

    protected List<Type> types; 
    
    // Not all types of Entity will define these, but many will, so handle them 
    // here to avoid repetition.
    protected String label;
    protected String value;
    
    /**
     * Constructor
     */
    public BaseEntity() {
        types = new ArrayList<Type>();
        Type superType = getSuperType();
        if (superType != null) {
            types.add(superType);
        }
    }

    @Override
    public void addTypes(List<Type> types) {
        types.addAll(types);
    }
    
    @Override
    public void addType(Type type) {
        types.add(type);
    }
    
    @Override
    public void setRdfsLabel(String label) {
        this.label = label;
    }
    
    @Override
    public void setRdfValue(String value) {
        this.value = value;
    }
     
    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entities.Entity#getTypes()
     */
    @Override
    public List<Type> getTypes() {
        return types;
    }
   
    public String getRdfsLabel() {
        return label;
    }
    
    public String getRdfValue() {
        return value;
    }


}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class Instance extends BaseEntity {
    
  
    // Maybe a Map<String, String>, mapping identifier type to value
    private Map<String, String> identifiers;
    
    public Instance() {
        identifiers = new HashMap<String, String>();
    }
    
    public void setIdentifier(String type, String value) {

        
//        try {
//            Identifier identifier = 
//                    (Identifier) Entity.instance(Identifier.class);
//            identifier.setType(type);
//            
//        } catch (InstantiationException | IllegalAccessException
//                | ClassNotFoundException e) {
//            throw new EntityInstantiationException(
//                    e.getMessage(), e.getCause());
//        }
    }
    
    
    

}

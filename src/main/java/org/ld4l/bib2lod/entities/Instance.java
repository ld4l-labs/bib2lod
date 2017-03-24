/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.ld4l.bib2lod.Namespace;

/**
 * Represents the data about an Instance resource.
 */
public class Instance extends BibEntity {
    
    private enum Type {
        INSTANCE(Namespace.BIBFRAME, "Instance");
        
        private String uri;
        /**
         * Constructor
         */
        Type(Namespace namespace, String name) {
            this.uri = namespace.uri() + name;
        }
        
        public String uri() {
            return uri;
        }
    }
    
    private static Type SUPERTYPE = Type.INSTANCE;

    @Override
    public String getSuperType() {
        return SUPERTYPE.uri;
    }


    
//    public void setIdentifier(String type, String value) {
//      
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
//    }


}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.ld4l.bib2lod.Namespace;

/**
 * Represents the data about an Instance resource.
 */
public class Instance extends BibEntity {
    
    private enum InstanceType implements Type {
        INSTANCE(Namespace.BIBFRAME, "Instance");
        
        private String uri;
        
        /**
         * Constructor
         */
        InstanceType(Namespace namespace, String name) {
            this.uri = namespace.uri() + name;
        }
        
        @Override
        public String uri() {
            return uri;
        }
    }

    @Override
    public Type getSuperType() {
        return InstanceType.INSTANCE;
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

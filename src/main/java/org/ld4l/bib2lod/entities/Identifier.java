/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

public class Identifier extends BaseEntity {  

    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Define the Identifier types.
     */
    // TODO This is a place where I'd like to use the ontology files to get 
    // back all the Identifier classes (i.e., the superclass and subclasses).
    public static enum IdentifierType implements Type {

        IDENTIFIER(Namespace.BIBFRAME, "Identifier"),                     
        LOCAL(Namespace.BIBFRAME, "Local");
        
        private final String uri;
        
        IdentifierType(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
        }
 
        public String uri() {
            return uri;
        }
    }
    
    private String value;


    @Override
    public Type getSuperType() {
        return IdentifierType.IDENTIFIER;
    }
    

}

/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities_deprecated;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

/**
 *
 */
public class Work extends BibEntity {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Define the Work classes.
     */
    // TODO This is a place where I'd like to use the ontology files to get 
    // back all the Identifier classes (i.e., the superclass and subclasses).
    // TODO Right now this is only used internally. It's not useful for
    // external classes to refer to this, because then they have to refer to
    // Instance.Type, Work.Type, etc.
    // OR: could put these all in the same package
    private static enum WorkType implements Type {

        // TODO Add the others
        WORK(Namespace.BIBFRAME, "Identifier");                    
        
        private final String uri;
        
        WorkType(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
        }
 
        @Override
        public String uri() {
            return uri;
        }
    }

    @Override
    public Type getSuperType() {
        return WorkType.WORK;
    }
    


}

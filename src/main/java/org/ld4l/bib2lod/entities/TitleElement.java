/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

/**
 *
 */
public class TitleElement extends BaseEntity {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /**
     * Define the TitleElement classes.
     */
    // TODO This is a place where I'd like to use the ontology files to get 
    // back all the Identifier classes (i.e., the superclass and subclasses).
    // TODO Right now this is only used internally. It's not useful for
    // external classes to refer to this, because then they have to refer to
    // Instance.Type, Work.Type, etc.
    // OR: could put these all in the same package
    private static enum Type {

        TITLE_ELEMENT(Namespace.LD4L, "TitleElement"),
        NON_SORT_ELEMENT(Namespace.LD4L, "TitleElement"),
        MAIN_TITLE_ELEMENT(Namespace.LD4L, "TitleElement"),
        SUBTITLE_ELEMENT(Namespace.LD4L, "TitleElement"),
        PART_NUMBER_ELEMENT(Namespace.LD4L, "TitleElement"),
        PART_NAME_ELEMENT(Namespace.LD4L, "TitleElement");

        private static final Logger LOGGER = LogManager.getLogger(); 
        
        private final String uri;
        
        Type(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;

        }
 
        public String uri() {
            return uri;
        }
    }

    private static Type SUPERTYPE = Type.TITLE_ELEMENT;

    
    public String getSuperType() {
        return SUPERTYPE.uri;
    }



}

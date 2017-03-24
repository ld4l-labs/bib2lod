/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.ld4l.bib2lod.Namespace;

/**
 *
 */
public class Title extends BaseEntity {
    
    private enum Type {
        TITLE(Namespace.BIBFRAME, "Title");
        
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
    
    private static Type SUPERTYPE = Type.TITLE;
    
    // This Title is the title of bibEntity
    private BibEntity bibEntity;
    
    public Title(BibEntity bibEntity) {
        super();
        this.bibEntity = bibEntity;
    }
    
    public BibEntity getBibEntity() {
        return bibEntity;
    }

    @Override
    public String getSuperType() {
        return SUPERTYPE.uri;
    }

}

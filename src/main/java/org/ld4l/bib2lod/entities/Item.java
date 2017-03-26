/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.ld4l.bib2lod.Namespace;

/**
 * 
 */
public class Item extends BibEntity {
    
    public enum ItemType implements Type {
        ITEM(Namespace.BIBFRAME, "Item");
        
        private final String uri;
        
        ItemType(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;
        }
 
        @Override
        public String uri() {
            return uri;
        }
    }
    

    @Override
    public Type getSuperType() {
        return ItemType.ITEM;
    }
    


}

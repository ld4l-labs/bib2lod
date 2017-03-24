/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.ld4l.bib2lod.Namespace;

/**
 * 
 */
public class Item extends BibEntity {
    
    private static String TYPE = Namespace.BIBFRAME.uri() + "Item";

    @Override
    public String getSuperType() {
        return TYPE;
    }
    


}

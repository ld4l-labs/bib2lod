/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.ArrayList;
import java.util.List;

import org.ld4l.bib2lod.Namespace;

/**
 *
 */
public class Title extends BaseEntity {
    
    private enum TitleType implements Type {
        TITLE(Namespace.BIBFRAME, "Title");
        
        private String uri;
        
        /**
         * Constructor
         */
        TitleType(Namespace namespace, String name) {
            this.uri = namespace.uri() + name;
        }
        
        public String uri() {
            return uri;
        }
    }
    
    private List<TitleElement> titleElements;
    
    /**
     * Constructor
     */
    public Title() {
        titleElements = new ArrayList<TitleElement>();
    }

    @Override
    public Type getSuperType() {
        return TitleType.TITLE;
    }
    
    public void addTitleElement(TitleElement element) {
        titleElements.add(element);
    }
    
    public void addTitleElements(List<TitleElement> elements) {
        titleElements.addAll(elements);
    }
    
    public List<TitleElement> getTitleElements() {
        return titleElements;
    }

}

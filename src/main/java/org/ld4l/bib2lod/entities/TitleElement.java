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
    public static enum TitleElementType implements Type {

        TITLE_ELEMENT(Namespace.LD4L, "TitleElement"),
        NON_SORT_ELEMENT(Namespace.LD4L, "NonSortTitleElement"),
        MAIN_TITLE_ELEMENT(Namespace.LD4L, "MainTitleElement"),
        SUBTITLE_ELEMENT(Namespace.LD4L, "SubtitleElement"),
        PART_NUMBER_ELEMENT(Namespace.LD4L, "PartNumberElement"),
        PART_NAME_ELEMENT(Namespace.LD4L, "PartNameElement");
        
        private final String uri;
        
        TitleElementType(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;

        }
        
        @Override
        public String uri() {
            return uri;
        }
    }

    private int rank;
    
    /**
     * Constructor
     */
    public TitleElement(TitleElementType type, String label) {
        addType(type);
        setRdfsLabel(label);
    }

    @Override
    public Type getSuperType() {
        return TitleElementType.TITLE_ELEMENT;
    }
    
    public void setRank(int rank) {
        this.rank = rank;
    }
    
    public int getRank() {
        return rank;
    }

}

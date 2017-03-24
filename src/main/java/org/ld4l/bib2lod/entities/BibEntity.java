/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract implementation containing methods common to bibliographic 
 * entities (Work, Instance, Item).
 */
public abstract class BibEntity extends BaseEntity {

    protected List<Identifier> identifiers;
    protected List<Title> titles;
    protected List<Activity> activities;
    
    /**
     * Constructor
     * @param record 
     */
    public BibEntity() {
        super();
        identifiers = new ArrayList<Identifier>();
        titles = new ArrayList<Title>();
        activities = new ArrayList<Activity>();
    }
    
    public void addIdentifier(Identifier identifier) {
        identifiers.add(identifier);
    }

}

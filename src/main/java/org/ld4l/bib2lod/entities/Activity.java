/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.Namespace;

/**
 *
 */
public class Activity extends BaseEntity {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    private static enum ActivityType implements Type {

        // TODO Add the others
        ACTIVITY(Namespace.LD4L, "Activity");

        private static final Logger LOGGER = LogManager.getLogger(); 
        
        private final String uri;
        
        ActivityType(Namespace namespace, String localName) {
            this.uri = namespace.uri() + localName;

        }
 
        @Override
        public String uri() {
            return uri;
        }
    }

    /*
     * (non-Javadoc)
     * @see org.ld4l.bib2lod.entities.Entity#getSuperType()
     */
    @Override
    public Type getSuperType() {
        return ActivityType.ACTIVITY;
    }

}

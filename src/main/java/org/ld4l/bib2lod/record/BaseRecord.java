/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package org.ld4l.bib2lod.record;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;

/**
 * An abstract implementation.
 */
public abstract class BaseRecord implements Record {
    
    private static final Logger LOGGER = LogManager.getLogger(); 
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.Record#getElements()
     * In the case of a MarcxmlRecord, this includes MarcxmlLeader, 
     * the list of MarcxmlControlFields, and the list of MarcxmlDataFields.
     */
    @Override
    public List<RecordElement> getElements() {
        // TODO Auto-generated method stub - get the elements or an empty list
        return null;
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.Record#getElements(java.lang.String)
     */
    @Override
    public List<RecordElement> getElements(String elementName) {
        // TODO Auto-generated method stub - get the elements or an empty list
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.record.Record#getFirstElement(java.lang.String)
     */
    @Override
    public RecordElement getFirstElement(String tagName) {
        // TODO Auto-generated method stub - get the element or null
        return null;
    }

}

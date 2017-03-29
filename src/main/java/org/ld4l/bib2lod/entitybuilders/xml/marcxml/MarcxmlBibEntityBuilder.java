package org.ld4l.bib2lod.entitybuilders.xml.marcxml;


import org.ld4l.bib2lod.entities.Entity;
import org.ld4l.bib2lod.record.xml.marcxml.MarcxmlDataField;

/**
 * Abstract superclass for Work, Instance, and Entity builders. 
 */
public abstract class MarcxmlBibEntityBuilder extends MarcxmlEntityBuilder {

    /**
     * Adds an identifier built from a data field to the related bib resource.
     */
    // TODO If we can always get the work identifier from the instance, we 
    // don't need this - and perhaps not even this class. 
    protected Entity addIdentifier(MarcxmlDataField field) {
        throw new RuntimeException("Method not implemented.");
        
    }

}

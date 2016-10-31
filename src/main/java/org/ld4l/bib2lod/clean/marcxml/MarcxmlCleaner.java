package org.ld4l.bib2lod.clean.marcxml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.BaseCleaner;
import org.ld4l.bib2lod.conversion.BaseConverter;
import org.w3c.dom.Node;

public class MarcxmlCleaner extends BaseCleaner {
 
    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlCleaner.class);
    
    public Node clean(Node record) {
        return record;
    }

}

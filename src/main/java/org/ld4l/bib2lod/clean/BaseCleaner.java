package org.ld4l.bib2lod.clean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//TODO Maybe not possible to have an interface and implementation - there's
//not much in common - the return value of clean() will differ according to
//the input type (Node for XML, etc.)
public abstract class BaseCleaner implements Cleaner {

    private static final Logger LOGGER = LogManager.getLogger();
    
    public BaseCleaner() {
        // TODO Auto-generated constructor stub
    }

}

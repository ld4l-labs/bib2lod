package org.ld4l.bib2lod.cleaning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

//TODO Maybe not useful to have an interface and implementation - there's
//not much in common - the return value of clean() will differ according to
//the input type (Node for XML, etc.)
public abstract class BaseCleaner implements Cleaner {

    private static final Logger LOGGER = LogManager.getLogger();
    
    protected Configuration configuration;

    @Override
    public void configure(Configuration c) {
        configuration = c;
    }

    @Override
    public String clean() {
        // TODO Auto-generated method stub
        throw new RuntimeException("BaseCleaner.clean() not implemented.");
        
    } 
    

}

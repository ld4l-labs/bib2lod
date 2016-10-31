package org.ld4l.bib2lod.conversion;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

// TODO figure out whether we need an interface and/or base class
public class RecordConverter {
    
    private static final Logger LOGGER = 
            LogManager.getLogger(RecordConverter.class);
    
       
    public RecordConverter(Configuration config) {
        // from config, get cleaner, parser, converters and instantiate all
        
    }
    
    public String convertFile(File file) {
        // TODO Auto-generated method stub
        // Loop through records. For each: clean, parse, and loop through 
        // converters to convert
        // TODO Do we need a Record implementation?
        return null;
    }
    

}

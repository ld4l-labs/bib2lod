package org.ld4l.bib2lod.conversion;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.converter.Converter;
import org.ld4l.bib2lod.parser.Parser;

// TODO figure out whether we need an interface and/or base class
public class RecordConverter {
    
    private static final Logger LOGGER = 
            LogManager.getLogger(RecordConverter.class);
    
    private Cleaner cleaner;
    private Parser parser;
    private List<Converter> converters;
       
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
    
    protected String clean() {
        return null;
    }
    
    protected String parse() {
        return null;
    }
    
    // Get the record from convert file, return converted record
    protected String convert() {
        return null;
    }

}

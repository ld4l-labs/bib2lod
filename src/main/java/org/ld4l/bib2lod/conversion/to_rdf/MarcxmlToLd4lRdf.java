package org.ld4l.bib2lod.conversion.to_rdf;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;
import org.ld4l.bib2lod.conversion.BaseConverter;

public class MarcxmlToLd4lRdf extends BaseConverter {

    private static final Logger LOGGER = 
            LogManager.getLogger(MarcxmlToLd4lRdf.class);
    
    @Override
    public String convertFile(File file) {

        // Loop through records. For each: clean, parse, and loop through 
        // converters to convert
        // TODO Do we need a Record implementation?
        return null;
    }

    @Override
    public String convertFile(String text) {
        // TODO Auto-generated method stub
        return null;
    }
}

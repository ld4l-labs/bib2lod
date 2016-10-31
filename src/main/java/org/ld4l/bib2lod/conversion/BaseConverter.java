package org.ld4l.bib2lod.conversion;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

// TODO Does this do anything? If not, remove
public abstract class BaseConverter implements Converter {
    
    private static final Logger LOGGER = 
            LogManager.getLogger(BaseConverter.class);
    
}

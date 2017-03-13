package org.ld4l.bib2lod.cleaning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ld4l.bib2lod.configuration.Configuration;

public class MarcxmlCleaner extends BaseCleaner {
 
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Constructor
     * @param configuration - the program Configuration
     */
    public MarcxmlCleaner(Configuration configuration) {
        super(configuration);
    }

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.cleaning.Cleaner#clean()
     */
    @Override
    public String clean() {
        // TODO Auto-generated method stub
        return null;
    }

}

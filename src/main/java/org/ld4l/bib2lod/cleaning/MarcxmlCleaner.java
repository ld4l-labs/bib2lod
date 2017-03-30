package org.ld4l.bib2lod.cleaning;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MarcxmlCleaner extends BaseCleaner {
 
    private static final Logger LOGGER = LogManager.getLogger();

    /* (non-Javadoc)
     * @see org.ld4l.bib2lod.cleaning.Cleaner#clean()
     */
    @Override
    public String clean() {
        throw new RuntimeException("Method not implemented.");
    }

}

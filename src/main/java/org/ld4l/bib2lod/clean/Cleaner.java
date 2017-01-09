package org.ld4l.bib2lod.clean;

import org.ld4l.bib2lod.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configuration;

public interface Cleaner {
    
    /**
     * Factory method
     */
    static Cleaner instance(Configuration configuration) {
        return Bib2LodObjectFactory.instance().createCleaner(configuration);
    }

    public String clean();
}

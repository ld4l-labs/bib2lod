package org.ld4l.bib2lod.cleaning;

import org.ld4l.bib2lod.Bib2LodObjectFactory;

public interface Cleaner {
    
    /**
     * Factory method
     */
    static Cleaner instance() {
        return Bib2LodObjectFactory.instance().createCleaner();
    }

    public String clean();
}

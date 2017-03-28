package org.ld4l.bib2lod.cleaning;

import org.ld4l.bib2lod.configuration.Bib2LodObjectFactory;
import org.ld4l.bib2lod.configuration.Configurable;

public interface Cleaner extends Configurable {
    
    /**
     * Factory method
     */
    static Cleaner instance() {
        return Bib2LodObjectFactory.getFactory()
                .instanceForClass(Cleaner.class);
    }

    public String clean();
}

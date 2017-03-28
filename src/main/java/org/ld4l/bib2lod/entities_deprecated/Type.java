package org.ld4l.bib2lod.entities_deprecated;

public interface Type {

    /**
     * Returns the URI of this type
     */
    // TODO Figure out whether we really want to store the URI here or in
    // the ResourceBuilder.
    public String uri();
}

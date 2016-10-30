package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.util.List;

import org.ld4l.bib2lod.uri.UriMinter;

public interface Configuration {

    public UriMinter getUriMinter();
    
    // TODO Or just return the input string?
    public List<File> getInput();
    
}

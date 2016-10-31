package org.ld4l.bib2lod.configuration;

import java.io.File;
import java.util.List;

import org.ld4l.bib2lod.clean.Cleaner;
import org.ld4l.bib2lod.converter.Converter;
import org.ld4l.bib2lod.parser.Parser;
import org.ld4l.bib2lod.uri.UriMinter;

public interface Configuration {

    public UriMinter getUriMinter();
    
    // same for other services
    
    public Cleaner getCleaner();
    
    public Parser getParser();
    
    public List<Converter> getConverters();
    
    // TODO Or just return the input string?
    public List<File> getInput();
    
}
